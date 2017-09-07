package com.lifeonwalden.ebmms.common.util;

public interface UUID {
    static String fetch() {
        char[] charArray = java.util.UUID.randomUUID().toString().toCharArray();
        int position = 0;
        for (int i = 0; i < charArray.length; i++) {
            if ('-' != charArray[i]) {
                charArray[position++] = charArray[i];
            }
        }

        return new String(charArray, 0, position);
    }
}
