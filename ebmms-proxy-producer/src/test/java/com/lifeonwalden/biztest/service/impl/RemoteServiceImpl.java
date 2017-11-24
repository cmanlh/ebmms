package com.lifeonwalden.biztest.service.impl;

import com.lifeonwalden.biztest.bean.Trade;
import com.lifeonwalden.biztest.bean.User;
import com.lifeonwalden.biztest.service.RemoteService;
import com.lifeonwalden.ebmms.common.annotation.TcpService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@TcpService(serviceInterface = RemoteService.class, version = 0)
@Service
public class RemoteServiceImpl implements RemoteService {
    @Override
    public String getName() {
        return "hello world";
    }

    @Override
    public String rememberMyName(String name) {
        System.out.println("Get request ".concat(name).concat(" @ ").concat((new Date()).toString()));
        try {
            Thread.sleep(Math.round(Math.random() * 5) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return name;
    }

    @Override
    public List<String> getList() {
        List<String> wordList = new ArrayList<>();
        wordList.add("锄禾日当午");
        wordList.add("汗滴禾下土");
        wordList.add("谁知盘中餐");
        wordList.add("粒粒皆辛苦");

        return wordList;
    }

    @Override
    public void mulityParameters(String bookName, User author) {
        System.out.println("\n");
        System.out.println("The book name is ".concat(bookName));
        System.out.println("The author is ".concat(author.getName()).concat(" ").concat(author.getCreateTime().toString()));
    }

    @Override
    public List<Trade> mulityParameters(Trade trade, User user) {
        System.out.println("the trade -- code : ".concat(trade.getCode()).concat(" trade amount : ").concat(trade.getTradeAmt().toPlainString()).concat(" date : ").concat(trade.getTradeDate().toString()).concat(" type : ").concat(String.valueOf(trade.getType())));
        System.out.println("The author is ".concat(user.getName()).concat(" ").concat(user.getCreateTime().toString()));
        List<Trade> tradeList = new ArrayList<>();

        for (int i = 0; i < 500000; i++) {
            trade = new Trade();
            trade.setCode(String.valueOf(i));
            trade.setTradeAmt(new BigDecimal("1234567890.0987654321"));
            trade.setTradeDate(new Date());
            trade.setType(1);
            tradeList.add(trade);
        }

        System.out.println(System.currentTimeMillis());
        return tradeList;
    }
}
