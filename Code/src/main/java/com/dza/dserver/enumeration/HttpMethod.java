package com.dza.dserver.enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    TRACE("TRACE"),
    CONNECT("CONNECT"),
    HEAD("HEAD"),
    UNKNOWN_METHOD(null),
    OPTIONS("OPTIONS");
    private String method;
    HttpMethod(String method){
        this.method = method;
    }
}
