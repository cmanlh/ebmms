package com.lifeonwalden.ebmms.client;

import com.lifeonwalden.biztest.service.RemoteService;
import com.lifeonwalden.ebmms.client.impl.ClientPool;
import com.lifeonwalden.ebmms.common.bean.Request;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientPoolTest {
    @Test
    public void requesTest() {
        ClientPool clientPool = new ClientPool("localhost", 9600, 1);
        Request request = new Request();
        request.setService(RemoteService.class.getName().concat(":0"));
        request.setMethod("getName");
        System.out.println(clientPool.send(request).getResult());
        clientPool.close();
    }

    @Test
    public void multiRequesTest() {
        ClientPool clientPool = new ClientPool("localhost", 9600, 10, 1024, 50, 15);
        AtomicInteger counter = new AtomicInteger(0);
        AtomicInteger failedCounter = new AtomicInteger(0);
        Random random = new Random();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            final int index = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Request request = new Request();
                    request.setTimeoutSeconds(random.nextInt(15));
                    request.setService(RemoteService.class.getName().concat(":0"));
                    request.setMethod("rememberMyName");
                    String msg = "msg".concat(String.valueOf(index));
                    Object[] parameters = new Object[]{String.valueOf(msg)};
                    request.setParameters(parameters);
                    try {
                        System.out.println(msg.concat(" -> ").concat((String) clientPool.send(request).getResult()));
                        counter.incrementAndGet();
                    } catch (Exception e) {
                        failedCounter.incrementAndGet();
                    }
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
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
        clientPool.close();
    }
}
