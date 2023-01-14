package com.campus.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.School;
import com.campus.backend.entity.Type;
import com.campus.backend.service.SchoolService;
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
@RequestMapping("/school")
public class SchoolController {
    @Autowired
    SchoolService schoolService;

    //分页列举所有类目
    @GetMapping("/list")
    public Object list(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(currentPage, 10);
        IPage pageData = schoolService.page(page, new QueryWrapper<School>().orderByAsc("id"));
        return Result.succ(pageData);
    }
    //列举所有类目
    @GetMapping("/list/all")
    public Object listAall(@RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(currentPage, 100);
        IPage pageData = schoolService.page(page, new QueryWrapper<School>().orderByAsc("id"));
        return Result.succ(pageData);
    }
    //添加类目
    @PostMapping("/add")
    public Object add(@RequestBody School school){
        School temp = school;

        if(temp.getId() == null){
            temp.setCount(0);
            System.out.println(temp);
            schoolService.saveOrUpdate(temp);
            return Result.succ(temp);
        }
        return Result.fail("type add fail");
    }

    //删除类目
    @PostMapping("/delete")
    public Object delete(@RequestBody School school){
        boolean result = schoolService.removeById(school.getId());
        if(result == true){
            return Result.succ(null);

        }else {
            return Result.fail(null);
        }

    }

}
