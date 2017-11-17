package com.lifeonwalden.ebmms.common.constant;

import java.util.HashMap;
import java.util.Map;

public enum ReturnCodeEnum {
    NIL(0), SUCCESS(1), FAILED(2);
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
