package com.lifeonwalden.ebmms.common.codec;


import com.esotericsoftware.kryo.io.Output;
import com.lifeonwalden.ebmms.common.bean.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;

public class ResponseEncoder extends MessageToByteEncoder<Response> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Response msg, ByteBuf out) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        ResponseSerializerUtil.write(output, msg);
        output.flush();
        out.writeBytes(baos.toByteArray());
    }
}
