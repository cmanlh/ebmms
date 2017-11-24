package com.lifeonwalden;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Unit test for simple App.
 */
public class ConsumerTest {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath*:app.xml");
    }
}
