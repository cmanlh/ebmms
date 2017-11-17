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
        Response response = new Response();
        response.setMsgId(msg.getMsgId());
        try {
            Object result = null;
            Object[] parameters = msg.getParameters();
            Object service = this.tcpServiceDiscovery.getServiceIndex().get(msg.getService());
            if (null != parameters && parameters.length > 0) {
                Class[] paramTypes = new Class[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    paramTypes[i] = parameters[i].getClass();
                }
                Method method = service.getClass().getMethod(msg.getMethod(), paramTypes);
                result = method.invoke(service, parameters);
            } else {
                Method method = service.getClass().getMethod(msg.getMethod());
                result = method.invoke(service);
            }
            response.setResult(result);
        } catch (Throwable e) {
            logger.error(new FormattedMessage("Failed to call service {}", msg.getService()), e);

            response.setErrMsg(e.getMessage());
        }

        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        logger.error(cause);
    }
}
