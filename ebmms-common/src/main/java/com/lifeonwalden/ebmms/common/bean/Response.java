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
    private byte[] returnVal;

    /**
     * error message when exception happened
     */
    private String errMsg;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public byte[] getReturnVal() {
        return returnVal;
    }

    public void setReturnVal(byte[] returnVal) {
        this.returnVal = returnVal;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
