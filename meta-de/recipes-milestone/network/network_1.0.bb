SUMMARY = "Network application"
DESCRIPTION = "Monitor network status"
LICENSE = "CLOSED"


SRC_URI = "file://network.py \
	   file://network.service \
	   file://wwan0 \
           "

inherit allarch systemd

S = "${WORKDIR}"

do_compile(){
}

do_install_append(){
        install -d ${D}${sysconfdir}/network/
        install -d ${D}${sysconfdir}/network/interfaces.d
        install -d ${D}${sysconfdir}/network/interfaces
	install -m 777 ${S}/wwan0 ${D}/${sysconfdir}/network/interfaces.d
	install -m 777 ${S}/wwan0 ${D}/${sysconfdir}/network/interfaces       
	install -d ${D}${sbindir}/network
	install -m 777 ${S}/network.py ${D}/${sbindir}/network
        install -d ${D}${systemd_unitdir}/system
        install -m 0644 ${WORKDIR}/network.service ${D}${systemd_unitdir}/system
        
}

SYSTEMD_SERVICE_${PN} = "network.service"

