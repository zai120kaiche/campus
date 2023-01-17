package com.campus.backend;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.*;
import com.campus.backend.mapper.LikePostMapper;
import com.campus.backend.mapper.PostMapper;
import com.campus.backend.mapper.TypeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private PostMapper postMapper;


    @Autowired
    private LikePostMapper likePostMapper;

    @Test
    void contextLoads() {
        LambdaQueryWrapper<LikePost> lqw=new LambdaQueryWrapper();
        lqw.eq(LikePost::getUid,1).eq(LikePost::getPid,100001);
        List<LikePost> likePosts = likePostMapper.selectList(lqw);
        if(likePosts.size()!=0) System.out.println(true);
        else System.out.println(false);
    }

}
