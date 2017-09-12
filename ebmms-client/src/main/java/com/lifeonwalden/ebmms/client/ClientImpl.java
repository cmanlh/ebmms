package com.lifeonwalden.ebmms.client;

import com.lifeonwalden.ebmms.client.handler.MsgProcessor;
import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.bean.Response;
import com.lifeonwalden.ebmms.common.codec.RequestEncoder;
import com.lifeonwalden.ebmms.common.codec.ResponseDecoder;
import com.lifeonwalden.ebmms.common.concurrent.MsgStorehouse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientImpl implements Client {
    private final static Logger logger = LogManager.getLogger(ClientImpl.class);

    private Channel channel;
    private EventLoopGroup group;
    private MsgStorehouse<Response> storehouse;

    public ClientImpl(String host, int port, MsgStorehouse<Response> storehouse) throws InterruptedException {
        group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 8, 0, 8), new ResponseDecoder())
                                .addLast(new LengthFieldPrepender(8), new RequestEncoder())
                                .addLast(new MsgProcessor(storehouse));
                    }
                });

        try {
            channel = bootstrap.connect(host, port).sync().channel();
        } catch (InterruptedException e) {
            throw e;
        }
        this.storehouse = storehouse;
    }

    @Override
    public Response send(Request request) {
        storehouse.buy(request.getMsgId());
        channel.writeAndFlush(request);

        return storehouse.take(request.getMsgId());
    }

    @Override
    public Response send(Request request, int timeout) {
        return send(request);
    }

    @Override
    public Response send(Request request, int retry, int timeout) {
        return send(request);
    }

    public boolean isActive() {
        return channel.isActive();
    }

    @Override
    public void close() {
        try {
            channel.close().sync();
        } catch (InterruptedException e) {
            logger.error("Failed to close the channel.", e);
        } finally {
            group.shutdownGracefully();
        }

    }
}
