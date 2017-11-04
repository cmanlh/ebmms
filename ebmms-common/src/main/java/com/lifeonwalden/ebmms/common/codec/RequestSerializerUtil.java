package com.lifeonwalden.ebmms.common.codec;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.lifeonwalden.ebmms.common.bean.Request;

import static com.esotericsoftware.kryo.Kryo.NULL;

public interface RequestSerializerUtil {
    static void write(Output output, Request object) {
        output.writeString(object.getMsgId());
        output.writeString(object.getService());
        output.writeString(object.getMethod());
        Object[] parameters = object.getParameters();
        if (null == parameters) {
            output.writeVarInt(NULL, true);
            return;
        }
        output.writeVarInt(parameters.length, true);
        for (Object param : parameters) {
            byte[] encoded = KryoCoder.encodeWithClass(param);
            output.writeVarInt(encoded.length, true);
            output.writeBytes(encoded);
        }
    }

    static Request read(Input input) {
        Request request = new Request();
        request.setMsgId(input.readString());
        request.setService(input.readString());
        request.setMethod(input.readString());
        int length = input.readVarInt(true);
        if (NULL != length) {
            Object[] parameters = new Object[length];
            for (int i = 0; i < length; i++) {
                parameters[i] = KryoCoder.decodeWithClass(input.readBytes(input.readVarInt(true)));
            }
            request.setParameters(parameters);
        }

        return request;
    }
}
