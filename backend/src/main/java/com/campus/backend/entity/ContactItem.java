package com.campus.backend.entity;

import lombok.Data;

@Data
public class ContactItem {
    private Integer others;
    private String username;
    private String avatar;
    private boolean hasMessage;
}
