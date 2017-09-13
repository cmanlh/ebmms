package com.lifeonwalden.ebmms.client;

import com.lifeonwalden.ebmms.common.bean.Request;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class ClientPoolTest {
    @Test
    public void requesTest() {
        ClientPool clientPool = new ClientPool("localhost", 8080, 1);
        Request request = new Request();
        request.setMethod("update");
        request.setService("com.lifeonwalden.remote.service.Test");
        System.out.println(clientPool.send(request).getErrMsg());
        clientPool.close();
    }

    @Test
    public void multiRequesTest() {
        ClientPool clientPool = new ClientPool("localhost", 8080, 10, 1024, 50);
        AtomicInteger counter = new AtomicInteger(0);
        AtomicInteger failedCounter = new AtomicInteger(0);
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Request request = new Request();
                    request.setMethod("update");
                    request.setService("com.lifeonwalden.remote.service.Test");
                    try {
                        clientPool.send(request);
                        counter.incrementAndGet();
                    } catch (Exception e) {
                        failedCounter.incrementAndGet();
                    }
                    try {
                        Thread.sleep(50);
                    } catch
                            (Exception e) {
                    }
                }
            }).start();
        }
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end\n".concat(counter.toString()).concat("\n").concat(failedCounter.toString()));
        clientPool.close();
    }
}
