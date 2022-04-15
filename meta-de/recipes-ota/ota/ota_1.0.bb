SUMMARY = "OTA app"
DESCRIPTION = "OTA files"
LICENSE = "CLOSED"


SRC_URI = "file://cam \
	   file://provision \
	   file://cloud \
	   file://jobreceiver \
	   file://network \
	   file://synchronizer \
           "

S = "${WORKDIR}"

do_compile(){
}

do_install(){
	install -d ${D}${datadir}/mender
	install -d ${D}${datadir}/mender/modules
	install -d ${D}${datadir}/mender/modules/v3
	install -m 777 ${S}/cam ${D}${datadir}/mender/modules/v3
	install -m 777 ${S}/provision ${D}${datadir}/mender/modules/v3
	install -m 777 ${S}/cloud ${D}${datadir}/mender/modules/v3
	install -m 777 ${S}/jobreceiver ${D}${datadir}/mender/modules/v3
	install -m 777 ${S}/network ${D}${datadir}/mender/modules/v3
	install -m 777 ${S}/synchronizer ${D}${datadir}/mender/modules/v3

}
FILES_${PN} += "${datadir}/*"
