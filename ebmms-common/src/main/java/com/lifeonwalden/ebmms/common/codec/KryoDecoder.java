package com.lifeonwalden.ebmms.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Kryo serialization byte decoder
 */
public class KryoDecoder extends ByteToMessageDecoder {
    public KryoDecoder() {
        super();
        System.out.println("new KryoDecoder");
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() > 0) {
            CharSequence charSequence = in.readCharSequence(in.readableBytes(), Charset.defaultCharset());
            System.out.println(charSequence.toString());
            out.add(charSequence);
        }
    }
}
