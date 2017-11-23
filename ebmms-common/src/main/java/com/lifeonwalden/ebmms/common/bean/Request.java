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
     * remote service
     */
    private String service;

    /**
     * remote operation method
     */
    private String method;

    /**
     * request parameter
     */
    private Object[] parameters;

    /**
     * seconds to timeout
     */
    private int timeoutSeconds;

    public String getMsgId() {
        return msgId;
    }

    public Request setMsgId(String msgId) {
        this.msgId = msgId;

        return this;
    }

    public String getService() {
        return service;
    }

    public Request setService(String service) {
        this.service = service;

        return this;
    }

    public String getMethod() {
        return method;
    }

    public Request setMethod(String method) {
        this.method = method;

        return this;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public Request setParameters(Object[] parameters) {
        this.parameters = parameters;

        return this;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }
}
