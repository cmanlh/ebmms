package com.lifeonwalden.ebmms.common.codec;


import com.esotericsoftware.kryo.io.Output;
import com.lifeonwalden.ebmms.common.bean.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RequestEncoder extends MessageToByteEncoder<Request> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Request msg, ByteBuf out) throws Exception {
        Output output = new Output(RequestSerializerUtil.estimateBufferSize(msg));
        RequestSerializerUtil.write(output, msg);
        out.writeBytes(output.toBytes());
    }
}
