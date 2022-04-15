SUMMARY = "Gateway Application"
DESCRIPTION = "Application handling cloud, node and database thread to communicate IOT Device to cloud server"
LICENSE = "CLOSED"


SRC_URI = "file://AmazonRootCA1.pem \
           file://certificate.pem.crt \
           file://scriptStatus.json \
           file://ento.conf \
           file://private.pem.key \
           file://public.pem.key \
           file://newFile.txt \
           "

inherit allarch systemd

S = "${WORKDIR}"

do_compile(){
}

do_install_append(){
	install -d ${D}${localstatedir}/log/ento
	install -d ${D}${sysconfdir}/entomologist
        install -d ${D}${sysconfdir}/entomologist/cert
        install -d ${D}${sysconfdir}/entomologist/bootstrap
        install -m 777 ${S}/AmazonRootCA1.pem ${D}/${sysconfdir}/entomologist/bootstrap
        install -m 777 ${S}/certificate.pem.crt ${D}/${sysconfdir}/entomologist/bootstrap
	install -m 777 ${S}/private.pem.key ${D}/${sysconfdir}/entomologist/bootstrap
	install -m 777 ${S}/public.pem.key ${D}/${sysconfdir}/entomologist/bootstrap
	install -m 777 ${S}/newFile.txt ${D}/${sysconfdir}/entomologist/bootstrap
	install -m 777 ${S}/ento.conf ${D}/${sysconfdir}/entomologist
	install -m 777 ${S}/scriptStatus.json ${D}/${sysconfdir}/entomologist

}

