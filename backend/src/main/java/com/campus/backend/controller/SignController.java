package com.campus.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.*;
import com.campus.backend.mapper.ActivityCollectMapper;
import com.campus.backend.mapper.ActivitySignMapper;
import com.campus.backend.mapper.SignMapper;
import com.campus.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author campus
 * @since 2023-02-28
 */
@RestController
@RequestMapping("/sign")
public class SignController {
    @Autowired
    private SignMapper signMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ActivitySignMapper activitySignMapper;

//首页签到
    @PostMapping("/signById")
    public Object signById(@RequestBody CID cid)
    {
        Integer uid=cid.getCid();
        if(uid==null) return Result.fail("null");

        try {
            LocalDate localDate=LocalDate.now();
            Sign sign=new Sign(uid,localDate);
            int i = signMapper.insert(sign);
            if(i==0) return Result.fail("签到失败");
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("签到失败");
        }
        return Result.succ();
    }

    //活动打卡
    @PostMapping("/activitySignById")
    public Object activitySignById(@RequestBody ActivitySign body)
    {
        try {
            LocalDate localDate=LocalDate.now();
            body.setDate(localDate);
            int i = activitySignMapper.insert(body);

            if(i==0) return Result.fail("签到失败");
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("签到失败");
        }
        return Result.succ();
    }

    //获取首页签到情况
    @PostMapping("/getInfoById")
    public Object getInfoById(@RequestBody CID cid)
    {
        Integer uid=cid.getCid();
        if(uid==null) return Result.fail("null");

        LambdaQueryWrapper<Sign> lqw=new LambdaQueryWrapper<>();
        IPage<Sign> page=new Page(1,50);
        SignInfo signInfo=new SignInfo();
        try {
            LocalDate date=LocalDate.now();
            lqw.eq(Sign::getUid,uid).orderByDesc(Sign::getData);
            signMapper.selectPage(page,lqw);
            signInfo.setSigns(page.getRecords());
            if(page.getRecords().get(0).getData().isEqual(date)) signInfo.setIsSign(true);
            else signInfo.setIsSign(false);

            LambdaQueryWrapper<Sign> lqw1=new LambdaQueryWrapper<>();
            lqw1.eq(Sign::getUid,uid).orderByDesc(Sign::getData);
            List<Sign> signs = signMapper.selectList(lqw1);
            signInfo.setDays(getSignDays(signs));

        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("获取失败");
        }
        return Result.succ(signInfo);
    }

    //活动获取打卡情况
    @PostMapping("/activityGetInfoById")
    public Object activityGetInfoById(@RequestBody ActivitySign body)
    {
        LambdaQueryWrapper<ActivitySign> lqw=new LambdaQueryWrapper<>();
        lqw.eq(ActivitySign::getAid,body.getAid()).eq(ActivitySign::getUid,body.getUid()).orderByDesc(ActivitySign::getDate);
        ActivitySignInfo info=new ActivitySignInfo();
        try {
            LocalDate date=LocalDate.now();
            List<ActivitySign> activitySigns = activitySignMapper.selectList(lqw);
            info.setSigns(activitySigns);
            if(activitySigns.get(0).getDate().isEqual(date)) info.setIsSign(true);
            else info.setIsSign(false);

        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("获取失败");
        }
        return Result.succ(info);
    }


    //管理员获取在指定时间段内的符合打卡天数的人
    @PostMapping("/getInfoByDate")
    public Object getInfoByDate(@RequestBody DateCondition request)
    {
        QueryWrapper<Sign> qw=new QueryWrapper<>();
        qw.lambda().ge(Sign::getData,request.getBegin()).le(Sign::getData,request.getEnd()).in(Sign::getUid,request.getUids());
        List<User> users=new ArrayList<>();
        try {
            qw.select("count(*) as count","uid");
            qw.groupBy("uid");
            List<Map<String, Object>> maps = signMapper.selectMaps(qw);
            for (Map<String, Object> map : maps) {
                if((int)map.get("count") >= request.getLimit())
                {
                    User user = userMapper.selectById((int) map.get("uid"));
                    users.add(user);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("获取失败");
        }
        return Result.succ(users);
    }

    //管理员获取活动打卡记录
    @PostMapping("/getInfoByAdmin")
    public Object getInfoByAdmin(@RequestBody CID cid)
    {
        Integer aid=cid.getCid();
        List<ActivitySign> activitySigns=null;
        try {
            LambdaQueryWrapper<ActivitySign> lqw=new LambdaQueryWrapper<>();
            lqw.eq(ActivitySign::getAid,aid).orderByDesc(ActivitySign::getUid).orderByDesc(ActivitySign::getDate);
            activitySigns = activitySignMapper.selectList(lqw);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("获取失败");
        }
        return Result.succ(activitySigns);
    }



    //获取连续签到天数
    private static int getSignDays(List<Sign> signs)
    {
        int signDays=0;
        LocalDate localDate=LocalDate.now();
        LocalDate newData = signs.get(0).getData();
        newData=newData.plusDays(1);
        if(newData.isEqual(localDate)) localDate=localDate.minusDays(1);

        for (Sign sign : signs) {
            LocalDate date=sign.getData();
            if(localDate.isEqual(date)) signDays++;
            else break;
            localDate=localDate.minusDays(1);
        }
        return signDays;
    }

}
