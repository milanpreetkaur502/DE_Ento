SUMMARY = "State Scripts"
DESCRIPTION = "State Scripts for web-file"
LICENSE = "CLOSED"


SRC_URI = "file://artifact_enter.sh \
           file://artifact_leave.sh \
           "

S = "${WORKDIR}"

do_compile(){
}

do_install(){
        
	install -d ${D}${sysconfdir}/mender
	install -d ${D}${sysconfdir}/mender/scripts
	install -m 777 ${S}/artifact_enter.sh ${D}${sysconfdir}/mender/scripts
	install -m 777 ${S}/artifact_leave.sh ${D}${sysconfdir}/mender/scripts

}
FILES_${PN} += "${sysconfdir}/*"
