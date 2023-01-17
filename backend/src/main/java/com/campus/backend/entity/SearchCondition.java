package com.campus.backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SearchCondition {
    private Integer uid;  //用户id，可以为空，用来判断当前内容是否点赞
    private Integer order; //非空
    private Integer type;
    private Integer school;
    private List<String> keyWord;
    private Integer current;    //指定页码，非空
}
