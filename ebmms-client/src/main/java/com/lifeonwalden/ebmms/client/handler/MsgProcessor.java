package com.lifeonwalden.ebmms.client.handler;

import com.lifeonwalden.ebmms.common.bean.Response;
import com.lifeonwalden.ebmms.common.concurrent.MsgStorehouse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MsgProcessor extends SimpleChannelInboundHandler<Response> {
    private MsgStorehouse<Response> storehouse;

    public MsgProcessor(MsgStorehouse<Response> storehouse) {
        this.storehouse = storehouse;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Response msg) throws Exception {
        storehouse.put(msg.getMsgId(), msg);
    }
}
