package com.campus.backend.entity;

import lombok.Data;

import java.util.List;

@Data
public class ActivitySignInfo {
    private List<ActivitySign> signs;
    private Boolean isSign;
}
