package com.lifeonwalden.ebmms.server.handler;

import com.lifeonwalden.ebmms.common.bean.register.TcpServiceBean;

import java.util.List;
import java.util.Map;

public interface TcpServiceDiscovery {
    Map<String, Object> getServiceIndex();

    List<TcpServiceBean> getServiceList();
}
