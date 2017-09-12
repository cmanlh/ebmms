package com.lifeonwalden.ebmms.proxy.service;

import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.bean.Response;

public interface RemoteService {
    Response update(Request request);
}
