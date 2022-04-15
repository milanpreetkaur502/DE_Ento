SUMMARY = "Network LED application"
DESCRIPTION = "Monitor network status"
LICENSE = "CLOSED"


SRC_URI = "file://network_script.sh \
	   file://network_script.service \
           "

inherit allarch systemd

S = "${WORKDIR}"

do_compile(){
}

do_install_append(){
        
	install -d ${D}${sbindir}/networkled
	install -m 777 ${S}/network_script.sh ${D}/${sbindir}/networkled
        install -d ${D}${systemd_unitdir}/system
        install -m 0644 ${WORKDIR}/network_script.service ${D}${systemd_unitdir}/system
        
}

SYSTEMD_SERVICE_${PN} = "network_script.service"
SYSTEMD_AUTO_ENABLE_${PN} = "enable"
