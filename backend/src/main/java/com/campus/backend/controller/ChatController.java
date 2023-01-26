package com.campus.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.Chat;
import com.campus.backend.entity.ChatContentItem;
import com.campus.backend.entity.ChatContentPages;
import com.campus.backend.entity.ChatRequestBody;
import com.campus.backend.mapper.ChatMapper;
import com.campus.backend.mapper.UserMapper;
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
 * @since 2023-01-25
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    private int pageSize=20;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChatMapper chatMapper;

    /**
     * 保存一条聊天记录
     * @param chat 包含：（三个均不能为空）
     *             content：聊天记录的内容
     *             send：发送人uid
     *             recv：接收人uid
     * @return 1即表示保存成功（没啥用）
     */
    @PostMapping("/addContent")
    public Object addContent(@RequestBody Chat chat)
    {
        int count=0;
        try {
            count=chatMapper.insert(chat);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("addContent失败");
        }
        return Result.succ(count);
    }

    /**
     * 获取一个pagesize条数的聊天记录（pagesize可修改）
     * @param requestBody 包含：（三个均不能为空）
     *                    current：页码
     *                    send：发送人uid
     *                    recv：接收人uid
     * @return 包含最近的聊天记录（ChatContentItem）的一个page，其中isMine=true表示当前内容属于发送人
     */
    @PostMapping("/getAllContent")
    public Object getAllContent(@RequestBody ChatRequestBody requestBody)
    {
        if(requestBody.getCurrent()==null) Result.fail("getAllContent失败，参数缺失");
        IPage<Chat> page=new Page<>(requestBody.getCurrent(),pageSize);
        ChatContentPages pages=new ChatContentPages();
        LambdaQueryWrapper<Chat> lqw=new LambdaQueryWrapper<Chat>();
        try {
            lqw.eq(Chat::getSend,requestBody.getSend()).eq(Chat::getRecv,requestBody.getRecv())
                    .or()
                    .eq(Chat::getSend,requestBody.getRecv()).eq(Chat::getRecv,requestBody.getSend())
                    .orderByDesc(Chat::getTime);
            chatMapper.selectPage(page,lqw);
            List<Chat> chats = page.getRecords();
            ChatContentItem contentItem;
            List<ChatContentItem> ChatContent=new ArrayList<>();
            for (Chat chat : chats) {
                contentItem=new ChatContentItem();
                contentItem.setContent(chat.getContent());
                contentItem.setTime(chat.getTime());
                if(chat.getSend()==requestBody.getSend())
                    contentItem.setMine(true);
                else contentItem.setMine(false);
                ChatContent.add(contentItem);
            }
            pages.setInfo(page);
            pages.setRecords(ChatContent);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("失败");
        }
        return Result.succ(pages);
    }



}
