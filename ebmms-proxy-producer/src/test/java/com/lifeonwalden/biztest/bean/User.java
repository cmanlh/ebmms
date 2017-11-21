package com.lifeonwalden.biztest.bean;

import java.util.*;

public class User implements Map<String, Object> {
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
     * 姓名
     */
    public String getName() {
        Object val = dataMap.get("name");
        if (null == val) {
            return null;
        }
        return (String) val;
    }

    /**
     * 姓名
     */
    public User setName(String name) {
        dataMap.put("name", name);
        return this;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        Object val = dataMap.get("createTime");
        if (null == val) {
            return null;
        }
        return (Date) val;
    }

    /**
     * 创建时间
     */
    public User setCreateTime(Date createTime) {
        dataMap.put("createTime", createTime);
        return this;
    }
}
