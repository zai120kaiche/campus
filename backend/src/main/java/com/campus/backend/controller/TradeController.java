package com.campus.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.*;
import com.campus.backend.mapper.*;
import com.campus.backend.service.UserService;
import com.campus.backend.tool.GetIpAddressUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author campus
 * @since 2023-01-20
 */
@RestController
@RequestMapping("/trade")
public class TradeController {
    private int pageSize=9;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private TradetypeMapper tradetypeMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private IntentionMapper intentionMapper;

    @Autowired
    UserService userService;

    /**
     * 发布商品
     * @param commodity
     * 必须参数：content,owner,tradeFlag,kind,university，对于tradeFlag true表示卖，false表示买
     * 可选参数：pic
     *
     */
//    发布商品
    @PostMapping("/addCommodity")
    public Object addCommodity(@RequestBody Commodity commodity,HttpServletRequest request){
        try {
            String ip = GetIpAddressUtil.getIpInfo(request);
            commodity.setLocation(ip);

            String kinds = commodity.getKind();
            String[] kindSplit = kinds.split(",");
            String kindNames="";
            for (int i = 0; i < kindSplit.length; i++) {
                int kindId = Integer.parseInt(kindSplit[i]);
                Tradetype tradetype = tradetypeMapper.selectById(kindId);
                kindNames+=tradetype.getTradetypename();
                if(i!=kindSplit.length-1)  kindNames+=",";
            }
            commodity.setKindName(kindNames);

            commodityMapper.insert(commodity);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("addCommodity失败");
        }
        return Result.succ();
    }
//获取商品信息
    @PostMapping("/getCommodity")
    public Object getCommodity(@RequestBody SearchConditionOfCommodity sc){

        IPage page=new Page(sc.getCurrent(),pageSize);
        CommodityPages commodityPages;
        LambdaQueryWrapper<Commodity> lqw=new LambdaQueryWrapper<>();

        try {
            lqw.eq(sc.getSchool()!=null,Commodity::getUniversity,sc.getSchool())
                    .apply(sc.getType()!=null,"FIND_IN_SET ( '"+ sc.getType() +"',kind )");
            if(sc.getKeyWord()!=null)
            {
                List<String> keyWord=sc.getKeyWord();
                for (int i = 0; i <keyWord.size(); i++) {
                    lqw.like(Commodity::getContent,keyWord.get(i));
                    if(i!=keyWord.size()-1) lqw.or();
                }
            }
            lqw.eq(sc.getTradeFlag()!=null,Commodity::getTradeFlag,sc.getTradeFlag())
                    .eq(Commodity::getFinishFlag,false);
            lqw.orderByDesc(Commodity::getDate);
            commodityMapper.selectPage(page,lqw);
            commodityPages=addUserInfoToCommodity(page, sc.getUid());
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("getCommodity失败");
        }
        return Result.succ(commodityPages);
    }
//收藏
    @PostMapping("/doCollect")
    public Object doCollect(@RequestBody Intention intention){
        try {
            intentionMapper.insert(intention);
            Commodity commodity = commodityMapper.selectById(intention.getCid());
            commodity.setCollectNum(commodity.getCollectNum()+1);
            commodityMapper.updateById(commodity);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("doCollect失败");
        }
        return Result.succ();
    }
//商品详情


    @PostMapping("/view")
    public Object addViewNum(@RequestBody CID cidN) {
        Integer cid = cidN.getCid();

        System.out.println(cid);
        Commodity commodity;
        try {
            commodity = commodityMapper.selectById(cid);
            commodity.setViewNum(commodity.getViewNum() + 1);
            commodityMapper.updateById(commodity);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("addViewNum失败");
        }
        return Result.succ(commodity);
    }

    @PostMapping("/getCommodityById")
    public Object getCommodityById(@RequestBody MyPostInfo info){
        System.out.println(info);
        IPage page=new Page(info.getCurrent(),10);
        CommodityPages commodityPages;
        LambdaQueryWrapper<Commodity> lqw=new LambdaQueryWrapper<>();
        try {
            lqw.eq(Commodity::getOwner,info.getUid()).orderByDesc(Commodity::getDate);
            commodityMapper.selectPage(page,lqw);
            commodityPages=addUserInfoToCommodity(page,info.getUid());
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("getCommodityById失败");
        }
        return Result.succ(commodityPages);
    }

    @PostMapping("/getCollect")
    public Object getCollectById(@RequestBody MyPostInfo info){
        IPage page=new Page(info.getCurrent(),8);
        CommodityPages commodityPages;
        List<Integer> cids=new ArrayList<>();
        LambdaQueryWrapper<Intention> lqw=new LambdaQueryWrapper<>();
        try {
            lqw.eq(Intention::getUid,info.getUid());
            List<Intention> intentions = intentionMapper.selectList(lqw);
            for (Intention intention : intentions) {
                cids.add(intention.getCid());
            }
            LambdaQueryWrapper<Commodity> lqw1=new LambdaQueryWrapper<>();
            lqw1.in(Commodity::getId,cids).orderByDesc(Commodity::getDate);
            commodityMapper.selectPage(page,lqw1);
            commodityPages=addUserInfoToCommodity(page,info.getUid());

        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("getCollectById失败");
        }
        return Result.succ(commodityPages);
    }

    @PostMapping("/deleteCommodity")
    public Object deleteCommodityById(@RequestBody DeleteInfo info){
        System.out.println(info);
        int count=0;
        try {
            User u = userService.getById(info.getUid());
            System.out.println(u.getStandard());
            if(u.getStandard() == 9){
                LambdaQueryWrapper<Commodity> lqw=new LambdaQueryWrapper<>();
                lqw.in(Commodity::getId,info.getObjectIds());
                count = commodityMapper.delete(lqw);
            }else {

                LambdaQueryWrapper<Commodity> lqw = new LambdaQueryWrapper<>();
                lqw.in(Commodity::getId, info.getObjectIds()).eq(Commodity::getOwner, info.getUid());
                count = commodityMapper.delete(lqw);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("deleteCommodity失败");
        }
        return Result.succ(count);
    }

    @PostMapping("/deleteCollect")
    public Object deleteCollectById(@RequestBody DeleteInfo info){
        int count=0;
        List<Integer> ids = info.getObjectIds();
        try {
            LambdaQueryWrapper<Intention> lqw=new LambdaQueryWrapper<>();
            lqw.eq(Intention::getUid,info.getUid()).in(Intention::getCid,ids);
            count=intentionMapper.delete(lqw);
            for (Integer id : ids) {
                Commodity commodity = commodityMapper.selectById(id);
                commodity.setCollectNum(commodity.getCollectNum()-1);
                commodityMapper.updateById(commodity);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("deleteCollect失败");
        }
        return Result.succ(count);
    }

    @PostMapping("/finishTrade")
    public Object finishTrade(@RequestBody FinishInfo finishInfo){
        if(finishInfo.getCid()==null || finishInfo.getUid()==null)
            return Result.fail("finishTrade参数缺失");
        try {
            LambdaQueryWrapper<Commodity> lqw=new LambdaQueryWrapper<>();
            lqw.eq(Commodity::getId,finishInfo.getCid()).eq(Commodity::getOwner,finishInfo.getUid());
            Commodity commodity = commodityMapper.selectOne(lqw);
            if(commodity==null) return Result.fail("finishTrade参数缺失");
            commodity.setFinishFlag(true);
            commodityMapper.updateById(commodity);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("finishTrade失败");
        }
        return Result.succ();
    }

    @PostMapping("/getIntentionInfo")
    public Object getIntentionInfo(Integer cid){
        IntentionInfo info=new IntentionInfo();
        try {
            LambdaQueryWrapper<Intention> lqw=new LambdaQueryWrapper<>();
            lqw.eq(Intention::getCid,cid);
            List<Intention> intentions = intentionMapper.selectList(lqw);
            List<User> list=new ArrayList<>();
            for (Intention intention : intentions) {
                User user = userMapper.selectById(intention.getUid());
                list.add(user);
            }
            info.setCount(list.size());
            info.setUsers(list);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("d");
        }
        return Result.succ(info);
    }


    private CommodityPages addUserInfoToCommodity(IPage<Commodity> page,Integer uid)
    {
        CommodityPages commodityPages=new CommodityPages();
        List<CommodityItem> commodityItems=new ArrayList<>();
        CommodityItem commodityItem;
        for (Commodity record : page.getRecords()) {
            commodityItem=new CommodityItem(record);
            Integer universityId = record.getUniversity();
            School school = schoolMapper.selectById(universityId);
            commodityItem.setUniversityName(school.getSchoolname());
            commodityItem.setUserName(getUserName(record.getOwner()));
            commodityItem.setCollectFlag(isCollect(uid,record.getId()));
            commodityItems.add(commodityItem);
        }
        commodityPages.setInfo(page);
        commodityPages.setRecords(commodityItems);
        return commodityPages;
    }

    private String getUserName(int id)
    {
        User user = userMapper.selectById(id);
        return user.getUsername();
    }

    private boolean isCollect(Integer uid,Integer pid)
    {
        LambdaQueryWrapper<Intention> lqw=new LambdaQueryWrapper<>();
        lqw.eq(Intention::getUid,uid).eq(Intention::getCid,pid);
        List<Intention> collects = intentionMapper.selectList(lqw);
        if(collects.size()!=0) return true;
        else return false;
    }


}
