package com.lifeonwalden.ebmms.proxy.serviceimpl;

import com.lifeonwalden.ebmms.common.annotation.TcpInject;
import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.bean.Response;
import com.lifeonwalden.ebmms.proxy.service.RemoteService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service(value = "testImpl")
public class Test implements com.lifeonwalden.ebmms.proxy.service.Test, InitializingBean {
    @TcpInject
    private RemoteService remoteService;

    @Override
    public Response update(Request request) {
        remoteService.update(new Request().setMsgId("hello world"));
        return new Response().setMsgId(request.getMsgId());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("pass");
    }
}
