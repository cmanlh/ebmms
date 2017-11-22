package com.lifeonwalden.ebmms.proxy;

import com.lifeonwalden.ebmms.common.annotation.TcpService;
import com.lifeonwalden.ebmms.common.bean.register.TcpServiceBean;
import com.lifeonwalden.ebmms.common.util.ServiceUtil;
import com.lifeonwalden.ebmms.liaison.Liaison;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TcpServiceDiscovery implements ApplicationContextAware {
    @Autowired
    private Liaison liaison;

    private ApplicationContext appContext;

    private Map<String, Object> serviceIndex = new HashMap<>();

    private List<TcpServiceBean> serviceList = new ArrayList<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;

        final TcpServiceDiscovery that = this;
        that.appContext.getBeansWithAnnotation(TcpService.class).forEach((key, value) -> {
            TcpService service = that.appContext.findAnnotationOnBean(key, TcpService.class);
            TcpServiceBean tcpServiceBean = new TcpServiceBean();
            serviceList.add(tcpServiceBean.setDescription(service.description()).setVersion(service.version()).setServiceInterface(service.serviceInterface().getName()));
            serviceIndex.put(ServiceUtil.fetchServiceName(tcpServiceBean.getServiceInterface(), tcpServiceBean.getVersion()), value);
        });

        liaison.registerProducer(this.serviceList);
    }

    public Map<String, Object> getServiceIndex() {
        return serviceIndex;
    }

    public List<TcpServiceBean> getServiceList() {
        return serviceList;
    }
}
