package com.campus.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.Type;
import com.campus.backend.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author campus
 * @since 2023-01-13
 */
@RestController
@RequestMapping("/type")
public class TypeController {
    @Autowired
    TypeService typeService;

    //分页列举所有类目
    @GetMapping("/list")
    public Object list(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(currentPage, 10);
        IPage pageData = typeService.page(page, new QueryWrapper<Type>().orderByAsc("id"));
        return Result.succ(pageData);
    }
    //列举所有类目
    @GetMapping("/list/all")
    public Object listAall(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(currentPage, 100);
        IPage pageData = typeService.page(page, new QueryWrapper<Type>().orderByAsc("id"));
        return Result.succ(pageData);
    }
    //添加类目
    @PostMapping("/add")
    public Object add(@RequestBody Type type){
        Type temp = type;

        if(temp.getId() == null){
            temp.setCount(0);
            System.out.println(temp);
            typeService.saveOrUpdate(temp);
            return Result.succ(temp);
        }
        return Result.fail("type add fail");
    }

    //删除类目
    @PostMapping("/delete")
    public Object delete(@RequestBody Type type){
        boolean result = typeService.removeById(type.getId());
        if(result == true){
            return Result.succ(null);

        }else {
            return Result.fail(null);
        }

    }


}
