package com.lifeonwalden.ebmms.common.codec;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.lifeonwalden.ebmms.common.bean.Request;

import static com.esotericsoftware.kryo.Kryo.NULL;

public interface RequestSerializerUtil {
    static int estimateBufferSize(Request request) {
        int bufferSize = 12;
        if (null != request.getService()) {
            bufferSize += request.getService().getBytes().length * 1.5;
        }
        if (null != request.getMsgId()) {
            bufferSize += request.getMsgId().getBytes().length * 1.5;
        }
        if (null != request.getMethod()) {
            bufferSize += request.getMethod().getBytes().length * 1.5;
        }
        if (null != request.getParameter()) {
            bufferSize += request.getParameter().length + 128;
        }

        return bufferSize;
    }

    static void write(Output output, Request object) {
        output.writeString(object.getMsgId());
        output.writeString(object.getService());
        output.writeString(object.getMethod());
        byte[] parameter = object.getParameter();
        if (null == parameter) {
            output.writeVarInt(NULL, true);
            return;
        }
        output.writeVarInt(parameter.length + 1, true);
        output.writeBytes(parameter);
    }

    static Request read(Input input) {
        Request request = new Request();
        request.setMsgId(input.readString());
        request.setService(input.readString());
        request.setMethod(input.readString());
        int length = input.readVarInt(true);
        if (0 != length) {
            request.setParameter(input.readBytes(length - 1));
        }

        return request;
    }
}
