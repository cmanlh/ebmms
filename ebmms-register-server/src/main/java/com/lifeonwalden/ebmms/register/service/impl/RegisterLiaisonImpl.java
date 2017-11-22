package com.lifeonwalden.ebmms.register.service.impl;

import com.lifeonwalden.ebmms.common.annotation.TcpService;
import com.lifeonwalden.ebmms.common.bean.register.*;
import com.lifeonwalden.ebmms.common.util.ServiceUtil;
import com.lifeonwalden.ebmms.register.bean.ServiceHostInfoBean;
import com.lifeonwalden.ebmms.register.service.RegisterLiaison;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 * security issue here, host cheat
 * unsafe lock way on intern servicename
 */
@Component
@TcpService(serviceInterface = RegisterLiaison.class)
public class RegisterLiaisonImpl implements RegisterLiaison {
    private final static Logger logger = LogManager.getLogger(RegisterLiaisonImpl.class);

    private Map<String, List<ServiceProducerBean>> serviceProviderMapping = new HashMap<>();

    private Map<String, ServiceHostInfoBean> serviceHostInfoMapping = new HashMap<>();

    public Map<String, List<ServiceProducerBean>> establish(ProducerServiceBean serviceProviderInfo) {
        List<TcpServiceBean> serviceBeanList = serviceProviderInfo.getServiceList();
        if (null != serviceBeanList && serviceBeanList.size() > 0) {
            serviceBeanList.forEach(tcpServiceBean -> {
                tcpServiceBean.setHost(serviceProviderInfo.getHost());
                tcpServiceBean.setPort(serviceProviderInfo.getPort());

                ServiceProducerBean serviceProvider = new ServiceProducerBean();
                serviceProvider.setHost(serviceProviderInfo.getHost());
                serviceProvider.setPort(serviceProviderInfo.getPort());
                String serviceName = ServiceUtil.fetchServiceName(tcpServiceBean.getServiceInterface(), tcpServiceBean.getVersion()).intern();
                if (!serviceProviderMapping.containsKey(serviceName)) {
                    synchronized (serviceProviderMapping) {
                        if (!serviceProviderMapping.containsKey(serviceName)) {
                            List<ServiceProducerBean> serviceProducerBeanList = new ArrayList<>();
                            serviceProviderMapping.put(serviceName, serviceProducerBeanList);
                        }
                    }
                }
                List<ServiceProducerBean> serviceProducerBeanList = serviceProviderMapping.get(serviceName);
                synchronized (serviceName) {
                    if (!serviceProducerBeanList.contains(serviceProvider)) {
                        serviceProducerBeanList.add(serviceProvider);
                    }
                }
            });
        }

        String hostName = ServiceUtil.fetchHostName(serviceProviderInfo.getHost(), serviceProviderInfo.getPort()).intern();
        hostInfoProcess(hostName, serviceProviderInfo.getHearbeatGapTime(), serviceProviderInfo.getServiceList(), null);

        return this.serviceProviderMapping;
    }

    @Override
    public Map<String, List<ServiceProducerBean>> heartbeat(List<ServiceStatisticBean> statisticBeanList) {
        return this.serviceProviderMapping;
    }

    @Override
    public List<ServiceProducerBean> fetchServiceProvider(ServiceConsumerBean consumerBean) {
        hostInfoProcess(consumerBean.getHostName(), -1, null, consumerBean.getServiceName());

        return this.serviceProviderMapping.get(consumerBean.getServiceName());
    }

    private void hostInfoProcess(String hostName, int heartBitGapTime, List<TcpServiceBean> serviceBeanList, String consumerService) {
        if (!serviceHostInfoMapping.containsKey(hostName)) {
            synchronized (serviceHostInfoMapping) {
                if (!serviceHostInfoMapping.containsKey(hostName)) {
                    serviceHostInfoMapping.put(hostName, new ServiceHostInfoBean());
                }
            }
        }
        ServiceHostInfoBean serviceHostInfo = serviceHostInfoMapping.get(hostName);
        synchronized (hostName.intern()) {
            serviceHostInfo.setLastHeartbeatTime(System.currentTimeMillis());
            if (null != serviceBeanList) {
                serviceHostInfo.setProduceServiceList(serviceBeanList);
            }
            if (-1 != heartBitGapTime) {
                serviceHostInfo.setHeartbeatGapTime(heartBitGapTime);
            }
            if (null != consumerService) {
                List<String> consumerServiceList = serviceHostInfo.getConsumerServiceList();
                if (null == consumerServiceList) {
                    consumerServiceList = new ArrayList<>();
                    serviceHostInfo.setConsumerServiceList(consumerServiceList);
                }
                if (!consumerServiceList.contains(consumerService)) {
                    consumerServiceList.add(consumerService);
                }
            }
        }
    }
}
