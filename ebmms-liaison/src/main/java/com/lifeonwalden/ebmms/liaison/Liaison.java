package com.lifeonwalden.ebmms.liaison;

import com.lifeonwalden.ebmms.client.Client;
import com.lifeonwalden.ebmms.common.bean.register.TcpServiceBean;

import java.util.List;

public interface Liaison {
    List<? extends Client> fetchProducer(String serviceName);

    void registerProducer(List<TcpServiceBean> serviceList);
}
