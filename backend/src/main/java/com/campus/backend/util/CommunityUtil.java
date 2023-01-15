package com.campus.backend.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.backend.entity.*;
import com.campus.backend.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class CommunityUtil {
    private int pageSize=10;
    private static CommunityUtil communityUtil;

    @Autowired
    private PostDao postDao;
    @Autowired
    private CollectDao collectDao;
    @Autowired
    private FloorDao floorDao;
    @Autowired
    private ReplyDao replyDao;
    @Autowired
    private CommentDao commentDao;

    @PostConstruct
    public void init()
    {
        communityUtil=this;
        communityUtil.postDao=this.postDao;
        communityUtil.collectDao=this.collectDao;
        communityUtil.floorDao=this.floorDao;
        communityUtil.replyDao=this.replyDao;
        communityUtil.commentDao=this.commentDao;
    }

    /**
     * 向post表中插入一条数据
     * @param title 帖子名
     * @param content 帖子内容
     * @param location ip位置
     * @param id 发帖人id
     * @param kind 帖子类别
     * @return 返回1表示发帖成功，0则失败
     */
    public int doPost(String title,String content,String location,int id,int kind)
    {
        Post post=new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setLocation(location);
        post.setOwner(id);
        post.setKind(kind);
        return communityUtil.postDao.insert(post);
    }

    /**
     *
     * @param id 帖子id
     * @return
     */
    public Post getPostById(int id)
    {
        return communityUtil.postDao.selectById(id);
    }

    /**
     * 批量删除
     * @param ids 帖子id的列表
     * @return 删除的帖子数目
     */
    public int deletePostByIds(List<Integer> ids)
    {
        return communityUtil.postDao.deleteBatchIds(ids);
    }
    public int deletePostById(int id)
    {
        return communityUtil.postDao.deleteById(id);
    }


    /**
     * 控制点赞数+1或是-1
     * @param type 点赞内容的类型，是帖子，评论，还是二级回复
     * @param flag true为点赞，false为点踩
     * @param id 点赞帖子,楼层或者楼层内回复的id
     * @return 1成功，0失败
     */
    public int doLike(TableType type, boolean flag, int id)
    {
        if(type==TableType.post)
        {
            Post post = communityUtil.postDao.selectById(id);
            if(flag)
                post.setLikeNum(post.getLikeNum()+1);
            else
                post.setLikeNum(post.getLikeNum()-1);
            return communityUtil.postDao.updateById(post);
        }
        else if(type==TableType.floor)
        {
            Floor floor=communityUtil.floorDao.selectById(id);
            if(flag)
                floor.setLikeNum(floor.getLikeNum()+1);
            else
                floor.setLikeNum(floor.getLikeNum()-1);
            return communityUtil.floorDao.updateById(floor);
        }
        else if(type==TableType.reply)
        {
            Reply reply=communityUtil.replyDao.selectById(id);
            if(flag)
                reply.setLikeNum(reply.getLikeNum()+1);
            else
                reply.setLikeNum(reply.getLikeNum()-1);
            return communityUtil.replyDao.updateById(reply);
        }
        return 0;
    }
    public int addViewNum(int id)
    {
        Post post = communityUtil.postDao.selectById(id);
        post.setViewNum(post.getViewNum()+1);
        System.out.println(post.getViewNum()+"   "+post.getId());
        return communityUtil.postDao.updateById(post);
    }
    public int addCommentNum(TableType type,boolean flag, int id)
    {
        if(type==TableType.post)
        {
            Post post = communityUtil.postDao.selectById(id);
            if(flag)
                post.setCommentNum(post.getCommentNum()+1);
            else post.setCommentNum(post.getCommentNum()-1);
            return communityUtil.postDao.updateById(post);
        }
        else if(type==TableType.floor)
        {
            Floor floor=communityUtil.floorDao.selectById(id);
            if(flag)
                floor.setReplyNum(floor.getReplyNum()+1);
            else floor.setReplyNum(floor.getReplyNum()-1);
            return communityUtil.floorDao.updateById(floor);
        }
        return 0;
    }
    public int addCollectNum(boolean flag, int pid)
    {
        Post post = communityUtil.postDao.selectById(pid);
        if(flag)
            post.setCollectNum(post.getCollectNum()+1);
        else  post.setCollectNum(post.getCollectNum()-1);
        return communityUtil.postDao.updateById(post);
    }
    public int addForwardNum(int id)
    {
        Post post = communityUtil.postDao.selectById(id);
        post.setForwardNum(post.getForwardNum()+1);
        return communityUtil.postDao.updateById(post);
    }

    /**
     * 收藏功能，添加收藏关系，并使收藏数+1
     * @param uid 收藏者的用户id
     * @param pid 被收藏帖子的id
     * @return 1表示成功，0失败
     */
//    @Transactional
    public int doCollect(int uid,int pid)
    {
        Collect collect=new Collect();
        collect.setPid(pid);
        collect.setUid(uid);
        try {
            addCollectNum(true,pid);
            communityUtil.collectDao.insert(collect);
        }catch (Exception e)
        {
            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return 1;
    }
    //    @Transactional
    public int deleteCollectItem(int uid,int pid)
    {
        LambdaQueryWrapper<Collect> lqw=new LambdaQueryWrapper<>();
        lqw.eq(Collect::getPid,pid).eq(Collect::getUid,uid);
        try {
            addCollectNum(false,pid);
            communityUtil.collectDao.delete(lqw);
        }catch (Exception e)
        {
            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return 1;
    }
    //    @Transactional
    public int deleteCollectByIdList(int uid,List<Integer> ids)
    {
        LambdaQueryWrapper<Collect> lqw=new LambdaQueryWrapper<>();
        lqw.eq(Collect::getUid,uid).in(Collect::getPid,ids);
        try {
            communityUtil.collectDao.delete(lqw);
            for (int i = 0; i < ids.size(); i++) {
                addCollectNum(false,ids.get(i));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return 1;
    }

    /**
     * 对帖子的评论：
     * 1.插入floor表
     * 2.更新post表中的评论数
     *
     * @param owner 评论用户的id
     * @param content 评论的内容
     * @param location ip
     * @param pid 被评论帖子的id
     * @return 1成功，0失败
     */
//    @Transactional
    public int doComment(int owner,String content,String location,int pid)
    {
        Floor floor=new Floor();
        floor.setOwner(owner);
        floor.setContent(content);
        floor.setLocation(location);
        floor.setPid(pid);
        try {
            communityUtil.floorDao.insert(floor);
            addCommentNum(TableType.post,true,pid);
        }catch (Exception e)
        {
            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return 1;
    }

    /**
     * 删除一批评论
     * @param ids <fid,pid>
     * @return
     */
//    @Transactional
    public int deleteFloorByIds(Map<Integer,Integer> ids)
    {
        try {
            Set<Integer> fids = ids.keySet();
            communityUtil.floorDao.deleteBatchIds(fids);
            for(Integer fid : fids)
            {
                addCommentNum(TableType.post,false,ids.get(fid));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return 1;
    }
    //    @Transactional
    public int deleteFloorById(int fid,int pid)
    {
        try {
            communityUtil.floorDao.deleteById(fid);
            addCommentNum(TableType.post,false,pid);
        }catch (Exception e)
        {
            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return 1;
    }

    /**
     * 1.内容插入reply表
     * 2.更新floor表
     *
     * @param owner 回复人
     * @param others 被回复的人，可为空
     * @param content 内容
     * @param location ip
     * @param fid 所属以及评论的id
     * @return 1成功，0失败
     */
//    @Transactional
    public int doReply(int owner,Integer others,String content,String location,int fid)
    {
        Reply reply=new Reply();
        reply.setOwner(owner);
        reply.setOthers(others);
        reply.setContent(content);
        reply.setLocation(location);
        reply.setFid(fid);
        try {
            communityUtil.replyDao.insert(reply);
            addCommentNum(TableType.floor,true,fid);
        }catch (Exception e)
        {
            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return 1;
    }

    /**
     * 批量删除二级评论
     * @param ids <rid,fid>
     * @return
     */
//    @Transactional
    public int deleteReplyByIds(Map<Integer,Integer> ids)
    {
        try {
            Set<Integer> rids = ids.keySet();
            communityUtil.replyDao.deleteBatchIds(rids);
            for(Integer rid : rids)
            {
                addCommentNum(TableType.floor,false,ids.get(rid));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return 1;
    }
    //    @Transactional
    public int deleteReplyById(int rid,int fid)
    {
        try {
            communityUtil.replyDao.deleteById(rid);
            addCommentNum(TableType.floor,false,fid);
        }catch (Exception e)
        {
            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return 1;
    }

    /**
     * 按日期降序排，即获取最新发布的
     * @param current 页码
     * @param orderType 排序类型
     * @return
     */
    public IPage getPostPageItems(int current, @NotNull OrderType orderType)
    {
        IPage page=new Page(current,pageSize);
        LambdaQueryWrapper<Post> lqw=new LambdaQueryWrapper<>();
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
        communityUtil.postDao.selectPage(page,lqw);
        return page;
    }

    public IPage getFloorPageItems(int current, @NotNull OrderType orderType)
    {
        IPage page=new Page(current,pageSize);
        LambdaQueryWrapper<Floor> lqw=new LambdaQueryWrapper<>();
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
        communityUtil.floorDao.selectPage(page,lqw);
        return page;
    }

    public IPage getReplyPageItems(int current, @NotNull OrderType orderType)
    {
        IPage page=new Page(current,pageSize);
        LambdaQueryWrapper<Reply> lqw=new LambdaQueryWrapper<>();
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
        communityUtil.replyDao.selectPage(page,lqw);
        return page;
    }

    public IPage getPostPagesByUid(int uid,int current, @NotNull OrderType orderType)
    {
        IPage page=new Page(current,pageSize);
        LambdaQueryWrapper<Post> lqw=new LambdaQueryWrapper<>();
        lqw.eq(Post::getOwner,uid);
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
        communityUtil.postDao.selectPage(page,lqw);
        return page;
    }

    public IPage getPostPagesByKind(int kind,int current, @NotNull OrderType orderType)
    {
        IPage page=new Page(current,pageSize);
        LambdaQueryWrapper<Post> lqw=new LambdaQueryWrapper<>();
        lqw.eq(Post::getKind,kind);
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
        communityUtil.postDao.selectPage(page,lqw);
        return page;
    }

    //获取指定用户的所有评论，包含一级和二级，参见实体类Comment
    public IPage getCommentPagesByUid(int uid,int current, @NotNull OrderType orderType)
    {
        IPage<Comment> page=new Page<>(current,pageSize);
        switch (orderType)
        {
            case Date:
                communityUtil.commentDao.findAllComments((Page<Comment>) page,uid);
                break;
            case Like:
                communityUtil.commentDao.findAllCommentsOfLikeNum((Page<Comment>) page,uid);
                break;
            default:
                break;
        }
        return page;
    }

    public IPage searchPost(List<String> keyWord ,int current, @NotNull OrderType orderType)
    {
        IPage page=new Page(current,pageSize);
        LambdaQueryWrapper<Post> lqw=new LambdaQueryWrapper<>();
        for (int i = 0; i <keyWord.size(); i++) {
            lqw.like(Post::getTitle,keyWord.get(i)).or().like(Post::getContent,keyWord.get(i));
            if(i!=keyWord.size()-1) lqw.or();
        }
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
        communityUtil.postDao.selectPage(page,lqw);
        return page;
    }

    public IPage getCollectPostById(int uid,int current, @NotNull OrderType orderType)
    {
        List<Integer> pids=new ArrayList<>();
        IPage page=new Page(current,pageSize);
        LambdaQueryWrapper<Post> lqw=new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Collect> lqw1=new LambdaQueryWrapper<>();
        lqw1.eq(Collect::getUid,uid);
        List<Collect> collects = communityUtil.collectDao.selectList(lqw1);
        for (int i = 0; i < collects.size(); i++) {
            pids.add(collects.get(i).getPid());
        }
        lqw.in(Post::getId,pids);
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
        communityUtil.postDao.selectPage(page,lqw);
        return page;
    }
}
