package com.dza.dserver.constant;

public enum FinalString {
    CRLF("\r\n");

    private String string;
    FinalString(String str){
        this.string = str;
    }

    public String getString() {
        return string;
    }
}
