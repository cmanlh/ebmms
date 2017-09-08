package com.lifeonwalden.ebmms.proxy.intercept;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TcpServiceClientInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println(invocation.getMethod().getName());
        System.out.println(invocation.getArguments().getClass().getName());
        System.out.println(invocation.getMethod().getName());
        System.out.println(invocation.getMethod().getName());
        System.out.println(invocation.getMethod().getName());
        System.out.println(invocation.getMethod().getName());

        return null;
    }
}
