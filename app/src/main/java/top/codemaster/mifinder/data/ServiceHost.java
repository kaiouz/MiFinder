package top.codemaster.mifinder.data;

import java.net.InetAddress;
import java.util.Objects;

public class ServiceHost {

    private String name;

    private String ip;

    private int port;

    public ServiceHost(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
    }


    public ServiceHost(String name, InetAddress ip, int port) {
        this.name = name;
        this.ip = ip.getHostAddress();
        this.port = port;
    }


    public String getUrl() {
        return "http://" + ip + ":" + port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceHost that = (ServiceHost) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("%s %s", name, getUrl());
    }
}
