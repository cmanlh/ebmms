package com.lifeonwalden.ebmms.common.bean.register;

import java.io.Serializable;

/**
 * service information bean
 */
public class TcpServiceBean implements Serializable {
    private static final long serialVersionUID = -1404424653678764305L;
    /**
     * the service interface class
     */
    private String serviceInterface;

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

    public String getServiceInterface() {
        return serviceInterface;
    }

    public TcpServiceBean setServiceInterface(String serviceInterface) {
        this.serviceInterface = serviceInterface;

        return this;
    }

    public int getVersion() {
        return version;
    }

    public TcpServiceBean setVersion(int version) {
        this.version = version;

        return this;
    }

    public String getDescription() {
        return description;
    }

    public TcpServiceBean setDescription(String description) {
        this.description = description;

        return this;
    }

    public long getId() {
        return id;
    }

    public TcpServiceBean setId(long id) {
        this.id = id;

        return this;
    }

    public String getAddress() {
        return address;
    }

    public TcpServiceBean setAddress(String address) {
        this.address = address;

        return this;
    }

    public boolean isAvailable() {
        return available;
    }

    public TcpServiceBean setAvailable(boolean available) {
        this.available = available;

        return this;
    }
}