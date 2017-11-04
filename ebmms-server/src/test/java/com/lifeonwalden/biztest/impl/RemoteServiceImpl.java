package com.lifeonwalden.biztest.impl;

import com.lifeonwalden.biztest.RemoteService;
import com.lifeonwalden.ebmms.common.annotation.TcpService;
import org.springframework.stereotype.Service;

@TcpService(serviceInterface = RemoteService.class)
@Service
public class RemoteServiceImpl implements RemoteService {
    @Override
    public String getName() {
        return "hello world";
    }

    @Override
    public void rememberMyName(String name) {
        System.out.println(name);
    }
}
