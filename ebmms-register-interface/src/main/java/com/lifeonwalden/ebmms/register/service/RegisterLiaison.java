package com.lifeonwalden.ebmms.register.service;

import com.lifeonwalden.ebmms.common.bean.register.ProducerServiceBean;
import com.lifeonwalden.ebmms.common.bean.register.ServiceConsumerBean;
import com.lifeonwalden.ebmms.common.bean.register.ServiceProducerBean;
import com.lifeonwalden.ebmms.common.bean.register.ServiceStatisticBean;

import java.util.List;
import java.util.Map;

public interface RegisterLiaison {
    Map<String, List<ServiceProducerBean>> establish(ProducerServiceBean serviceProviderInfo);

    Map<String, List<ServiceProducerBean>> heartbeat(List<ServiceStatisticBean> statisticBeanList);

    List<ServiceProducerBean> fetchServiceProvider(ServiceConsumerBean consumerBean);
}
