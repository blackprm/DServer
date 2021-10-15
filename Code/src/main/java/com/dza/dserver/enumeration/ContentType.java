package com.dza.dserver.enumeration;

public enum ContentType {
    JSON("JSON"),
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
