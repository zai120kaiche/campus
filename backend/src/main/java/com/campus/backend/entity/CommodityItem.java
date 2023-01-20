package com.campus.backend.entity;

import lombok.Data;

@Data
public class CommodityItem extends Commodity{
    private String universityName;
    private String userName;
    private boolean collectFlag=false;
    public CommodityItem(Commodity commodity)
    {
        super(commodity);
    }
}
