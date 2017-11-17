package com.lifeonwalden.ebmms.client.handler;

import com.lifeonwalden.ebmms.common.bean.Response;
import com.lifeonwalden.ebmms.common.concurrent.MsgStorehouse;
import com.lifeonwalden.ebmms.common.constant.ReturnCodeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MsgProcessor extends SimpleChannelInboundHandler<Response> {
    private final static Logger logger = LogManager.getLogger(MsgProcessor.class);

    private MsgStorehouse<Response> storehouse;

    public MsgProcessor(MsgStorehouse<Response> storehouse) {
        this.storehouse = storehouse;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Response msg) throws Exception {
        msg.setReturnCode(ReturnCodeEnum.SUCCESS.getValue());
        storehouse.put(msg.getMsgId(), msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Response msg = new Response();
        msg.setMsgId(ctx.channel().id().asLongText());
        msg.setErrMsg(cause.getMessage());
        msg.setReturnCode(ReturnCodeEnum.FAILED.getValue());
        logger.error(msg.getErrMsg(), cause);

        storehouse.put(msg.getMsgId(), msg);
        ctx.close();
    }


}
