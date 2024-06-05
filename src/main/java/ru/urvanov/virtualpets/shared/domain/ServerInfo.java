package ru.urvanov.virtualpets.shared.domain;

import java.io.Serializable;

public class ServerInfo implements Serializable {
    private static final long serialVersionUID = -7056060003187438749L;
    public String address;
    public String locale;
    public String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return address + " " + locale + " " + name;

    }
}
