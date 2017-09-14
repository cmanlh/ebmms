package com.lifeonwalden.ebmms.proxy;

import com.lifeonwalden.ebmms.client.Client;
import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.bean.Response;

public class ClientProxy implements Client {

    @Override
    public Response send(Request request) {
        return null;
    }

    @Override
    public Response send(Request request, int timeout) {
        return null;
    }

    @Override
    public Response send(Request request, int retry, int timeout) {
        return null;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void close() {

    }
}
