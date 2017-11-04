package com.lifeonwalden.ebmms.client;

import com.lifeonwalden.biztest.RemoteService;
import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.concurrent.MsgStorehouse;
import org.junit.Test;

public class ClientImplTest {
    @Test
    public void requestTest() {
        try {
            ClientImpl clientImpl = new ClientImpl("localhost", 8080, new MsgStorehouse(1024));
            Request request = new Request();
            request.setMethod("getName");
            request.setService(RemoteService.class.getName().concat("0"));
            System.out.println(clientImpl.send(request).getErrMsg());
            clientImpl.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
