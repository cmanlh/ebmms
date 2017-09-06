package com.lifeonwalden.ebmms.common.bean.register;

import java.io.Serializable;

/**
 * service information bean
 */
public class TcpServiceBean implements Serializable {
    private static final long serialVersionUID = -1404424653678764305L;
    /**
     * service name
     */
    private String name;

    /**
     * service version
     */
    private int version;

    /**
     * description for service
     */
    private String description;

    /**
     * global id for service, handled by register
     */
    private long id;

    /**
     * address of service
     */
    private String address;

    /**
     * is the service available
     */
    private boolean available;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}