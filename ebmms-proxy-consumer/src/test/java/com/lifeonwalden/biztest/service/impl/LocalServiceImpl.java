package com.lifeonwalden.biztest.service.impl;

import com.lifeonwalden.biztest.bean.Trade;
import com.lifeonwalden.biztest.bean.User;
import com.lifeonwalden.biztest.service.LocalService;
import com.lifeonwalden.biztest.service.RemoteService;
import com.lifeonwalden.ebmms.common.annotation.TcpInject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class LocalServiceImpl implements LocalService, InitializingBean {

    @TcpInject(serviceInterface = RemoteService.class)
    private RemoteService remoteService;

    @TcpInject(serviceInterface = RemoteService.class, version = 1)
    private RemoteService remoteService2;

    @Override
    public void callRemoteService() {
        System.out.println(remoteService.getName());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        callRemoteService();
        AtomicInteger counter = new AtomicInteger(0);
        AtomicInteger failedCounter = new AtomicInteger(0);
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            final int index = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String msg = "msg".concat(String.valueOf(index));
                    try {
                        System.out.println(msg.concat(" -> ").concat(remoteService.rememberMyName(msg)));
                        counter.incrementAndGet();
                    } catch (Exception e) {
                        failedCounter.incrementAndGet();
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            threadList.add(thread);
        }

        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("end\n".concat(counter.toString()).concat("\n").concat(failedCounter.toString()));

        User user = new User();
        user.setName("John");
        user.setCreateTime(new Date());
        Trade trade = new Trade();
        trade.setCode("000001");
        trade.setTradeAmt(new BigDecimal("1234567890.0987654321"));
        trade.setTradeDate(new Date());
        trade.setType(1);
        remoteService2.mulityParameters(trade, user);
    }
}
