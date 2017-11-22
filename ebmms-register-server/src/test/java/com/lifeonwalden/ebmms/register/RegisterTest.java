package com.lifeonwalden.ebmms.register;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RegisterTest {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath*:app.xml");
    }
}
