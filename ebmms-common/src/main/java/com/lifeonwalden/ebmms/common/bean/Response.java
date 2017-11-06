package com.lifeonwalden.ebmms.common.bean;

import java.io.Serializable;

/**
 * response from remote service call
 */
public class Response implements Serializable {
    private static final long serialVersionUID = 8760591568880178199L;

    /**
     * message id
     */
    private String msgId;

    /**
     * the return value
     */
    private Object result;

    /**
     * return code
     */
    private int returnCode;

    /**
     * error message when exception happened
     */
    private String errMsg;

    public String getMsgId() {
        return msgId;
    }

    public Response setMsgId(String msgId) {
        this.msgId = msgId;

        return this;
    }

    public Object getResult() {
        return result;
    }

    public Response setResult(Object result) {
        this.result = result;

        return this;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public Response setErrMsg(String errMsg) {
        this.errMsg = errMsg;

        return this;
    }
}
