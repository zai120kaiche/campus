package com.campus.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.*;
import com.campus.backend.mapper.*;
import com.campus.backend.service.PostService;
import com.campus.backend.service.UserService;
import com.campus.backend.tool.GetIpAddressUtil;
import javafx.geometry.Pos;
import lombok.Data;
import org.bouncycastle.crypto.engines.AESLightEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.parser.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.campus.backend.entity.OrderType.Date;

@RestController
@RequestMapping("/community")
public class CommunityController {
    private int pageSize=5;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    UserService userService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private FloorMapper floorMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private LikePostMapper likePostMapper;

    @Autowired
    private LikeFloorMapper likeFloorMapper;

    @Autowired
    private LikeReplyMapper likeReplyMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private TypeMapper typeMapper;

//发帖
    @PostMapping("/doPost")
    public Object doPost(@RequestBody Post post, HttpServletRequest request){
        System.out.println(post);
        try {
            String ip = GetIpAddressUtil.getIpInfo(request);
            post.setLocation(ip);

            String kinds = post.getKind();
            String[] kindSplit = kinds.split(",");
            String kindNames="";
            for (int i = 0; i < kindSplit.length; i++) {
                int kindId = Integer.parseInt(kindSplit[i]);
                Type type = typeMapper.selectById(kindId);
                kindNames+=type.getTypename();
                if(i!=kindSplit.length-1)  kindNames+=",";
            }
            post.setKindName(kindNames);

            postMapper.insert(post);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("doPost失败");
        }
        return Result.succ();
    }

    //单篇详情
    @PostMapping("/postDetail")
    public Result postDetail(@RequestBody Post post)
    {
        if(post.getId() != null){
            return Result.succ(postMapper.selectById(post.getId()));
        } else {
            return Result.fail("获取失败");
        }
    }
    //获取帖子
    @PostMapping("/getPost")
    public Object getPost(@RequestBody SearchCondition sc)
    {
        if(sc.getOrder()==null || sc.getCurrent()==null) return Result.fail("未指定排序类型或页码");

        IPage page=new Page(sc.getCurrent(),pageSize);
        PostPages postPages;
        LambdaQueryWrapper<Post> lqw=new LambdaQueryWrapper<>();

        try {
            lqw.eq(sc.getSchool()!=null,Post::getUniversity,sc.getSchool())
                    .apply(sc.getType()!=null,"FIND_IN_SET ( '"+ sc.getType() +"',kind )");
            if(sc.getKeyWord()!=null)
            {
                List<String> keyWord=sc.getKeyWord();
                for (int i = 0; i <keyWord.size(); i++) {
                    lqw.like(Post::getTitle,keyWord.get(i)).or().like(Post::getContent,keyWord.get(i));
                    if(i!=keyWord.size()-1) lqw.or();
                }
            }
            OrderType value = OrderType.values()[sc.getOrder()];
            switch (value)
            {
                case Date:
                    lqw.orderByDesc(Post::getDate);
                    break;
                case Like:
                    lqw.orderByDesc(Post::getLikeNum);
                    break;
                case View:
                    lqw.orderByDesc(Post::getViewNum);
                    break;
                case Comment:
                    lqw.orderByDesc(Post::getCommentNum);
                    break;
                case Collect:
                    lqw.orderByDesc(Post::getCollectNum);
                    break;
                default:
                    break;
            }
            postMapper.selectPage(page,lqw);
            postPages=addUserInfoToPost(page,sc.getUid());
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("SearchCondition出现异常");
        }

        return Result.succ(postPages);
    }
    //获取帖子列表
    @PostMapping("/getPostList")
    public Object getPostList(@RequestBody SearchCondition sc)
    {
        if(sc.getOrder()==null || sc.getCurrent()==null) return Result.fail("未指定排序类型或页码");

        IPage page=new Page(sc.getCurrent(),10);
        PostPages postPages;
        LambdaQueryWrapper<Post> lqw=new LambdaQueryWrapper<>();

        try {
            lqw.eq(sc.getSchool()!=null,Post::getUniversity,sc.getSchool())
                    .apply(sc.getType()!=null,"FIND_IN_SET ( '"+ sc.getType() +"',kind )");
            if(sc.getKeyWord()!=null)
            {
                List<String> keyWord=sc.getKeyWord();
                for (int i = 0; i <keyWord.size(); i++) {
                    lqw.like(Post::getTitle,keyWord.get(i)).or().like(Post::getContent,keyWord.get(i));
                    if(i!=keyWord.size()-1) lqw.or();
                }
            }
            OrderType value = OrderType.values()[sc.getOrder()];
            switch (value)
            {
                case Date:
                    lqw.orderByDesc(Post::getDate);
                    break;
                case Like:
                    lqw.orderByDesc(Post::getLikeNum);
                    break;
                case View:
                    lqw.orderByDesc(Post::getViewNum);
                    break;
                case Comment:
                    lqw.orderByDesc(Post::getCommentNum);
                    break;
                case Collect:
                    lqw.orderByDesc(Post::getCollectNum);
                    break;
                default:
                    break;
            }
            postMapper.selectPage(page,lqw);
            postPages=addUserInfoToPost(page,sc.getUid());
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("SearchCondition出现异常");
        }

        return Result.succ(postPages);
    }
//点赞
    @PostMapping("/doLike")
    public Object doLike(@RequestBody LikeType likeType)
    {
        if(likeType.getType()==null || likeType.getObjectId()==null ||likeType.getUid()==null )
            return Result.fail("doLike参数缺失");
        TableType tableType = TableType.values()[likeType.getType()];
        try {
            int objectId=likeType.getObjectId();
            int uid=likeType.getUid();
            if(tableType==TableType.post)
            {
                Post post = postMapper.selectById(objectId);
                //点赞
                if(likeType.isFlag())
                {
                    post.setLikeNum(post.getLikeNum()+1);
                    LikePost likePost=new LikePost(uid,objectId);
                    likePostMapper.insert(likePost);
                    postMapper.updateById(post);
                }
                else //取消赞
                {
                    post.setLikeNum(post.getLikeNum()-1);
                    LambdaQueryWrapper<LikePost> lqw=new LambdaQueryWrapper<>();
                    lqw.eq(LikePost::getUid,uid).eq(LikePost::getPid,objectId);
                    likePostMapper.delete(lqw);
                    postMapper.updateById(post);
                }
            }
            else if(tableType==TableType.floor)
            {
                Floor floor = floorMapper.selectById(objectId);
                if(likeType.isFlag())
                {
                    floor.setLikeNum(floor.getLikeNum()+1);
                    LikeFloor likeFloor=new LikeFloor(uid,objectId);
                    likeFloorMapper.insert(likeFloor);
                    floorMapper.updateById(floor);
                }
                else
                {
                    floor.setLikeNum(floor.getLikeNum()-1);
                    LambdaQueryWrapper<LikeFloor> lqw=new LambdaQueryWrapper<>();
                    lqw.eq(LikeFloor::getUid,uid).eq(LikeFloor::getFid,objectId);
                    likeFloorMapper.delete(lqw);
                    floorMapper.updateById(floor);
                }
            }
            else if(tableType==TableType.reply)
            {
                Reply reply=replyMapper.selectById(objectId);
                if(likeType.isFlag())
                {
                    reply.setLikeNum(reply.getLikeNum()+1);
                    LikeReply likeReply=new LikeReply(uid,objectId);
                    likeReplyMapper.insert(likeReply);
                    replyMapper.updateById(reply);
                }
                else
                {
                    reply.setLikeNum(reply.getLikeNum()-1);
                    LambdaQueryWrapper<LikeReply> lqw=new LambdaQueryWrapper<>();
                    lqw.eq(LikeReply::getUid,uid).eq(LikeReply::getRid,objectId);
                    likeReplyMapper.delete(lqw);
                    replyMapper.updateById(reply);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("doLike失败");

        }
        return Result.succ();
    }
//一级评论
    @PostMapping("/doComment")
    public Object doComment(@RequestBody Floor floor,HttpServletRequest request)
    {
        try {
            String ip = GetIpAddressUtil.getIpInfo(request);
            floor.setLocation(ip);
            floorMapper.insert(floor);
            Post post = postMapper.selectById(floor.getPid());
            post.setCommentNum(post.getCommentNum()+1);
            postMapper.updateById(post);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("doComment失败");
        }
        return Result.succ();
    }
//二级评论
    @PostMapping("/doReply")
    public Object doReply(@RequestBody Reply reply,HttpServletRequest request)
    {
        try {
            String ip = GetIpAddressUtil.getIpInfo(request);
            reply.setLocation(ip);
            replyMapper.insert(reply);
            Floor floor = floorMapper.selectById(reply.getFid());
            floor.setReplyNum(floor.getReplyNum()+1);
            floorMapper.updateById(floor);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("doReply失败");
        }
        return Result.succ();
    }
//收藏
    @PostMapping("/doCollect")
    public Object doCollect(@RequestBody Collect collect)
    {
        try {
            collectMapper.insert(collect);
            Post post = postMapper.selectById(collect.getPid());
            post.setCollectNum(post.getCollectNum()+1);
            postMapper.updateById(post);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("n ");
        }
        return Result.succ();
    }
//分享
    @PostMapping("/doForward")
    public Object doForward(@RequestBody CID cid)
    {
        try {

            Post post = postMapper.selectById(cid.getCid());
            post.setForwardNum(post.getForwardNum()+1);
            postMapper.updateById(post);
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("doForward失败");
        }
        return Result.succ();
    }
//获取某个帖子评论信息
    @PostMapping("/getPostInfo")
    public Object getPostInfo(@RequestBody PostInfoRequestBody requestBody)
    {
        List<PostCommentItem> postCommentItems=new ArrayList<>();
        try {
            Post post = postMapper.selectById(requestBody.getPid());
            post.setViewNum(post.getViewNum()+1);
            postMapper.updateById(post);
            IPage<Floor> floorPage=new Page<>(requestBody.getCurrent(),pageSize);
            OrderType orderType = OrderType.values()[requestBody.getOrder()];
            LambdaQueryWrapper<Floor> lqw=new LambdaQueryWrapper<>();
            lqw.eq(Floor::getPid,requestBody.getPid());
            switch (orderType)
            {
                case Date:
                    lqw.orderByDesc(Floor::getDate);
                    break;
                case Like:
                    lqw.orderByDesc(Floor::getLikeNum);
                    break;
                default:
                    break;
            }
            floorMapper.selectPage(floorPage,lqw);

            List<Floor> floors=floorPage.getRecords();
            PostCommentItem postCommentItem;
            for (int i = 0; i < floors.size(); i++) {
                Floor floor = floors.get(i);
                FloorItem floorItem=new FloorItem(floor);
                Integer uid = floorItem.getOwner();
                floorItem.setUserName(getUserName(uid));

                floorItem.setLikeFlag(isLike(TableType.floor,requestBody.getUid(),floorItem.getId()));

                IPage<Reply> replyPage=new Page<>(1,pageSize);
                LambdaQueryWrapper<Reply> lqw1=new LambdaQueryWrapper<>();
                lqw1.eq(Reply::getFid,floor.getId())
                        .orderByDesc(Reply::getDate);
                replyMapper.selectPage(replyPage,lqw1);

                List<ReplyItem> replyItems=new ArrayList<>();
                ReplyItem replyItem;
                for (Reply record : replyPage.getRecords()) {
                    replyItem=new ReplyItem(record);
                    replyItem.setUserName(getUserName(replyItem.getOwner()));
                    if(replyItem.getOthers()!=null)
                        replyItem.setOthersName(getUserName(replyItem.getOthers()));
                    replyItem.setLikeFlag(isLike(TableType.reply,requestBody.getUid(),replyItem.getId()));
                    replyItems.add(replyItem);
                }

                postCommentItem=new PostCommentItem(floorItem,replyItems);
                postCommentItems.add(postCommentItem);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("getPostInfo失败");
        }
        return Result.succ(postCommentItems);
    }
//获取二级评论
    @PostMapping("/getReplies")
    public Object getReplies(@RequestBody RepliesInfoRequestBody requestBody)
    {
        IPage<Reply> replyPage=new Page<>(requestBody.getCurrent(),pageSize);

        ReplyPages replyPages=new ReplyPages();
        try {
            OrderType orderType = OrderType.values()[requestBody.getOrder()];
            LambdaQueryWrapper<Reply> lqw=new LambdaQueryWrapper<>();
            lqw.eq(Reply::getOwner,requestBody.getFid());
            switch (orderType)
            {
                case Date:
                    lqw.orderByDesc(Reply::getDate);
                    break;
                case Like:
                    lqw.orderByDesc(Reply::getLikeNum);
                    break;
                default:
                    break;
            }
            replyMapper.selectPage(replyPage,lqw);
            List<ReplyItem> replyItems=new ArrayList<>();
            ReplyItem replyItem;
            for (Reply record : replyPage.getRecords()) {
                replyItem=new ReplyItem(record);
                replyItem.setUserName(getUserName(record.getOwner()));
                if(record.getOthers()!=null)
                    replyItem.setOthersName(getUserName(record.getOthers()));
                replyItem.setLikeFlag(isLike(TableType.reply,requestBody.getUid(),record.getId()));
                replyItems.add(replyItem);
            }

            replyPages.setRecords(replyItems);
            replyPages.setInfo(replyPage);

        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("getReplies失败");
        }
        return Result.succ(replyPages);
    }
//我的收藏
    @PostMapping("/getCollect")
    public Object getCollect(@RequestBody CollectInfoRequestBody requestBody)
    {
        IPage page=new Page(requestBody.getCurrent(),8);
        PostPages postPages;
        try {
            List<Integer> pids=new ArrayList<>();
            LambdaQueryWrapper<Collect> lqw=new LambdaQueryWrapper<>();
            lqw.eq(Collect::getUid,requestBody.getUid());
            List<Collect> collects = collectMapper.selectList(lqw);
            for (int i = 0; i < collects.size(); i++) {
                pids.add(collects.get(i).getPid());
            }

            OrderType orderType = OrderType.values()[requestBody.getOrder()];
            LambdaQueryWrapper<Post> lqw1=new LambdaQueryWrapper<>();
            lqw1.in(Post::getId,pids);
            switch (orderType)
            {
                case Date:
                    lqw1.orderByDesc(Post::getDate);
                    break;
                case Like:
                    lqw1.orderByDesc(Post::getLikeNum);
                    break;
                case View:
                    lqw1.orderByDesc(Post::getViewNum);
                    break;
                case Comment:
                    lqw1.orderByDesc(Post::getCommentNum);
                    break;
                case Collect:
                    lqw1.orderByDesc(Post::getCollectNum);
                    break;
                default:
                    break;
            }
            postMapper.selectPage(page,lqw1);
//            postPages=addUserInfoToPost(page,requestBody.getUid());
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("getCollect失败");
        }
        return Result.succ(page);
    }
//    我的发帖
    @PostMapping("/getPostById")
    public Object getPostById(@RequestBody MyPostInfo info)
    {
        IPage<Post> page=new Page(info.getCurrent(),10);
        PostPages postPages;
        try {
            LambdaQueryWrapper<Post> lqw=new LambdaQueryWrapper();
            lqw.eq(Post::getOwner,info.getUid());
            OrderType orderType = OrderType.values()[info.getOrder()];
            switch (orderType)
            {
                case Date:
                    lqw.orderByDesc(Post::getDate);
                    break;
                case Like:
                    lqw.orderByDesc(Post::getLikeNum);
                    break;
                case View:
                    lqw.orderByDesc(Post::getViewNum);
                    break;
                case Comment:
                    lqw.orderByDesc(Post::getCommentNum);
                    break;
                case Collect:
                    lqw.orderByDesc(Post::getCollectNum);
                    break;
                default:
                    break;
            }
            postMapper.selectPage(page,lqw);
            postPages=addUserInfoToPost(page,info.getUid());

        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("getPostById失败");
        }
        return Result.succ(postPages);
    }
//获取用户所有评论（我的评论）
    @PostMapping("/getAllCommentById")
    public Object getAllCommentById(@RequestBody MyPostInfo info)
    {
        IPage<MyComment> page=new Page<>(info.getCurrent(),pageSize);
        try {
            OrderType orderType = OrderType.values()[info.getOrder()];
            switch (orderType)
            {
                case Date:
                    commentMapper.findAllComments((Page<MyComment>) page,info.getUid());
                    break;
                case Like:
                    commentMapper.findAllCommentsOfLikeNum((Page<MyComment>) page,info.getUid());
                    break;
                default:
                    break;
            }
            for (MyComment record : page.getRecords()) {
                if(record.getFlag()==1)
                    record.setLikeFlag(isLike(TableType.floor,info.getUid(),record.getId()));
                else if(record.getFlag()==2)
                    record.setLikeFlag(isLike(TableType.reply,info.getUid(),record.getId()));
                record.setUserName(getUserName(record.getOwner()));
                record.setOthersName(getUserName(record.getOthers()));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("getAllCommentById失败");
        }
        return Result.succ(page);
    }
//删帖
    @PostMapping("/deletePostsByIds")
    public Object deletePostsById(@RequestBody DeleteInfo info)
    {
        int count=0;
        try {
            User u = userService.getById(info.getUid());
            System.out.println(u.getStandard());
            if(u.getStandard() == 9){
                LambdaQueryWrapper<Post> lqw=new LambdaQueryWrapper<>();
                lqw.in(Post::getId,info.getObjectIds());
                count = postMapper.delete(lqw);
            }else{
                LambdaQueryWrapper<Post> lqw=new LambdaQueryWrapper<>();
                lqw.in(Post::getId,info.getObjectIds()).eq(Post::getOwner,info.getUid());
                count = postMapper.delete(lqw);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("deletePostsById失败");
        }
        return Result.succ(count);
    }
//删除收藏
    @PostMapping("/deleteCollectByIds")
    public Object deleteCollectByIds(@RequestBody DeleteInfo info)
    {
        int count=0;
        List<Integer> ids=info.getObjectIds();
        try {
            LambdaQueryWrapper<Collect> lqw=new LambdaQueryWrapper<>();
            lqw.eq(Collect::getUid,info.getUid()).in(Collect::getPid,ids);
            count=collectMapper.delete(lqw);
            for (int i = 0; i < ids.size(); i++) {
                Post post = postMapper.selectById(ids.get(i));
                post.setCollectNum(post.getCollectNum()-1);
                postMapper.updateById(post);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("deleteCollectByIds失败");
        }
        return Result.succ(count);
    }
//删除评论
    @PostMapping("/deleteComment")
    public Object deleteComment(@RequestBody DeleteInfo info)
    {
        int count=0;
        try {
            int objectId=info.getObjectIds().get(0);
            TableType tableType = TableType.values()[info.getType()];
            if(tableType==TableType.floor)
            {
                count=floorMapper.deleteById(objectId);
            }
            else if(tableType==TableType.reply)
            {
                count=replyMapper.deleteById(objectId);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return Result.fail("deleteComment失败");
        }
        return Result.succ(count);
    }


    private boolean isLike(TableType tableType,Integer uid,Integer pid)
    {
        if(uid==null || pid==null ) return false;
        if(tableType==TableType.post)
        {
            LambdaQueryWrapper<LikePost> lqw=new LambdaQueryWrapper();
            lqw.eq(LikePost::getUid,uid).eq(LikePost::getPid,pid);
            List<LikePost> likePosts = likePostMapper.selectList(lqw);
            if(likePosts.size()!=0) return true;
            else return false;
        }
        else if(tableType==TableType.floor)
        {
            LambdaQueryWrapper<LikeFloor> lqw=new LambdaQueryWrapper();
            lqw.eq(LikeFloor::getUid,uid).eq(LikeFloor::getFid,pid);
            List<LikeFloor> likeFloors = likeFloorMapper.selectList(lqw);
            if(likeFloors.size()!=0) return true;
            else return false;
        }
        else if(tableType==TableType.reply)
        {
            LambdaQueryWrapper<LikeReply> lqw=new LambdaQueryWrapper<>();
            lqw.eq(LikeReply::getUid,uid).eq(LikeReply::getRid,pid);
            List<LikeReply> likeReplies = likeReplyMapper.selectList(lqw);
            if(likeReplies.size()!=0) return true;
            else return false;
        }
        return false;
    }

    private boolean isCollect(Integer uid,Integer pid)
    {
        LambdaQueryWrapper<Collect> lqw=new LambdaQueryWrapper<>();
        lqw.eq(Collect::getUid,uid).eq(Collect::getPid,pid);
        List<Collect> collects = collectMapper.selectList(lqw);
        if(collects.size()!=0) return true;
        else return false;
    }

    private String getUserName(int id)
    {
        User user = userMapper.selectById(id);
        return user.getUsername();
    }

    private PostPages addUserInfoToPost(IPage<Post> page,Integer uid)
    {
        PostPages postPages=new PostPages();
        List<PostItem> postItems=new ArrayList<>();
        PostItem postItem;
        for (Post post : page.getRecords()) {
            postItem=new PostItem(post);
            Integer universityId = postItem.getUniversity();
            School school = schoolMapper.selectById(universityId);
            postItem.setUserName(getUserName(postItem.getOwner()));
            postItem.setUniversityName(school.getSchoolname());
            postItem.setLikeFlag(isLike(TableType.post,uid,postItem.getId()));
            postItem.setCollectFlag(isCollect(uid,postItem.getId()));
            postItems.add(postItem);
        }
        postPages.setInfo(page);
        postPages.setRecords(postItems);
        return postPages;
    }
}
