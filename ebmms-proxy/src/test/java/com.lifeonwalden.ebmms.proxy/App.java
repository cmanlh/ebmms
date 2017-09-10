package com.lifeonwalden.ebmms.proxy;

import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.proxy.intercept.TcpServiceClientInterceptor;
import com.lifeonwalden.ebmms.proxy.service.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class App {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(App.class);

        Test test = ProxyFactory.getProxy(Test.class, new TcpServiceClientInterceptor());
        System.out.println(test.update(new Request()).getMsgId());
    }
}
