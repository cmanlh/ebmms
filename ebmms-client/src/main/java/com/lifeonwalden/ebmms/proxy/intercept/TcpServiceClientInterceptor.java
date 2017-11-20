package com.lifeonwalden.ebmms.proxy.intercept;

import com.lifeonwalden.ebmms.client.Client;
import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.bean.Response;
import com.lifeonwalden.ebmms.common.util.UUID;
import com.lifeonwalden.ebmms.proxy.ClientProxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.List;

public class TcpServiceClientInterceptor implements MethodInterceptor, ClientProxy {
    private List<? extends Client> clients;

    public TcpServiceClientInterceptor(List<? extends Client> clients) {
        this.clients = clients;
    }

    @Override
    public void refresh(List<? extends Client> clients) {
        this.clients = clients;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        System.out.println(method.getName());
        Object[] arguments = invocation.getArguments();
        if (null != arguments) {
            for (Object obj : arguments) {
                if (null != obj) {
                    System.out.println(obj.getClass().getName());
                    if (obj instanceof Request) {
                        System.out.println(((Request) obj).getMsgId());
                    }
                }
            }
        }

        Response response = new Response();
        response.setMsgId(UUID.fetch());
        return response;
    }
}
