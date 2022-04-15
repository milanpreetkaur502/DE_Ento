#!/usr/bin/env python3

import paho.mqtt.client as mqtt
import datetime as dt
import subprocess
import random
import json
import ast
import time
import os
import multiprocessing
from sub import start_subscribe
from pub import start_publish
from imageUpload import image_upload_manager
from verification import start_verification
import logging as log

# AWS Setup
log.basicConfig(filename='/var/tmp/cloud.log', filemode='w', level=log.INFO, format='[%(asctime)s]- %(message)s', datefmt='%d-%m-%Y %I:%M:%S %p')
with open(f"/etc/entomologist/ento.conf",'r') as file:
	data=json.load(file)


DEVICE_SERIAL_ID = data["device"]["SERIAL_ID"]
provisionstatus=data["device"]["PROVISION_STATUS"]


MQTT_BROKER = data["device"]["ENDPOINT_URL"]
PORT = 8883
MQTT_KEEP_INTERVAL = 44
rootCA = '/etc/entomologist/cert/AmazonRootCA1.pem'
cert = '/etc/entomologist/cert/certificate.pem.crt'
privateKey = '/etc/entomologist/cert/private.pem.key'

BUCKET_NAME = "test-entomoligist"

# Publish Details

PUBLISH_CLIENT_NAME = 'digitalEntomologist'
PUBLISH_TOPIC = f'cameraDevice/generateURL/{DEVICE_SERIAL_ID}'
PUBLISH_QoS = 1

# Subscribe Details

SUBSCRIBE_CLIENT_NAME = 'iot-data'
SUBSCRIBE_TOPIC = f'cameraDevice/getURL/{DEVICE_SERIAL_ID}'
SUBSCRIBE_QoS = 0

# Verification Details

VERIFICATION_TOPIC = f'cameraDevice/fileUploaded/{DEVICE_SERIAL_ID}'

# Buffer Storage Path

BUFFER_IMAGES_PATH = '/media/mmcblk1p1/'


def generate_payload(filesList):



	payload = {
		"device-serialID":DEVICE_SERIAL_ID,
		"bucket-name":BUCKET_NAME,
		"files": filesList
	}

	return json.dumps(payload)

def signed_url_file_exist():
	log.info("Checking for signed URL json file exist")
	while "signedUrls.json" not in os.listdir():
		time.sleep(2)
	log.info("Signed Url file exist")
	return True


def upload_manager(filesList):

	batchSize = len(filesList)

	log.info("Generating for payload")
	publishPayload = generate_payload(filesList)
	log.info("Payload generated for upload")

	# Create start_subscribe and start_publish as two processes by implementing mulitprocessess.
	p1 = multiprocessing.Process(target = start_subscribe, args = [
		MQTT_BROKER,
		PORT,
		MQTT_KEEP_INTERVAL,
		SUBSCRIBE_CLIENT_NAME,
		SUBSCRIBE_TOPIC,
		SUBSCRIBE_QoS,
		rootCA,
		cert,
		privateKey])

	p2 = multiprocessing.Process(target = start_publish, args =[
		MQTT_BROKER,
		PORT,
		MQTT_KEEP_INTERVAL,
		PUBLISH_CLIENT_NAME,
		PUBLISH_TOPIC,
		PUBLISH_QoS,
		publishPayload,
		rootCA,
		cert,
		privateKey])
	p1.start()
	log.info("Start Subscribe process started")
	p2.start()
	log.info("Start Publish process started")
	p1.join()
	p2.join()
	log.info("Subscribe and publish process finished")

	# Create a better implementation once the signedUrls.json file has been created.
	if signed_url_file_exist():

		p3 = multiprocessing.Process(target = start_verification, args = [
		MQTT_BROKER,
		PORT,
		MQTT_KEEP_INTERVAL,
		SUBSCRIBE_CLIENT_NAME,
		VERIFICATION_TOPIC,
		SUBSCRIBE_QoS,
		batchSize,
		rootCA,
		cert,
		privateKey])

		p4 = multiprocessing.Process(target = image_upload_manager)

		p3.start()
		log.info("Start Verification process started")
		p4.start()
		log.info("Image Upload manager process started")
		p3.join()
		p4.join()
		log.info("Image Upload manager and verification process finished")

		os.remove('signedUrls.json')


def main():
	log.info("Cloud Main started..")
	if provisionstatus=="True":
		while len(os.listdir(BUFFER_IMAGES_PATH)):
			filesList = os.listdir(BUFFER_IMAGES_PATH)[:10]
			log.info("Calling upload manager..")
			try:
				upload_manager(filesList)
			except Exception as e:
				log.info("Upload failed /n"+str(e))
				time.sleep(5)	
		log.info("Upload manager successfully executed..")
		log.info("-"*50)
		time.sleep(1)
	else:
		log.info("I m running but provison status is False")



main()
