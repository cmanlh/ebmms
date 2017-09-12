package com.lifeonwalden.ebmms.client;

import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.bean.Response;
import com.lifeonwalden.ebmms.common.concurrent.MsgStorehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.FormattedMessage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Netty ClientImpl connection pooling
 */
public class ClientPool implements Client {
    private final static Logger logger = LogManager.getLogger(ClientPool.class);

    private BlockingQueue<Client> pool;

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

    /**
     * tcp service host
     */
    private String host;

    /**
     * tcp service port
     */
    private int port;

    private MsgStorehouse<Response> storehouse;

    public ClientPool(String host, int port) {
        this(host, port, 1, 1, 1);
    }

    public ClientPool(String host, int port, int coreSize) {
        this(host, port, coreSize, coreSize, coreSize);
    }

    public ClientPool(String host, int port, int coreSize, int maxSize, int idleSize) {
        this.host = host;
        this.port = port;
        this.coreSize = coreSize <= 0 ? this.coreSize : coreSize;
        this.maxSize = maxSize < this.coreSize ? this.coreSize : maxSize;
        this.idleSize = (idleSize < this.coreSize ? this.coreSize : idleSize);
        this.idleSize = this.idleSize > this.maxSize ? this.maxSize : this.idleSize;

        this.pool = new ArrayBlockingQueue<>(this.maxSize);
        this.storehouse = new MsgStorehouse(1024 > this.maxSize ? 1024 : this.maxSize);
        aliveSize = new AtomicInteger(0);
    }

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

    @Override
    public Response send(Request request) {
        Client client = pool.poll();
        try {
            if (null == client && aliveSize.incrementAndGet() <= this.maxSize) {
                try {
                    client = new ClientImpl(this.host, this.port, this.storehouse);
                } catch (InterruptedException e) {
                    aliveSize.decrementAndGet();

                    logger.error(new FormattedMessage("create a new client to {}:{} failed", this.host, this.port), e);

                    throw new RuntimeException(e);
                }
            }

            if (null != client) {
                return client.send(request);
            } else {
                throw new RuntimeException("no available connection.");
            }
        } finally {
            if (null != client) {
                try {
                    pool.put(client);
                } catch (InterruptedException e) {
                    logger.error("Failed to return connection back to pool", e);

                    aliveSize.decrementAndGet();
                    client.close();
                }
            }
        }
    }

    @Override
    public Response send(Request request, int timeout) {
        return send(request);
    }

    @Override
    public Response send(Request request, int retry, int timeout) {
        return send(request);
    }

    /**
     * TODO
     * <p>
     * not safe implementation, maybe forget to close some connection
     */
    @Override
    public void close() {
        pool.forEach(client -> {
            client.close();
            aliveSize.decrementAndGet();
        });
    }
}
