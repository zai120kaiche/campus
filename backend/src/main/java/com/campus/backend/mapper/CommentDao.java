package com.campus.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.backend.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentDao extends BaseMapper<Comment> {
    IPage<Comment> findAllComments(Page<Comment> page, int id); //按日期降序
    IPage<Comment> findAllCommentsOfLikeNum(Page<Comment> page,int id); //按点赞数降序
}