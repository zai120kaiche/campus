package com.campus.backend.entity;

import lombok.Data;

import java.util.List;

@Data
public class CommodityPages extends Pages{
    private List<CommodityItem> records;
}
