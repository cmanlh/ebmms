package com.lifeonwalden.ebmms.client.handler;

import com.lifeonwalden.ebmms.client.exception.ReadTimeoutException;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.Timer;
import java.util.TimerTask;

public class ReadTimeoutHandler extends ChannelDuplexHandler {
    private Timer timer;

    private int timeoutSeconds;

    private TimerTask timerTask;

    public ReadTimeoutHandler(int timeoutSeconds) {
        this.timer = new Timer();
        this.timeoutSeconds = timeoutSeconds;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.timerTask.cancel();
        super.channelRead(ctx, msg);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        super.flush(ctx);
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                ctx.fireExceptionCaught(new ReadTimeoutException("Time out for reading response from server"));
                ctx.close();
            }
        };
        this.timer.schedule(this.timerTask, this.timeoutSeconds * 1000);
    }
}
