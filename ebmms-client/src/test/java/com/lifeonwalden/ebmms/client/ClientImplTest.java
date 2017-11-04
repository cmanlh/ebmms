package com.lifeonwalden.ebmms.client;

import com.lifeonwalden.biztest.RemoteService;
import com.lifeonwalden.biztest.bean.Trade;
import com.lifeonwalden.biztest.bean.User;
import com.lifeonwalden.ebmms.common.bean.Request;
import com.lifeonwalden.ebmms.common.concurrent.MsgStorehouse;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ClientImplTest {
    @Test
    public void getStringTest() {
        try {
            ClientImpl clientImpl = new ClientImpl("localhost", 8080, new MsgStorehouse(1024));
            Request request = new Request();
            request.setService(RemoteService.class.getName().concat("0"));
            request.setMethod("getName");
            request.setService(RemoteService.class.getName().concat("0"));
            System.out.println(clientImpl.send(request).getResult());
            clientImpl.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void passStringTest() {
        try {
            ClientImpl clientImpl = new ClientImpl("localhost", 8080, new MsgStorehouse(1024));
            Request request = new Request();
            request.setService(RemoteService.class.getName().concat("0"));
            request.setMethod("rememberMyName");
            Object[] parameters = new Object[]{"tcp service called"};
            request.setParameters(parameters);
            System.out.println(clientImpl.send(request).getResult());
            clientImpl.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getListStringTest() {
        try {
            ClientImpl clientImpl = new ClientImpl("localhost", 8080, new MsgStorehouse(1024));
            Request request = new Request();
            request.setService(RemoteService.class.getName().concat("0"));
            request.setMethod("getList");
            request.setParameters(null);
            for (String word : (List<String>) clientImpl.send(request).getResult()) {
                System.out.println(word);
            }
            clientImpl.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void mulityParametersTest() {
        try {
            ClientImpl clientImpl = new ClientImpl("localhost", 8080, new MsgStorehouse(1024));
            Request request = new Request();
            request.setService(RemoteService.class.getName().concat("0"));
            request.setMethod("mulityParameters");
            User user = new User();
            user.setName("John");
            user.setCreateTime(new Date());
            request.setParameters(new Object[]{"poet", user});
            clientImpl.send(request).getResult();
            clientImpl.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void complicatedMulityParametersTest() {
        try {
            ClientImpl clientImpl = new ClientImpl("localhost", 8080, new MsgStorehouse(1024));
            Request request = new Request();
            request.setService(RemoteService.class.getName().concat("0"));
            request.setMethod("mulityParameters");
            User user = new User();
            user.setName("John");
            user.setCreateTime(new Date());
            Trade trade = new Trade();
            trade.setCode("000001");
            trade.setTradeAmt(new BigDecimal("1234567890.0987654321"));
            trade.setTradeDate(new Date());
            trade.setType(1);
            request.setParameters(new Object[]{trade, user});
            List<Trade> tradeList = (List<Trade>) clientImpl.send(request).getResult();
            System.out.println(System.currentTimeMillis());
            Trade _trade = tradeList.get(0);
            System.out.println("the trade -- code : ".concat(_trade.getCode()).concat(" trade amount : ").concat(_trade.getTradeAmt().toPlainString()).concat(" date : ").concat(_trade.getTradeDate().toString()).concat(" type : ").concat(String.valueOf(_trade.getType())));
            clientImpl.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void invalidMethodSignTest() {
        try {
            ClientImpl clientImpl = new ClientImpl("localhost", 8080, new MsgStorehouse(1024));
            Request request = new Request();
            request.setService(RemoteService.class.getName().concat("0"));
            request.setMethod("mulityParameters");
            User user = new User();
            user.setName("John");
            user.setCreateTime(new Date());
            Trade trade = new Trade();
            trade.setCode("000001");
            trade.setTradeAmt(new BigDecimal("1234567890.0987654321"));
            trade.setTradeDate(new Date());
            trade.setType(1);
            request.setParameters(new Object[]{user, trade});
            System.out.println(clientImpl.send(request).getErrMsg());
            clientImpl.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
