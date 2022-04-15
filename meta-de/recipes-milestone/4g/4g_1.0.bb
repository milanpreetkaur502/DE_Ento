SUMMARY = "OTA app"
DESCRIPTION = "OTA web-file"
LICENSE = "CLOSED"


SRC_URI = "file://4g.sh \
           file://4g_restart.sh \
           "

S = "${WORKDIR}"

do_compile(){
}

do_install(){
        
	install -d ${D}${exec_prefix}/sbin/4g
	install -m 777 ${S}/4g.sh ${D}${exec_prefix}/sbin/4g
	install -m 777 ${S}/4g_restart.sh ${D}${exec_prefix}/sbin/4g

}


