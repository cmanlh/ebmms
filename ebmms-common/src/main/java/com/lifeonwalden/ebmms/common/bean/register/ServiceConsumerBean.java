package com.lifeonwalden.ebmms.common.bean.register;

import java.io.Serializable;

public class ServiceConsumerBean implements Serializable {
    private static final long serialVersionUID = 8122939374202397825L;

    private String hostName;

    private String serviceName;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
