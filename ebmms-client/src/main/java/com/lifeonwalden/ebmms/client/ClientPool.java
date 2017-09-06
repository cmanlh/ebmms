package com.lifeonwalden.ebmms.client;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Netty Client connection pooling
 */
public class ClientPool {
    /**
     * allowed maximum size of client
     */
    private int maxSize = 1;

    /**
     * allowed core size of client
     */
    private int coreSize = 1;

    /**
     * allowed idle size of client
     */
    private int idleSize = 1;

    /**
     * current size of alive client
     */
    private AtomicInteger aliveSize;



    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(int coreSize) {
        this.coreSize = coreSize;
    }

    public int getIdleSize() {
        return idleSize;
    }

    public void setIdleSize(int idleSize) {
        this.idleSize = idleSize;
    }

    public int getAliveSize() {
        return aliveSize.get();
    }
}
