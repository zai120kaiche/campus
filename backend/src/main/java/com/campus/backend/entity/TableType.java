package com.campus.backend.entity;

public enum TableType {
    post(0),
    floor(1),
    reply(2);


    private final int key;

    private TableType(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}
