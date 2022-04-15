SUMMARY = "Camera Application"
DESCRIPTION = "Capture motion images and store on SD card"
LICENSE = "CLOSED"


SRC_URI = "file://cam.py \
           file://cam.service \
           "

inherit allarch systemd

S = "${WORKDIR}"

do_compile(){
}

do_install_append(){
        
	install -d ${D}${sbindir}/cam
	install -m 777 ${S}/cam.py ${D}/${sbindir}/cam
        install -d ${D}${systemd_unitdir}/system
        install -m 0644 ${WORKDIR}/cam.service ${D}${systemd_unitdir}/system
}

SYSTEMD_SERVICE_${PN} = "cam.service"
SYSTEMD_AUTO_ENABLE_${PN} = "disable"

