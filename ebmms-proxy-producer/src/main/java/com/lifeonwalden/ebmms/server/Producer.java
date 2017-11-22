package com.lifeonwalden.ebmms.server;

import com.lifeonwalden.ebmms.proxy.TcpServiceDiscovery;
import com.lifeonwalden.ebmms.server.handler.MsgProcessor;
import com.lifeonwalden.ebmms.server.impl.ServerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Producer implements InitializingBean, DisposableBean {
    private final static Logger logger = LogManager.getLogger(Producer.class);

    @Value(value = "${ebmms.server.boss.count:#{1}}")
    private int bossThreadCount;

    @Value(value = "${ebmms.server.worker.count:#{20}}")
    private int workerThreadCount;

    @Value(value = "${ebmms.server.worker.queue:#{128}}")
    private int workerBackLogSize;

    @Value(value = "${ebmms.server.host:127.0.0.1}")
    private String host;

    @Value(value = "${ebmms.server.port:#{9600}}")
    private int port;

    @Autowired
    private TcpServiceDiscovery tcpServiceDiscovery;

    private Server server;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.server = new ServerImpl<>(this.bossThreadCount, this.workerThreadCount, this.host, this.port, this.workerBackLogSize, new MsgProcessor(this.tcpServiceDiscovery));
    }

    @Override
    public void destroy() throws Exception {
        this.server.close();
    }
}
