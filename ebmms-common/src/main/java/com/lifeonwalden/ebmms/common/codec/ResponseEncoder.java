package com.lifeonwalden.ebmms.common.codec;


import com.esotericsoftware.kryo.io.Output;
import com.lifeonwalden.ebmms.common.bean.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ResponseEncoder extends MessageToByteEncoder<Response> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Response msg, ByteBuf out) throws Exception {
        Output output = new Output(ResponseSerializerUtil.estimateBufferSize(msg));
        ResponseSerializerUtil.write(output, msg);
        out.writeBytes(output.toBytes());
    }
}
