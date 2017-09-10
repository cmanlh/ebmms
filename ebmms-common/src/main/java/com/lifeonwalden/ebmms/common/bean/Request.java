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

    public Request setMsgId(String msgId) {
        this.msgId = msgId;

        return this;
    }

    public long getServiceId() {
        return serviceId;
    }

    public Request setServiceId(long serviceId) {
        this.serviceId = serviceId;

        return this;
    }

    public String getMethod() {
        return method;
    }

    public Request setMethod(String method) {
        this.method = method;

        return this;
    }

    public byte[] getParameter() {
        return parameter;
    }

    public Request setParameter(byte[] parameter) {
        this.parameter = parameter;

        return this;
    }
}
