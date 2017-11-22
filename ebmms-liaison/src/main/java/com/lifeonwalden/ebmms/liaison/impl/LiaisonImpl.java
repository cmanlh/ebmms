package com.lifeonwalden.ebmms.liaison.impl;

import com.lifeonwalden.ebmms.client.Client;
import com.lifeonwalden.ebmms.client.impl.ClientPool;
import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.bean.Response;
import com.lifeonwalden.ebmms.common.bean.register.ProducerServiceBean;
import com.lifeonwalden.ebmms.common.bean.register.ServiceProducerBean;
import com.lifeonwalden.ebmms.common.bean.register.TcpServiceBean;
import com.lifeonwalden.ebmms.common.constant.ReturnCodeEnum;
import com.lifeonwalden.ebmms.common.util.ServiceUtil;
import com.lifeonwalden.ebmms.liaison.Liaison;
import com.lifeonwalden.ebmms.register.service.RegisterLiaison;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

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

    @Value(value = "${ebmms.server.host ?:127.0.0.1}")
    private String host;

    @Value(value = "${ebmms.server.port ?:9600}")
    private int port;

    @Value(value = "${ebmms.heartbeat.gap ?:5000}")
    private int heartbeatGapTime;

    private Map<String, List<Client>> serviceProducerCache = new HashMap<>();

    private Map<String, Client> producerCache = new HashMap<>();

    private Client client;

    @Override
    public List<Client> fetchProducer(String serviceName) {
        return serviceProducerCache.get(serviceName);
    }

    @Override
    public void registerProducer(List<TcpServiceBean> serviceList) {
        ProducerServiceBean producerServiceBean = new ProducerServiceBean();
        producerServiceBean.setHost(this.host);
        producerServiceBean.setPort(this.port);
        producerServiceBean.setServiceList(serviceList);
        producerServiceBean.setHearbeatGapTime(this.heartbeatGapTime);

        Request request = new Request();
        request.setMethod("establish").setService(ServiceUtil.fetchServiceName(RegisterLiaison.class.getName(), 0)).setParameters(new Object[]{producerServiceBean});
        Response response = this.client.send(request);

        if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
            refreshProducerCache((Map<String, List<ServiceProducerBean>>) response.getResult());
        } else {
            throw new RuntimeException("Failed to setup connection with service register, for ".concat(response.getErrMsg()));
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.client = new ClientPool(this.registerHost, this.registerPort, this.coreSize, this.maxSize, this.idleSize, this.timeoutSeconds);
    }

    @Override
    public void destroy() throws Exception {
        this.client.close();
    }

    private void refreshProducerCache(Map<String, List<ServiceProducerBean>> producerMapping) {
        synchronized (serviceProducerCache) {
            TreeSet<String> availableProducer = new TreeSet<>();

            producerMapping.forEach((serviceName, producerList) -> {
                List<Client> _connectionList = serviceProducerCache.get(serviceName);
                if (null == _connectionList) {
                    _connectionList = new ArrayList<>();
                    serviceProducerCache.put(serviceName, _connectionList);
                }

                final List<Client> connectionList = _connectionList;
                producerList.forEach(producer -> {
                    String hostName = ServiceUtil.fetchHostName(producer.getHost(), producer.getPort());
                    availableProducer.add(hostName);
                    Client client = producerCache.get(hostName);
                    if (null == client) {
                        client = new ClientPool(producer.getHost(), producer.getPort());
                        producerCache.put(hostName, client);
                    }
                    if (!connectionList.contains(client)) {
                        connectionList.add(client);
                    }
                });
            });

            producerCache.forEach((hostName, producer) -> {
                if (!availableProducer.contains(hostName)) {
                    producerCache.remove(hostName);
                    serviceProducerCache.forEach((serviceName, producerList) -> {
                        producerList.remove(producer);
                    });
                }
            });
        }
    }
}
