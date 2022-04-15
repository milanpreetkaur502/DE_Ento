#Append script for tdx-reference minimal
#ARV
#Toradex
#Layer meta-de

SYSTEMD_DEFAULT_TARGET = "graphical.target"

IMAGE_FEATURES += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'x11', \
                                                       '', d), d)} \
"


IMAGE_INSTALL += " \
    packagegroup-tdx-cli \
    packagegroup-tdx-graphical \
    packagegroup-fsl-isp \
    \
    python3 \
    python3-pillow \
    python3-pyserial \
    python3-smbus2 \
    python3-flask \
    apache2 \
    opencv \
    cmake \
    monitor \
    camera \
    cloud \ 
    provision \
    jobreceiver \
    network \
    networkled \
    synchronizer \
    weather \
    4g \
    nano \
    bc \ 
    gcc \
    statescripts \
    ffmpeg \
    ota \
    aws-crt-python \
    aws-iot-device-sdk-python-v2 \
    aws-c-mqtt \
    python3-sqlite3 \
    python3-paho-mqtt \
    python3-requests \
    packagegroup-tdx-cli \
    packagegroup-tdx-graphical \
    packagegroup-fsl-isp \
    bash \
    coreutils \
    less \
    makedevs \
    mime-support \
    util-linux \
    v4l-utils \
    gpicview \
    media-files \
    modemmanager \
    networkmanager \
"

IMAGE_INSTALL_remove = " connman"
IMAGE_INSTALL_remove = " connman-client"
IMAGE_INSTALL_remove = " connman-gnome"
IMAGE_INSTALL_remove = " connman-plugin-wifi"
IMAGE_INSTALL_remove = " connman-plugin-ethernet"
IMAGE_INSTALL_remove = " connman-plugin-loopback"
PACKAGECONFIG_append_pn-networkmanager = " modemmanager ppp"

change_mod() {
        ln -nsf /usr/share/zoneinfo/Asia/Kolkata ${IMAGE_ROOTFS}/etc/localtime   
}

ROOTFS_POSTPROCESS_COMMAND += "change_mod; "
