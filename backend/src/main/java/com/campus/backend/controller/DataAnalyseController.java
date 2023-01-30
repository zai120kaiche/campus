package com.campus.backend.controller;

import com.campus.backend.service.PostService;
import com.campus.backend.service.SchoolService;
import com.campus.backend.service.TradetypeService;
import com.campus.backend.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataAnalyseController {
    @Autowired
    SchoolService schoolService;

    @Autowired
    TypeService typeService;

    @Autowired
    TradetypeService tradetypeService;

    @Autowired
    PostService postService;


}
