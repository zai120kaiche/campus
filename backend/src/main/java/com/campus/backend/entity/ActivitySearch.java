package com.campus.backend.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class ActivitySearch implements Serializable {
    /*
    * 1：志愿服务
    * 2：校园活动
    * */
    private Integer type;
    /*
    * 1：按照分类排序
    * 2：按照关键字排序
    * */
    private Integer order;
    private String keyWord;
}
