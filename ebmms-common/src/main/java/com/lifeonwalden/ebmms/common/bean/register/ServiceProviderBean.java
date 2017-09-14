package com.lifeonwalden.ebmms.common.bean.register;

import java.io.Serializable;
import java.util.List;

public class ServiceProviderBean implements Serializable {
    private static final long serialVersionUID = -2477702117782870663L;

    private List<ServiceListBean> serviceListBeans;

    public List<ServiceListBean> getServiceListBeans() {
        return serviceListBeans;
    }

    public ServiceProviderBean setServiceListBeans(List<ServiceListBean> serviceListBeans) {
        this.serviceListBeans = serviceListBeans;

        return this;
    }
}
