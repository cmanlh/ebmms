package com.lifeonwalden.ebmms.common.bean.register;

import java.io.Serializable;
import java.util.List;

public class ServiceListBean implements Serializable {
    private static final long serialVersionUID = 2801846335068972668L;

    private String host;

    private int port;

    private List<TcpServiceBean> serviceList;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<TcpServiceBean> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<TcpServiceBean> serviceList) {
        this.serviceList = serviceList;
    }
}
