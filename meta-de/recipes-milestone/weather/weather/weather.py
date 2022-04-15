from datetime import datetime
import subprocess
import time
import logging as log
import json
log.basicConfig(filename='/var/tmp/weather.log', filemode='w', level=log.INFO, format='[%(asctime)s]- %(message)s', datefmt='%d-%m-%Y %I:%M:%S %p')

path = "/etc/entomologist/"
with open(path + "ento.conf",'r') as file:
	data=json.load(file)
DEVICE_SERIAL_ID = data["device"]["SERIAL_ID"]
STORAGE_PATH=data["device"]["STORAGE_PATH"]
def weather():
	p = subprocess.Popen("/usr/sbin/weather/hts221", stdout=subprocess.PIPE, shell=True) # Use script file instead.
	(output, err) = p.communicate()
	q = subprocess.Popen("/usr/sbin/weather/VEML7700", stdout=subprocess.PIPE, shell=True)
	(outputL, errL) = q.communicate()
	log.info("Sensor read attempted")
	lux = ", ".join(str(outputL)[2:len(outputL)].split("\\n"))
	#print("Command output : ", output)
	#print("Command exit status/return code : ", p_status)
	tim = str(time.time())
	now = datetime.now()
	tim = tim.replace(".", "_")
	ti=now.strftime("%d-%m-%Y_%H")
	string=f"{STORAGE_PATH}weather_{ti}_{DEVICE_SERIAL_ID}.txt"
	file = open(string, "a")
	file.writelines("\n"+tim+" , "+", ".join(str(output)[2:len(output)-1].split("\\n"))+" , "+lux+"\n")
	file.close()
	time.sleep(30)

while True:
	try:
		weather()
	except Exception as e:
		log.info(e)
		time.sleep(5)
