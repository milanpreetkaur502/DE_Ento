SUMMARY = "Jobreceiver application"
DESCRIPTION = "Handles Job requests"
LICENSE = "CLOSED"


SRC_URI = "file://jobReceiver.py \
	   file://logsUpload.py \
	   file://jobreceiver.service \
           "

inherit allarch systemd

S = "${WORKDIR}"

do_compile(){
}

do_install_append(){        
	install -d ${D}${sbindir}/jobreceiver
	install -m 777 ${S}/jobReceiver.py ${D}/${sbindir}/jobreceiver
	install -m 777 ${S}/logsUpload.py ${D}/${sbindir}/jobreceiver
        install -d ${D}${systemd_unitdir}/system
        install -m 0644 ${WORKDIR}/jobreceiver.service ${D}${systemd_unitdir}/system

}

SYSTEMD_SERVICE_${PN} = "jobreceiver.service"
SYSTEMD_AUTO_ENABLE_${PN} = "disable"
