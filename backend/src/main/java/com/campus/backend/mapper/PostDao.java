package com.campus.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.backend.entity.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostDao extends BaseMapper<Post> {
}