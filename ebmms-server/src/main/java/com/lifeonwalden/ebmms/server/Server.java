package com.lifeonwalden.ebmms.server;

import com.lifeonwalden.ebmms.common.codec.RequestDecoder;
import com.lifeonwalden.ebmms.common.codec.ResponseEncoder;
import com.lifeonwalden.ebmms.proxy.TcpServiceDiscovery;
import com.lifeonwalden.ebmms.server.handler.MsgProcessor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Server implements InitializingBean, DisposableBean {
    private final static Logger logger = LogManager.getLogger(Server.class);

    @Value(value = "${ebmms.server.boss.count ?:1}")
    private int bossThreadCount;

    @Value(value = "${ebmms.server.worker.count ?:20}")
    private int workerThreadCount;

    @Value(value = "${ebmms.server.worker.queue ?:128}")
    private int workerBackLogSize;

    @Value(value = "${ebmms.server.host ?:127.0.0.1}")
    private String host;

    @Value(value = "${ebmms.server.port ?:8080}")
    private int port;

    @Autowired
    private TcpServiceDiscovery tcpServiceDiscovery;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private Channel channel;

    @Override
    public void afterPropertiesSet() throws Exception {
        bossGroup = new NioEventLoopGroup(this.bossThreadCount);
        workerGroup = new NioEventLoopGroup(this.workerThreadCount);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 8, 0, 8), new RequestDecoder())
                                .addLast(new LengthFieldPrepender(8), new ResponseEncoder())
                                .addLast(new MsgProcessor(tcpServiceDiscovery));
                    }
                })
                .option(ChannelOption.SO_BACKLOG, this.workerBackLogSize)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        // Bind and start to accept incoming connections.
        ChannelFuture f = bootstrap.bind(this.host, this.port).sync();
        channel = f.channel();
    }

    @Override
    public void destroy() throws Exception {
        channel.close();
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
