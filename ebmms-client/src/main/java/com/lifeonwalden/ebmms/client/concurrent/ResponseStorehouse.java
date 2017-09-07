package com.lifeonwalden.ebmms.client.concurrent;

import com.lifeonwalden.ebmms.common.bean.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseStorehouse {
    private final static Logger logger = LogManager.getLogger(ResponseStorehouse.class);

    private ConcurrentHashMap<String, BlockingQueue<Response>> storehouse;

    public ResponseStorehouse(int initialCapacity) {
        storehouse = new ConcurrentHashMap<>(1024);
    }

    public void buy(String msgId) {
        storehouse.putIfAbsent(msgId, new ArrayBlockingQueue<Response>(1));
    }

    public void put(String msgId, Response response) {
        BlockingQueue<Response> queue = storehouse.get(msgId);
        if (null == queue) {
            RuntimeException runtimeException = new RuntimeException("Can't find a message receiver.");
            logger.error(runtimeException);
            throw runtimeException;
        }
        queue.offer(response);
    }

    public Response take(String msgId) {
        BlockingQueue<Response> queue = storehouse.get(msgId);
        if (null == queue) {
            RuntimeException runtimeException = new RuntimeException("Can't find a message holder.");
            logger.error(runtimeException);
            throw runtimeException;
        }

        try {
            Response response = queue.take();
            return response;
        } catch (InterruptedException e) {
            RuntimeException re = new RuntimeException(e);
            logger.error("Fetch response failed.", re);
            throw re;
        } finally {
            storehouse.remove(msgId);
        }
    }
}
