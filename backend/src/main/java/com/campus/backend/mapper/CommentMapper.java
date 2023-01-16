package com.campus.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.backend.entity.MyComment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<MyComment> {
    IPage<MyComment> findAllComments(Page<MyComment> page, int id); //按日期降序
    IPage<MyComment> findAllCommentsOfLikeNum(Page<MyComment> page,int id); //按点赞数降序

}
