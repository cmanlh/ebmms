package com.lifeonwalden.ebmms.client;

import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.bean.Response;

public interface Client {
    /**
     * send a request and get the response
     *
     * @param request
     * @return
     */
    Response send(Request request);

    /**
     * send a request and get the response in ${timeout} seconds
     *
     * @param request
     * @param timeout
     * @return
     */
    Response send(Request request, int timeout);

    /**
     * send a request and get the response in ${timeout} seconds, and allowed retry ${retry} times
     *
     * @param request
     * @param retry
     * @param timeout
     * @return
     */
    Response send(Request request, int retry, int timeout);

    String getFamilyName();

    boolean isActive();

    void close();
}
