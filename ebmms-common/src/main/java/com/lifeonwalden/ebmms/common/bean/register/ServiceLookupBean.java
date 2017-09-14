package com.lifeonwalden.ebmms.common.bean.register;

import java.io.Serializable;
import java.util.List;

public class ServiceLookupBean implements Serializable {
    private static final long serialVersionUID = -7674942474439419743L;

    private List<String> serviceList;

    public List<String> getServiceList() {
        return serviceList;
    }

    public ServiceLookupBean setServiceList(List<String> serviceList) {
        this.serviceList = serviceList;

        return this;
    }
}
