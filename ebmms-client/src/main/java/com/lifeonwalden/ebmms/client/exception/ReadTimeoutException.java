package com.lifeonwalden.ebmms.client.exception;

import io.netty.channel.ChannelException;

public class ReadTimeoutException extends ChannelException {
    public ReadTimeoutException(String message) {
        super(message);
    }
}
