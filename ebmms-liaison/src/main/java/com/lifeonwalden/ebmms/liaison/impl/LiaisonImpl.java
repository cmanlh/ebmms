package com.lifeonwalden.ebmms.liaison.impl;

import com.lifeonwalden.ebmms.client.Client;
import com.lifeonwalden.ebmms.client.impl.ClientPool;
import com.lifeonwalden.ebmms.common.bean.register.TcpServiceBean;
import com.lifeonwalden.ebmms.liaison.Liaison;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LiaisonImpl implements Liaison, InitializingBean, DisposableBean {
    private final static Logger logger = LogManager.getLogger(LiaisonImpl.class);

    @Value(value = "${ebmms.liaison.maxSize ?:1}")
    private int maxSize;

    @Value(value = "${ebmms.liaison.coreSize ?:1}")
    private int coreSize;

    @Value(value = "${ebmms.liaison.idleSize ?:1}")
    private int idleSize;

    @Value(value = "${ebmms.liaison.register.registerHost ?:127.0.0.1}")
    private String registerHost;

    @Value(value = "${ebmms.liaison.register.registerPort ?:9610}")
    private int registerPort;

    @Value(value = "${ebmms.liaison.timeoutSeconds ?:15}")
    private int timeoutSeconds;

    private Client client;

    @Override
    public List<? extends Client> fetchProducer(String serviceName) {
        return null;
    }

    @Override
    public void registerProducer(List<TcpServiceBean> serviceList) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.client = new ClientPool(this.registerHost, this.registerPort, this.coreSize, this.maxSize, this.idleSize, this.timeoutSeconds);
    }

    @Override
    public void destroy() throws Exception {
        this.client.close();
    }
}
