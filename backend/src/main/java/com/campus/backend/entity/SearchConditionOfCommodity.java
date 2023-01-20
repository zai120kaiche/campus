package com.campus.backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SearchConditionOfCommodity {
    private Integer uid;  //用户id，可以为空，用来判断当前内容是否收藏（有交易意图）
    private Integer type;
    private Integer school;
    private List<String> keyWord;
    private Integer current;    //指定页码，非空
    private Boolean tradeFlag;   //true表示卖，false表示买，可为空
}
