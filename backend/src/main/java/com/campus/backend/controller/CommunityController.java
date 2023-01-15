package com.campus.backend.controller;

import com.campus.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunityController {
    @Autowired
    PostService postService;

    
}
