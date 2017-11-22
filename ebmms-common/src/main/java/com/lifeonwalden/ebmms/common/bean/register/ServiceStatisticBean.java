package com.lifeonwalden.ebmms.common.bean.register;

import java.io.Serializable;

public class ServiceStatisticBean implements Serializable {
    private static final long serialVersionUID = -855312748787079239L;

    private long start;

    private long end;

    private String host;

    private int port;

    private String service;

    private boolean success;

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
