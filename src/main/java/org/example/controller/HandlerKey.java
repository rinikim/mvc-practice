package org.example.controller;

import org.example.annotation.RequestMethod;

import java.util.Objects;

public class HandlerKey {
    private final RequestMethod requestMethoe;
    private final String uriPath;

    public HandlerKey(RequestMethod requestMethod, String uriPath) {
        this.requestMethoe = requestMethod;
        this.uriPath = uriPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HandlerKey)) return false;
        HandlerKey that = (HandlerKey) o;
        return requestMethoe == that.requestMethoe && Objects.equals(uriPath, that.uriPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestMethoe, uriPath);
    }
}
