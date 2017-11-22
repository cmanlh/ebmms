package com.lifeonwalden.ebmms.proxy.producer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProducerTest {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath*:app.xml");
    }
}
