package com.campus.backend.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class Pages {
    private long total;  //总计数据条数
    private long size; //页大小
    private long current; //当前页码
    private long pages;  //总计页数

    public void setInfo(IPage page)
    {
        this.setPages(page.getPages());
        this.setCurrent(page.getCurrent());
        this.setSize(page.getSize());
        this.setPages(page.getPages());
    }
}
