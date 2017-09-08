package com.lifeonwalden.ebmms.common.codec;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.lifeonwalden.ebmms.common.bean.Response;

import static com.esotericsoftware.kryo.Kryo.NULL;

public interface ResponseSerializerUtil {
    static int estimateBufferSize(Response response) {
        int bufferSize = 12;
        if (null != response.getMsgId()) {
            bufferSize += response.getMsgId().getBytes().length * 1.5;
        }
        if (null != response.getErrMsg()) {
            bufferSize += response.getErrMsg().getBytes().length * 1.5;
        }
        if (null != response.getReturnVal()) {
            bufferSize += response.getReturnVal().length + 128;
        }

        return bufferSize;
    }

    static void write(Output output, Response object) {
        output.writeString(object.getMsgId());
        byte[] returnVal = object.getReturnVal();
        if (null == returnVal) {
            output.writeVarInt(NULL, true);
        } else {
            output.writeVarInt(returnVal.length + 1, true);
            output.writeBytes(returnVal);
        }
        output.writeString(object.getErrMsg());
    }

    static Response read(Input input) {
        Response response = new Response();
        response.setMsgId(input.readString());
        int length = input.readVarInt(true);
        if (0 != length) {
            response.setReturnVal(input.readBytes(length - 1));
        }
        response.setErrMsg(input.readString());

        return response;
    }
}
