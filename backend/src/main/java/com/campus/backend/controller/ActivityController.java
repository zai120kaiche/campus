package com.campus.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.*;
import com.campus.backend.service.ActivityCollectService;
import com.campus.backend.service.ActivityService;
import com.campus.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author campus
 * @since 2023-02-06
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @Autowired
    UserService userService;

    @Autowired
    ActivityCollectService activityCollectService;

    @PostMapping("add")
    public Result add(@RequestBody Activity activity){
        try{
            User u = userService.getById(activity.getOwner());
            if(u.getStandard() == 9){

                activityService.saveOrUpdate(activity);
                return Result.succ();
            }else{
                return Result.fail("权限不足");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody ActivityInfo activityInfo){
        try{
            User u = userService.getById(activityInfo.getUid());
            if(u.getStandard() == 9){
                activityService.removeById(activityInfo.getAid());
                return Result.succ();
            }else{
                return Result.fail("权限不足");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/list/all")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage, 7);
        IPage pageData = activityService.page(page, new QueryWrapper<Activity>().orderByAsc("id"));
        return Result.succ(pageData);
    }

    @PostMapping("/search")
    public Result search(@RequestParam(defaultValue = "1") Integer currentPage, @RequestBody ActivitySearch activitySearch){
        try {
            switch (activitySearch.getOrder()){
                case 0:{
                    Page page = new Page(currentPage, 7);
                    IPage pageData = activityService.page(page, new QueryWrapper<Activity>().orderByAsc("id"));
                    return Result.succ(pageData);
                }
                case 1:{
                    Page page = new Page(currentPage, 7);
                    IPage pageData = activityService.page(page, new QueryWrapper<Activity>().eq("flag", activitySearch.getType()).orderByAsc("id"));
                    return Result.succ(pageData);
                }

                case 2:{
                    Page page = new Page(currentPage, 7);
                    IPage pageData = activityService.page(page, new QueryWrapper<Activity>().like("title", activitySearch.getKeyWord()).
                            or().like("content",activitySearch.getKeyWord()).
                            or().like("des",activitySearch.getKeyWord()).
                            orderByAsc("id"));
                    return Result.succ(pageData);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @PostMapping("/detail")
    public Result detail(@RequestBody CID aid){
        try {
            Activity a = activityService.getById(aid.getCid());

            return Result.succ(a);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
