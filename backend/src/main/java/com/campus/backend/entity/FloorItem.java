package com.campus.backend.entity;

import lombok.Data;

@Data
public class FloorItem extends Floor{
    private String userName;
    private boolean likeFlag=false;

    public FloorItem(Floor floor)
    {
        super(floor);
    }
}
