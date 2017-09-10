package com.lifeonwalden.ebmms.proxy.serviceimpl;

import com.lifeonwalden.ebmms.common.annotation.TcpService;
import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.bean.Response;

@TcpService(name = "com.lifeonwalden.ebmms.proxy.service.test")
public class Test implements com.lifeonwalden.ebmms.proxy.service.Test {
    @Override
    @TcpService(name = "update")
    public Response update(Request request) {
        return new Response().setMsgId(request.getMsgId());
    }
}
