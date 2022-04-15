SUMMARY = "Boot Provision"
DESCRIPTION = "Device provisioning at boot"
LICENSE = "CLOSED"


SRC_URI = "file://boot.py \
	   file://update_boot_status.py \
           "

inherit allarch systemd

S = "${WORKDIR}"

do_compile(){
}

do_install_append(){
        
	install -d ${D}${sbindir}/provision
	install -m 777 ${S}/boot.py ${D}/${sbindir}/provision
	install -m 777 ${S}/update_boot_status.py ${D}/${sbindir}/provision

}

