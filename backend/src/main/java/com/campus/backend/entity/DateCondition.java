package com.campus.backend.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DateCondition {
    private List<Integer> uids;
    private LocalDate begin;
    private LocalDate end;
    private Integer limit;
}
