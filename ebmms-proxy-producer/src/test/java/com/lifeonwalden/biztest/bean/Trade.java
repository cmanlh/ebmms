package com.lifeonwalden.biztest.bean;

import java.math.BigDecimal;
import java.util.*;

public class Trade implements Map<String, Object> {
    protected Map<String, Object> dataMap = new HashMap<String, Object>();

    @Override
    public int size() {
        return dataMap.size();
    }

    @Override
    public boolean isEmpty() {
        return dataMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return dataMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object key) {
        return dataMap.containsValue(key);
    }

    @Override
    public Object get(Object key) {
        return dataMap.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return dataMap.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return dataMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        dataMap.putAll(m);
    }

    @Override
    public void clear() {
        dataMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return dataMap.keySet();
    }

    @Override
    public Collection<Object> values() {
        return dataMap.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return dataMap.entrySet();
    }

    /**
     * 产品编码
     */
    public String getCode() {
        Object val = dataMap.get("code");
        if (null == val) {
            return null;
        }
        return (String) val;
    }

    /**
     * 产品编码
     */
    public Trade setCode(String code) {
        dataMap.put("code", code);
        return this;
    }

    /**
     * 财富类型
     */
    public Integer getType() {
        Object val = dataMap.get("type");
        if (null == val) {
            return null;
        }
        return (Integer) val;
    }

    /**
     * 财富类型
     */
    public Trade setType(Integer type) {
        dataMap.put("type", type);
        return this;
    }

    /**
     * 交易日
     */
    public Date getTradeDate() {
        Object val = dataMap.get("tradeDate");
        if (null == val) {
            return null;
        }
        return (Date) val;
    }

    /**
     * 交易日
     */
    public Trade setTradeDate(Date tradeDate) {
        dataMap.put("tradeDate", tradeDate);
        return this;
    }

    /**
     * 交易金额
     */
    public BigDecimal getTradeAmt() {
        Object val = dataMap.get("tradeAmt");
        if (null == val) {
            return null;
        }
        return (BigDecimal) val;
    }

    /**
     * 交易金额
     */
    public Trade setTradeAmt(BigDecimal tradeAmt) {
        dataMap.put("tradeAmt", tradeAmt);
        return this;
    }
}
