package com.lifeonwalden.ebmms.proxy.intercept;

import com.lifeonwalden.ebmms.common.bean.Response;
import com.lifeonwalden.ebmms.common.util.UUID;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class TcpServiceClientInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        System.out.println(method.getName());
        Object[] arguments = invocation.getArguments();
        if (null != arguments) {
            for (Object obj : arguments) {
                if (null != obj) {
                    System.out.println(obj.getClass().getName());
                }
            }
        }

        Response response = new Response();
        response.setMsgId(UUID.fetch());
        return response;
    }
}
