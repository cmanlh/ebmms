package com.lifeonwalden.ebmms.common.codec;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.lifeonwalden.ebmms.common.bean.Response;

import static com.esotericsoftware.kryo.Kryo.NULL;

public interface ResponseSerializerUtil {
    static void write(Output output, Response object) {
        output.writeString(object.getMsgId());
        Object result = object.getResult();
        if (null == result) {
            output.writeVarInt(NULL, true);
        } else {
            byte[] encoded = KryoCoder.encodeWithClass(result);
            output.writeVarInt(encoded.length, true);
            output.writeBytes(encoded);
        }
        output.writeVarInt(object.getReturnCode(), true);
        output.writeString(object.getErrMsg());
    }

    static Response read(Input input) {
        Response response = new Response();
        response.setMsgId(input.readString());
        int length = input.readVarInt(true);
        if (NULL != length) {
            response.setResult(KryoCoder.decodeWithClass(input.readBytes(length)));
        }
        response.setReturnCode(input.readInt(true));
        response.setErrMsg(input.readString());

        return response;
    }
}
