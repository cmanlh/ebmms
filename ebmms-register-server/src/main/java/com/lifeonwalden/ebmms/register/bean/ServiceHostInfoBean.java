package com.lifeonwalden.ebmms.register.bean;

import com.lifeonwalden.ebmms.common.bean.register.TcpServiceBean;

import java.io.Serializable;
import java.util.List;

public class ServiceHostInfoBean implements Serializable {
    private static final long serialVersionUID = 3931398716146567927L;

    private boolean available = true;

    private long lastHeartbeatTime;

    private List<TcpServiceBean> produceServiceList;

    private List<String> consumerServiceList;

    private int heartbeatGapTime;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public long getLastHeartbeatTime() {
        return lastHeartbeatTime;
    }

    public void setLastHeartbeatTime(long lastHeartbeatTime) {
        this.lastHeartbeatTime = lastHeartbeatTime;
    }

    public List<TcpServiceBean> getProduceServiceList() {
        return produceServiceList;
    }

    public void setProduceServiceList(List<TcpServiceBean> produceServiceList) {
        this.produceServiceList = produceServiceList;
    }

    public List<String> getConsumerServiceList() {
        return consumerServiceList;
    }

    public void setConsumerServiceList(List<String> consumerServiceList) {
        this.consumerServiceList = consumerServiceList;
    }

    public int getHeartbeatGapTime() {
        return heartbeatGapTime;
    }

    public void setHeartbeatGapTime(int heartbeatGapTime) {
        this.heartbeatGapTime = heartbeatGapTime;
    }
}
