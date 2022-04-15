SUMMARY = "Weather App"
DESCRIPTION = "Weather Application"
LICENSE = "CLOSED"


SRC_URI = "file://TH.sh \
	   file://hts221.c \
	   file://hts221 \
	   file://VEML7700 \
	   file://weather.service \
	   file://weather.py \
           "
inherit systemd

S = "${WORKDIR}"

do_compile(){
}

do_install_append(){     
	install -d ${D}${sbindir}/weather
	install -m 777 ${S}/TH.sh ${D}${sbindir}/weather
	install -m 777 ${S}/hts221.c ${D}${sbindir}/weather
	install -m 777 ${S}/hts221 ${D}${sbindir}/weather
	install -m 777 ${S}/VEML7700 ${D}${sbindir}/weather
	install -m 777 ${S}/weather.py ${D}${sbindir}/weather
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/weather.service ${D}${systemd_unitdir}/system
	
}

SYSTEMD_SERVICE_${PN} = "weather.service"
SYSTEMD_AUTO_ENABLE_${PN} = "disable"
