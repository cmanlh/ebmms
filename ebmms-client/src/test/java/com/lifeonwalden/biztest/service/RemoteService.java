package com.lifeonwalden.biztest.service;

import com.lifeonwalden.biztest.bean.Trade;
import com.lifeonwalden.biztest.bean.User;

import java.util.List;

public interface RemoteService {
    String getName();

    void rememberMyName(String name);

    List<String> getList();

    void mulityParameters(String bookName, User user);

    List<Trade> mulityParameters(Trade trade, User user);
}
