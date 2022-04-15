
SUMMARY = "Motion Vision"
DESCRIPTION = "Motion capture using webcam and image storage"

LICENSE = "CLOSED"


SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/milanpreetkaur502/DEV;protocol=git;branch=main"

inherit allarch

S = "${WORKDIR}/git"

do_compile(){
}

do_install_append(){
	install -d ${D}${sbindir}/motion
        install -m 755 ${S}/motion_detection_cv2.py ${D}/${sbindir}/motion
}


