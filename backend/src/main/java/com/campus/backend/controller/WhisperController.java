package com.campus.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.*;
import com.campus.backend.entity.WhisperFloor;
import com.campus.backend.entity.WhisperPost;
import com.campus.backend.entity.WhisperPostCommentItem;
import com.campus.backend.entity.WhisperReply;
import com.campus.backend.mapper.UserMapper;
import com.campus.backend.mapper.WhisperFloorMapper;
import com.campus.backend.mapper.WhisperPostMapper;
import com.campus.backend.mapper.WhisperReplyMapper;
import com.campus.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author campus
 * @since 2023-02-07
 */
@RestController
@RequestMapping("/whisper")
public class WhisperController {
    private int pageSize=5;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WhisperPostMapper whisperPostMapper;

    @Autowired
    private WhisperFloorMapper whisperFloorMapper;

    @Autowired
    private WhisperReplyMapper whisperReplyMapper;

    @Autowired
    private UserService userService;



    //发帖
    @PostMapping("/doPost")
    public Object doPost(@RequestBody WhisperPost post)
    {
        post.setOwner(getMask(post.getUid()));
        post.setViewNum(0);
        post.setCommentNum(0);
        try {
            whisperPostMapper.insert(post);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("doPost失败");
        }
        return Result.succ();
    }

    //一级评论
    @PostMapping("/doComment")
    public Object doComment(@RequestBody WhisperFloor floor)
    {
        try {

            floor.setOwner(getMask(floor.getUid()));
            floor.setReplyNum(0);
            whisperFloorMapper.insert(floor);

            WhisperPost post = whisperPostMapper.selectById(floor.getPid());
            post.setCommentNum(post.getCommentNum()+1);
            whisperPostMapper.updateById(post);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("doComment失败");
        }
        return Result.succ();
    }

    //二级评论
    @PostMapping("/doReply")
    public Object doReply(@RequestBody WhisperReply reply)
    {
        try {
            reply.setOwner(getMask(reply.getUid()));
            whisperReplyMapper.insert(reply);

            WhisperFloor floor = whisperFloorMapper.selectById(reply.getFid());
            floor.setReplyNum(floor.getReplyNum()+1);
            whisperFloorMapper.updateById(floor);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("doReply失败");
        }
        return Result.succ();
    }

    //获取帖子
    @PostMapping("/getPost")
    public Object getPost(@RequestBody SearchCondition sc)
    {
        IPage<WhisperPost> page=new Page(sc.getCurrent(),7);
        LambdaQueryWrapper<WhisperPost> lqw=new LambdaQueryWrapper<>();
        try {
            if(sc.getKeyWord()!=null)
            {
                List<String> keyWord=sc.getKeyWord();
                for (int i = 0; i <keyWord.size(); i++) {
                    lqw.like(WhisperPost::getTitle,keyWord.get(i)).or().like(WhisperPost::getContent,keyWord.get(i));
                    if(i!=keyWord.size()-1) lqw.or();
                }
            }
            lqw.orderByDesc(WhisperPost::getDate);
            whisperPostMapper.selectPage(page,lqw);
            List<WhisperPost> lis = page.getRecords();
            for (int i = 0; i < lis.size(); i++) {
                lis.get(i).setUid(-1);
            }
            page.setRecords(lis);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("getPost失败");
        }
        return Result.succ(page);
    }

    @PostMapping("/getPostInfo")
    public Object getPostInfo(@RequestBody PostInfoRequestBody requestBody)
    {
        List<WhisperPostCommentItem> commentItems =new ArrayList<>();
        try {
            WhisperPost post = whisperPostMapper.selectById(requestBody.getPid());
            post.setViewNum(post.getViewNum()+1);
            whisperPostMapper.updateById(post);
            IPage<WhisperFloor> floorPage=new Page<>(requestBody.getCurrent(),pageSize);
            LambdaQueryWrapper<WhisperFloor> lqw=new LambdaQueryWrapper<>();
            lqw.eq(WhisperFloor::getPid,requestBody.getPid()).orderByDesc(WhisperFloor::getDate);
            whisperFloorMapper.selectPage(floorPage,lqw);

            WhisperPostCommentItem commentItem;
            for (WhisperFloor record : floorPage.getRecords()) {
                IPage<WhisperReply> page=new Page<>(1,500);
                LambdaQueryWrapper<WhisperReply> lqw1=new LambdaQueryWrapper<>();
                lqw1.eq(WhisperReply::getFid,record.getId()).orderByDesc(WhisperReply::getDate);
                whisperReplyMapper.selectPage(page,lqw1);

                commentItem=new WhisperPostCommentItem(record,page.getRecords());
                commentItems.add(commentItem);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("getPostInfo失败");
        }
        return Result.succ(commentItems);
    }

    @PostMapping("getDetail")
    public Result detail(@RequestBody CID p){
        try {
            return Result.succ(whisperPostMapper.selectById(p.getCid()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("changeMask")
    public Result changeMask(@RequestBody User user){
        try{
            User u = userMapper.selectById(user.getId());
            u.setMask(user.getMask());
            userService.saveOrUpdate(u);
            return Result.succ();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getMask(Integer id)
    {
        User user = userMapper.selectById(id);
        return user.getMask();
    }
}
