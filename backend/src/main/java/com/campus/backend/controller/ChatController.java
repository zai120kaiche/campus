package com.campus.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.*;
import com.campus.backend.mapper.ChatMapper;
import com.campus.backend.mapper.ContactsMapper;
import com.campus.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
    private int pageSize=5000;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private ContactsMapper contactsMapper;

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
        if(chat.getSend() != chat.getRecv()){
            try {
                count=chatMapper.insert(chat);
                Contacts contacts=new Contacts();
                contacts.setOwner(chat.getSend());
                contacts.setOthers(chat.getRecv());
                refreshContactList(contacts);
                contacts.setOwner(chat.getRecv());
                contacts.setOthers(chat.getSend());
                refreshContactList(contacts);
            }catch (Exception e)
            {
                e.printStackTrace();
                return Result.fail("addContent失败");
            }
            return Result.succ(count);
        }else{
            return  Result.fail("addContent失败");
        }

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
                contentItem.setWithdraw(chat.isWithdraw());
                contentItem.setId(chat.getId());
                if(chat.getSend()==requestBody.getSend())
                    contentItem.setMine(true);
                else contentItem.setMine(false);
                ChatContent.add(contentItem);
            }
            pages.setInfo(page);
            pages.setRecords(ChatContent);

            LambdaQueryWrapper<Chat> lqw1=new LambdaQueryWrapper<>();
            lqw1.eq(Chat::getSend,requestBody.getRecv())
                    .eq(Chat::getRecv,requestBody.getSend());
            Chat chat=new Chat();
            chat.setRead(true);
            chatMapper.update(chat,lqw1);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("失败");
        }
        return Result.succ(pages);
    }

    @PostMapping("/doWithdraw")
    public Object doWithdraw(@RequestBody WithdrawRequest request)
    {
        int i=0;
        try {
            LambdaQueryWrapper<Chat> lqw=new LambdaQueryWrapper<>();
            lqw.eq(Chat::getId,request.getId())
                    .eq(Chat::getSend,request.getUid());
            Chat chat = chatMapper.selectOne(lqw);
            if(chat==null)
                return Result.fail("doWithdraw失败,参数有误");
            else {
                chat.setWithdraw(true);
                i = chatMapper.updateById(chat);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("doWithdraw失败");
        }
        return Result.succ(i);
    }


    /**
     * 在发起私聊时使用
     * @param contacts 包含（均不为空）：
     *                 owner：当前用户id,
     *                 others：对方id
     * @return
     */
    @PostMapping("/establishContact")
    public Object establishContact(@RequestBody Contacts contacts)
    {
        int count=0;
        try {
            count=contactsMapper.insert(contacts);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("establishContact失败");
        }
        return Result.succ(count);
    }

    /**
     * 单方面删除某一聊天项
     * @param contacts 参数同上
     * @return
     */
    @PostMapping("/deleteContact")
    public Object deleteContact(@RequestBody Contacts contacts)
    {
        int count=0;
        try {
            LambdaQueryWrapper<Contacts> lqw=new LambdaQueryWrapper<>();
            lqw.eq(Contacts::getOwner,contacts.getOwner())
                    .eq(Contacts::getOthers,contacts.getOthers());
            count=contactsMapper.delete(lqw);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("deleteContact失败");
        }
        return Result.succ(count);
    }


    /**
     * 获取当前用户的聊天记列表
     * 两个参数：uid 当前和用户id
     *        current 当前页码
     * @return
     */
    @PostMapping("/getContactList")
    public Object getContactList(@RequestBody ContactRequest request)
    {
        ContactPage contactPage=new ContactPage();
        try {
            User user = userMapper.selectById(request.getUid());
            if(user==null) return Result.fail("getContactList失败");

            IPage<Contacts> pages=new Page<>(request.getCurrent(),pageSize);
            LambdaQueryWrapper<Contacts> lqw=new LambdaQueryWrapper();
            lqw.eq(Contacts::getOwner,request.getUid());
            contactsMapper.selectPage(pages,lqw);
            ContactItem contactItem;
            List<ContactItem> list=new ArrayList<>();
            for (Contacts record : pages.getRecords()) {
                contactItem=new ContactItem();
                User user1 = userMapper.selectById(record.getOthers());
                LambdaQueryWrapper<Chat> lqw1=new LambdaQueryWrapper<>();
                lqw1.eq(Chat::getSend,record.getOthers())
                        .eq(Chat::getRecv,request.getUid())
                        .eq(Chat::isRead,false);
                Chat chat = chatMapper.selectOne(lqw1);
                if(chat==null)
                    contactItem.setHasMessage(false);
                else contactItem.setHasMessage(true);
                contactItem.setOthers(user1.getId());
                contactItem.setUsername(user1.getUsername());
                contactItem.setAvatar(user1.getAvatar());
                list.add(contactItem);
            }
            contactPage.setInfo(pages);
            contactPage.setRecords(list);

        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("getContactList失败");
        }
        return Result.succ(contactPage);
    }

    private int refreshContactList(Contacts contacts)
    {
        int count=0;
        try {
            LambdaQueryWrapper<Contacts> lqw=new LambdaQueryWrapper<>();
            lqw.eq(Contacts::getOwner,contacts.getOwner())
                    .eq(Contacts::getOthers,contacts.getOthers());
            Contacts contacts1 = contactsMapper.selectOne(lqw);
            if(contacts1==null)
                count=contactsMapper.insert(contacts);
            else
            {
                contacts1.setTime(LocalDateTime.now());
                count=contactsMapper.updateById(contacts1);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
        return count;
    }

}
