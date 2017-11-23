package com.lifeonwalden.ebmms.common.codec;


import com.esotericsoftware.kryo.io.Output;
import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.constant.BizConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.io.ByteArrayOutputStream;

public class RequestEncoder extends MessageToByteEncoder<Request> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Request msg, ByteBuf out) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        RequestSerializerUtil.write(output, msg);
        output.flush();
        out.writeBytes(baos.toByteArray());
        Attribute<Integer> attribute = ctx.channel().<Integer>attr(AttributeKey.valueOf(BizConstant.TIMEOUT_SECONDS_KEY));
        if (msg.getTimeoutSeconds() > 0) {
            attribute.set(msg.getTimeoutSeconds());
        } else {
            attribute.set(null);
        }
    }
}
