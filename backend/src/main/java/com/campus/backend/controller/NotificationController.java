package com.campus.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.*;
import com.campus.backend.mapper.*;
import com.campus.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author campus
 * @since 2023-02-03
 */
@RestController
@RequestMapping("/notification")
public class NotificationController {
    int pageSize=10;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private FloorMapper floorMapper;

    @Autowired
    private ReplyMapper replyMapper;


    @PostMapping("/hasMessage")
    public Object hasMessage(@RequestBody CID uid)
    {
        try {
            LambdaQueryWrapper<Notification> lqw=new LambdaQueryWrapper<>();
            lqw.eq(Notification::getReceiver,uid.getCid())
                    .eq(Notification::getR,false);
            List<Notification> list = notificationMapper.selectList(lqw);
            if(list.size()!=0) return Result.succ(true);
            else return Result.succ(false);
        }catch (Exception e)
        {
            return Result.fail("hasMessage失败");
        }
    }

    @PostMapping("/getList")
    public Object getList(@RequestBody MyPostInfo info)
    {
        NotificationPages notificationPages=new NotificationPages();
        LambdaQueryWrapper<Notification> lqw=new LambdaQueryWrapper<>();
        IPage<Notification> pages=new Page<>(info.getCurrent(),pageSize);
        try {
            lqw.eq(Notification::getReceiver,info.getUid()).orderByDesc(Notification::getTime);
            notificationMapper.selectPage(pages,lqw);
            NotificationItem item;
            List<NotificationItem> list=new ArrayList<>();
            for (Notification record : pages.getRecords()) {
                item=new NotificationItem(record);
                item.setUsername(getUserName(record.getNotifier()));
                if(record.getType()== TableType.post.getKey())
                {
                    Post post = postMapper.selectById(record.getReference());
                    setPostInfo(item,post);

                    Floor floor = floorMapper.selectById(record.getOuterid());
                    item.setUsername(getUserName(floor.getOwner()));
                    item.setContent(floor.getContent());
                }
                else if(record.getType()==TableType.floor.getKey()){
                    Floor floor = floorMapper.selectById(record.getReference());
                    setFloorInfo(item,floor);
                    Post post = postMapper.selectById(floor.getPid());
                    setPostInfo(item,post);

                    Reply reply = replyMapper.selectById(record.getOuterid());
                    item.setUsername(getUserName(reply.getOwner()));
                    item.setContent(reply.getContent());
                }
                else if(record.getType()==TableType.reply.getKey()){
                    System.out.println(record);
                    Reply reply = replyMapper.selectById(record.getReference());
                    item.setRid(reply.getId());
                    item.setReplyUserName(getUserName(reply.getOwner()));
                    item.setReplyContent(reply.getContent());
                    if(reply.getOthers()!=null) item.setOthersName(getUserName(reply.getOthers()));

                    Floor floor = floorMapper.selectById(reply.getFid());
                    setFloorInfo(item,floor);
                    Post post = postMapper.selectById(floor.getPid());
                    setPostInfo(item,post);

                    Reply reply1 = replyMapper.selectById(record.getOuterid());
                    item.setUsername(getUserName(reply1.getOwner()));
                    item.setContent(reply1.getContent());
                }
                else Result.fail("getList失败,type参数错误");
                list.add(item);

            }
            notificationPages.setInfo(pages);
            notificationPages.setRecords(list);
            setAllRead(info.getUid());
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("getList失败");
        }
        return Result.succ(notificationPages);
    }


    public Object setAllRead(Integer uid)
    {
        LambdaQueryWrapper<Notification> lqw=new LambdaQueryWrapper<>();
        try {
            lqw.eq(Notification::getReceiver,uid);
            Notification notification=new Notification();
            notification.setR(true);
            notificationMapper.update(notification,lqw);
        }catch (Exception e)
        {
            return Result.fail("setAllRead失败");
        }
        return Result.succ();
    }


    private String getUserName(int id)
    {
        User user = userMapper.selectById(id);
        return user.getUsername();
    }

    private void setPostInfo(NotificationItem item,Post post)
    {
        item.setPid(post.getId());
        item.setPostUsername(getUserName(post.getOwner()));
        item.setTitle(post.getTitle());
    }

    private void setFloorInfo(NotificationItem item,Floor floor)
    {
        item.setFid(floor.getId());
        item.setFloorUsername(getUserName(floor.getOwner()));
        item.setFloorContent(floor.getContent());
    }

}
