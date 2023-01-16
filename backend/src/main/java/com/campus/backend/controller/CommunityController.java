package com.campus.backend.controller;

import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.Post;
import com.campus.backend.service.PostService;
import com.campus.backend.tool.GetIpAddressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/community")
public class CommunityController {
    @Autowired
    PostService postService;

    @PostMapping("/addpost")
    public Object addPost(@RequestBody Post post, HttpServletRequest request){
        System.out.println(post);
        String ip = GetIpAddressUtil.getIpInfo(request);
        post.setLocation(ip);
        postService.saveOrUpdate(post);

        return Result.succ();
    }
}
