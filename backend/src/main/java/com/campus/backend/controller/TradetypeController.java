package com.campus.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.Tradetype;
import com.campus.backend.entity.Type;
import com.campus.backend.service.TradetypeService;
import com.campus.backend.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author campus
 * @since 2023-01-14
 */
@RestController
@RequestMapping("/tradetype")
public class TradetypeController {
    @Autowired
    TradetypeService tradetypeService;

    //分页列举所有类目
    @GetMapping("/list")
    public Object list(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(currentPage, 10);
        IPage pageData = tradetypeService.page(page, new QueryWrapper<Tradetype>().orderByAsc("id"));
        return Result.succ(pageData);
    }
    //列举所有类目
    @GetMapping("/list/all")
    public Object listAall(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(currentPage, 100);
        IPage pageData = tradetypeService.page(page, new QueryWrapper<Tradetype>().orderByAsc("id"));
        return Result.succ(pageData);
    }
    //添加类目
    @PostMapping("/add")
    public Object add(@RequestBody Tradetype tradetype){
        Tradetype temp = tradetype;

        if(temp.getId() == null){
            temp.setCount(0);
            System.out.println(temp);
            tradetypeService.saveOrUpdate(temp);
            return Result.succ(temp);
        } else {

            Tradetype current = tradetypeService.getById(temp.getId());
            current.setTradetypename(temp.getTradetypename());
            tradetypeService.saveOrUpdate(current);
            return Result.succ(current);
        }

    }

    //删除类目
    @PostMapping("/delete")
    public Object delete(@RequestBody Tradetype tradetype){
        boolean result = tradetypeService.removeById(tradetype.getId());
        if(result == true){
            return Result.succ(null);

        }else {
            return Result.fail(null);
        }

    }
}
