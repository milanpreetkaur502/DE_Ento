#Replacing httpd.conf with user httpd.conf for Flask server host

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += " file://httpd.conf "

do_install_append() {
    install -d ${D}${sysconfdir}/apache2
    install -m 0644 ${WORKDIR}/httpd.conf ${D}${sysconfdir}/apache2/httpd.conf
    
    
}

