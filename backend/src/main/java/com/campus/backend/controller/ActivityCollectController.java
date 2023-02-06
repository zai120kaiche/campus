package com.campus.backend.controller;


import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.ActivityCollect;
import com.campus.backend.service.ActivityCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author campus
 * @since 2023-02-06
 */
@RestController
@RequestMapping("/activity-collect")
public class ActivityCollectController {

    @Autowired
    ActivityCollectService activityCollectService;

    @PostMapping("do")
    public Result D(@RequestBody ActivityCollect activityCollect){
        try{
            System.out.println(activityCollect.getAid());
            System.out.println(activityCollect.getUid());
            activityCollectService.saveOrUpdate(activityCollect);
            return Result.succ();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
