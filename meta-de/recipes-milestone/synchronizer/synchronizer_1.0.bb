SUMMARY = "Synchronizer application"
DESCRIPTION = "synchronizes all other services"
LICENSE = "CLOSED"


SRC_URI = "file://synchronizer.py \
	   file://synchronizer.service \
           "

inherit allarch systemd

S = "${WORKDIR}"

do_compile(){
}

do_install_append(){
        
	install -d ${D}${sbindir}/synchronizer
	install -m 777 ${S}/synchronizer.py ${D}/${sbindir}/synchronizer
        install -d ${D}${systemd_unitdir}/system
        install -m 0644 ${WORKDIR}/synchronizer.service ${D}${systemd_unitdir}/system

}

SYSTEMD_SERVICE_${PN} = "synchronizer.service"
SYSTEMD_AUTO_ENABLE_${PN} = "enable"
