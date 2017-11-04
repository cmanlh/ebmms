package com.lifeonwalden.ebmms.server.handler;

import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.bean.Response;
import com.lifeonwalden.ebmms.proxy.TcpServiceDiscovery;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.FormattedMessage;

import java.lang.reflect.Method;

public class MsgProcessor extends SimpleChannelInboundHandler<Request> {
    private final static Logger logger = LogManager.getLogger(MsgProcessor.class);

    private TcpServiceDiscovery tcpServiceDiscovery;

    public MsgProcessor(TcpServiceDiscovery tcpServiceDiscovery) {
        super();
        this.tcpServiceDiscovery = tcpServiceDiscovery;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {
        Object service = this.tcpServiceDiscovery.getServiceIndex().get(msg.getService());
        Method method = service.getClass().getMethod(msg.getMethod());

        Response response = new Response();
        response.setMsgId(msg.getMsgId());
        try {
            Object result = null;
            if (null != msg.getParameter() && msg.getParameter().length > 0) {

            } else {
                result = method.invoke(service);
            }
        } catch (Throwable e) {
            logger.error(new FormattedMessage("Failed to call service {}", msg.getService()), e);

            response.setErrMsg(e.getMessage());
        }

        ctx.writeAndFlush(response);
    }
}
