package com.lifeonwalden.ebmms.common.concurrent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class MsgStorehouse<T> {
    private final static Logger logger = LogManager.getLogger(MsgStorehouse.class);

    private ConcurrentHashMap<String, BlockingQueue<T>> storehouse;

    public MsgStorehouse(int initialCapacity) {
        storehouse = new ConcurrentHashMap<>(initialCapacity);
    }

    public void buy(String msgId) {
        storehouse.putIfAbsent(msgId, new ArrayBlockingQueue<>(1));
    }

    public void put(String msgId, T msg) {
        BlockingQueue<T> queue = storehouse.get(msgId);
        if (null == queue) {
            RuntimeException runtimeException = new RuntimeException("Can't find a message receiver for ".concat(msgId));
            logger.error(runtimeException);
            throw runtimeException;
        }
        queue.offer(msg);
    }

    public T take(String msgId) {
        BlockingQueue<T> queue = storehouse.get(msgId);
        if (null == queue) {
            RuntimeException runtimeException = new RuntimeException("Can't find a message receiver for ".concat(msgId));
            logger.error(runtimeException);
            throw runtimeException;
        }

        try {
            T msg = queue.take();
            return msg;
        } catch (InterruptedException e) {
            RuntimeException re = new RuntimeException(e);
            logger.error("Fetch response failed.", re);
            throw re;
        } finally {
            storehouse.remove(msgId);
        }
    }
}
