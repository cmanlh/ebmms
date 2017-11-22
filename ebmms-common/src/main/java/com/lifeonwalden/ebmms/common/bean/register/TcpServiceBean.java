package com.lifeonwalden.ebmms.common.bean.register;

import java.io.Serializable;

/**
 * service information bean
 */
public class TcpServiceBean implements Serializable {
    private static final long serialVersionUID = -4511599476707702960L;

    /**
     * global id for service, handled by register
     */
    private long id;

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
     * host of service
     */
    private String host;

    /**
     * port of service
     */
    private int port;

    /**
     * is the service available
     */
    private boolean available = true;

    public long getId() {
        return id;
    }

    public TcpServiceBean setId(long id) {
        this.id = id;

        return this;
    }

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

    public String getHost() {
        return host;
    }

    public TcpServiceBean setHost(String host) {
        this.host = host;

        return this;
    }

    public int getPort() {
        return port;
    }

    public TcpServiceBean setPort(int port) {
        this.port = port;

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