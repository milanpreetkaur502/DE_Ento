FILESEXTRAPATHS_prepend := "${THISDIR}/linux-toradex:"

# Prevent the use of in-tree defconfig
unset KBUILD_DEFCONFIG

SRC_URI += "\ 
    file://defconfig \
    "
}
