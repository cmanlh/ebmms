package com.lifeonwalden.ebmms.common.constant;

import java.util.HashMap;
import java.util.Map;

public enum SystemRequestEnum {
    NIL("0"), HEART_BIT("1"), SERVICE_REGISTER("2"), SERVICE_DISCOVERY("3"), SERVICE_CALLED_INFO("4"), SERVICE_RESPONSIVE_INFO("4");
    private static final Map<String, SystemRequestEnum> codeMapping = new HashMap<>();

    static {
        SystemRequestEnum[] enumArray = SystemRequestEnum.values();
        for (SystemRequestEnum _enum : enumArray) {
            codeMapping.put(_enum.code, _enum);
        }
    }

    private String code;

    SystemRequestEnum(String code) {
        this.code = code;
    }

    public static SystemRequestEnum codeOf(String code) {
        SystemRequestEnum systemRequest = codeMapping.get(code);
        return null == systemRequest ? NIL : systemRequest;
    }

    public String getCode() {
        return code;
    }
}
