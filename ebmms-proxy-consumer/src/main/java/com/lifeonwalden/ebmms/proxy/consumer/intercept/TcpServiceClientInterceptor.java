package com.lifeonwalden.ebmms.proxy.consumer.intercept;

import com.lifeonwalden.ebmms.client.Client;
import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.bean.Response;
import com.lifeonwalden.ebmms.common.constant.ReturnCodeEnum;
import com.lifeonwalden.ebmms.liaison.Liaison;
import com.lifeonwalden.ebmms.proxy.consumer.ClientProxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TcpServiceClientInterceptor implements MethodInterceptor, ClientProxy {
    private final static Logger logger = LogManager.getLogger(TcpServiceClientInterceptor.class);

    private Liaison liaison;
    private String serviceName;
    private AtomicInteger counter = new AtomicInteger(0);
    private int maxRetryTimes;
    private int timeoutSeconds;

    public TcpServiceClientInterceptor(String serviceName, int maxRetryTimes, int timeoutSeconds, Liaison liaison) {
        this.liaison = liaison;
        this.serviceName = serviceName;
        this.maxRetryTimes = maxRetryTimes;
        this.timeoutSeconds = timeoutSeconds;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Request request = new Request();
        request.setMethod(method.getName()).setService(this.serviceName);
        request.setTimeoutSeconds(this.timeoutSeconds);
        Object[] arguments = invocation.getArguments();
        if (null != arguments && arguments.length > 0) {
            request.setParameters(arguments);
        }

        int retryTime = 0;
        while (retryTime++ <= this.maxRetryTimes) {
            List<? extends Client> producerList = liaison.fetchProducer(this.serviceName);
            int currentSize = producerList.size();
            if (currentSize > 0) {
                Client client = producerList.get(counter.getAndIncrement() % currentSize);
                Response response = client.send(request);
                if (ReturnCodeEnum.SUCCESS.getValue() == response.getReturnCode()) {
                    return response.getResult();
                }
            }
        }

        throw new RuntimeException("Failed to call remote service ".concat(this.serviceName));
    }
}
