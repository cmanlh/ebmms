package com.lifeonwalden.ebmms.common.bean.register;

import java.io.Serializable;

public class ServiceProducerBean implements Serializable {
    private static final long serialVersionUID = -7674942474439419743L;

    private String host;

    private int port;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (null == obj) {
            return false;
        }

        if (!(obj instanceof ServiceProducerBean)) {
            return false;
        }

        ServiceProducerBean _obj = (ServiceProducerBean) obj;
        if (((null == this.host && null == _obj.host) || this.host.equals(_obj.host)) && this.port == _obj.port) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return String.valueOf(this.port).concat(this.host).hashCode();
    }
}
