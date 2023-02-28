package com.campus.backend.entity;

import lombok.Data;

import java.util.List;

@Data
public class SignInfo {
    private List<Sign> signs;
    private Boolean isSign;
    private Integer days;
}
