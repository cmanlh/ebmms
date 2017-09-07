package com.lifeonwalden.ebmms.common.bean;

import java.io.Serializable;

/**
 * request for calling remote method
 */
public class Request implements Serializable {
    private static final long serialVersionUID = 8316181377929274990L;
    /**
     * message id
     */
    private String msgId;

    /**
     * remote service id
     */
    private long serviceId;

    /**
     * remote operation method
     */
    private String method;

    /**
     * request parameter
     */
    private byte[] parameter;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public byte[] getParameter() {
        return parameter;
    }

    public void setParameter(byte[] parameter) {
        this.parameter = parameter;
    }
}
