package com.lifeonwalden.ebmms.proxy.consumer;

import com.lifeonwalden.ebmms.common.annotation.TcpInject;
import com.lifeonwalden.ebmms.common.util.ServiceUtil;
import com.lifeonwalden.ebmms.proxy.consumer.intercept.TcpServiceClientInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class TcpServiceProxyInjector implements BeanPostProcessor {
    private final static Logger logger = LogManager.getLogger(TcpServiceProxyInjector.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();

        Arrays.asList(fields).forEach(field -> {
            TcpInject tcpInject = field.getAnnotation(TcpInject.class);
            if (null != tcpInject) {
                //TODO
                String serviceName = ServiceUtil.fetchServiceName(tcpInject.serviceInterface().getName(),tcpInject.version());
                Object service = ProxyFactory.getProxy(field.getType(), new TcpServiceClientInterceptor(new ArrayList<>()));
                try {
                    ReflectionUtils.makeAccessible(field);
                    field.set(bean, service);
                } catch (IllegalAccessException e) {
                    logger.error("Tcp service inject failed.", e);

                    throw new RuntimeException(e);
                }
            }
        });

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
