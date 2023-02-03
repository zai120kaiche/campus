package com.campus.backend;

import cn.hutool.crypto.SecureUtil;
import com.campus.backend.mapper.LikePostMapper;
import com.campus.backend.mapper.PostMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private PostMapper postMapper;


    @Autowired
    private LikePostMapper likePostMapper;

    @Test
    void contextLoads() {
        System.out.println(SecureUtil.md5("10000"));
    }

}
