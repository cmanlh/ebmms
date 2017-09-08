package com.lifeonwalden.ebmms.proxy;

import com.lifeonwalden.ebmms.common.annotation.TcpService;
import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.bean.Response;

@TcpService(name = "test")
public interface Test {
    public Response update(Request request);
}
