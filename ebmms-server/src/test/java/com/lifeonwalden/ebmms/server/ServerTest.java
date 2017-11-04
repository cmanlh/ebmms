package com.lifeonwalden.ebmms.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("com.lifeonwalden")
public class ServerTest {
    public static void main(String[] args) throws Exception {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(ServerTest.class);
        System.out.println("Start Over");
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigure = new PropertySourcesPlaceholderConfigurer();

        return propertySourcesPlaceholderConfigure;
    }
}
