package com.lifeonwalden.ebmms.server.handler;

import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.bean.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MsgProcessor extends SimpleChannelInboundHandler<Request> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {
        Response response = new Response();
        response.setMsgId(msg.getMsgId());
        response.setErrMsg("No error, wrong message!");

        ctx.writeAndFlush(response);
    }
}
