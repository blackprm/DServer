package com.dza.dserver.enumeration;


public enum ContentType {
    JSON("application/json"),
    TEXT("text/plain"),
    HTML("text/html"),
    JPG("image/jpeg"),
    JS("text/javascript"),
    PDF("application/pdf"),
    PNG("image/png"),
    MP3("audio/mpeg"),
    ICO("image/vnd.microsoft.icon"),
    Nil(null)
    ;
    private String type;
    ContentType(String type){
        this.type = type;
    }

    public boolean eq(String o){
        return type.equals(o);
    }

    @Override
    public String toString() {
        return type;
    }


}
