package com.lifeonwalden.ebmms.client;

import com.lifeonwalden.ebmms.client.concurrent.ResponseStorehouse;
import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.bean.Response;
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
import org.apache.logging.log4j.message.FormattedMessage;

public class Client {
    private final static Logger logger = LogManager.getLogger(Client.class);

    private Channel channel;
    private EventLoopGroup group;
    private ResponseStorehouse storehouse;

    public Client(String host, int port, ResponseStorehouse storehouse) throws InterruptedException {
        group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).option(ChannelOption.SO_KEEPALIVE, true).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldPrepender(8), new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 8));
                    }
                });

        try {
            channel = bootstrap.connect(host, port).sync().channel();
        } catch (InterruptedException e) {
            logger.error(new FormattedMessage("create a new client to {}:{} failed", host, port), e);

            throw e;
        }
    }

    public Response send(Request request) {
        storehouse.buy(request.getMsgId());
        channel.writeAndFlush(request);

        return storehouse.take(request.getMsgId());
    }

    public void close() {
        try {
            channel.closeFuture().sync();
            group.shutdownGracefully();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
