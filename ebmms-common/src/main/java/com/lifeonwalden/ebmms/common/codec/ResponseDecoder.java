package com.lifeonwalden.ebmms.common.codec;

import com.esotericsoftware.kryo.io.Input;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ResponseDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try (Input input = new Input(new ByteBufInputStream(in), in.readableBytes())) {
            out.add(ResponseSerializerUtil.read(input));
        }
    }
}
