package com.dza.dserver.reponse;

public enum HttpStates {
    OK(200),
    NOT_MODIFIED(304),
    NOT_FOUND(404),
    BAD_REQUEST(400),
    HTTP_VERSION_NOT_SUPPORT(505)
    ;


    private int code;
    HttpStates(int code){
        this.code = code;
    }
    public String getName(){
        return super.name();
    }
    public int getCode(){
        return code;
    }

}
