package com.lifeonwalden.ebmms.common.constant;

import java.util.HashMap;
import java.util.Map;

public enum ReturnCodeEnum {
    NIL(0), SUCCESS(1), CLIENT_OPERATION_TIMEOUT(2), SERVER_PROCESS_FAILED(3), UNKNOW_EXCEPTION(4);
    private static final Map<Integer, ReturnCodeEnum> codeMapping = new HashMap<>();

    static {
        ReturnCodeEnum[] enumArray = ReturnCodeEnum.values();
        for (ReturnCodeEnum _enum : enumArray) {
            codeMapping.put(_enum.value, _enum);
        }
    }

    private int value;

    ReturnCodeEnum(int value) {
        this.value = value;
    }

    public static ReturnCodeEnum codeOf(String code) {
        ReturnCodeEnum returnCodeEnum = codeMapping.get(code);
        return null == returnCodeEnum ? NIL : returnCodeEnum;
    }

    public int getValue() {
        return value;
    }
}
