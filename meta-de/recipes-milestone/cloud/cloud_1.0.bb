SUMMARY = "Cloud application"
DESCRIPTION = "uploads captured image on AWS(s3) cloud"
LICENSE = "CLOSED"


SRC_URI = "file://imageUpload.py \
	   file://pub.py \
	   file://run.py \
	   file://sub.py \
	   file://verification.py \
           file://cloud.service \
           "

inherit allarch systemd

S = "${WORKDIR}"

do_compile(){
}

do_install_append(){
        
	install -d ${D}${sbindir}/cloud
	install -m 777 ${S}/imageUpload.py ${D}/${sbindir}/cloud
	install -m 777 ${S}/pub.py ${D}/${sbindir}/cloud
	install -m 777 ${S}/run.py ${D}/${sbindir}/cloud
	install -m 777 ${S}/sub.py ${D}/${sbindir}/cloud
	install -m 777 ${S}/verification.py ${D}/${sbindir}/cloud
        install -d ${D}${systemd_unitdir}/system
        install -m 0644 ${WORKDIR}/cloud.service ${D}${systemd_unitdir}/system

}

SYSTEMD_SERVICE_${PN} = "cloud.service"
SYSTEMD_AUTO_ENABLE_${PN} = "disable"
