package com.lifeonwalden.ebmms.common.util;

public interface ServiceUtil {
    static String fetchServiceName(String service, int version) {
        return service.concat(":").concat(String.valueOf(version));
    }

    static String fetchHostName(String host, int port) {
        return host.concat(":").concat(String.valueOf(port));
    }
}
