package com.lifeonwalden.ebmms.proxy.consumer;

import com.lifeonwalden.ebmms.client.Client;

import java.util.List;

public interface ClientProxy {
    void refresh(List<? extends Client> clients);
}
