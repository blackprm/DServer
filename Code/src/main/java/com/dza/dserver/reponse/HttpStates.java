package com.dza.dserver.reponse;

public enum HttpStates {
    OK(200),
    NOT_FOUND(404),
    NOT_MODIFIED(304);


    private int code;
    HttpStates(int code){
        this.code = code;
    }
}
