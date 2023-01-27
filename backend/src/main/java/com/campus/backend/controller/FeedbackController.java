package com.campus.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.Feedback;
import com.campus.backend.entity.School;
import com.campus.backend.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author campus
 * @since 2023-01-26
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @PostMapping("/send")
    public Result send(@RequestBody Feedback feedback){
        try {
            feedback.setDealFlag(false);
            feedbackService.saveOrUpdate(feedback);
            return Result.succ();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @PostMapping("/deal")
    public Result deal(@RequestBody Feedback feedback){
        try {
            feedback.setDealFlag(true);
            feedbackService.saveOrUpdate(feedback);
            return Result.succ();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        try {
            Page page = new Page(currentPage, 10);
            IPage pageData = feedbackService.page(page, new QueryWrapper<Feedback>().orderByAsc("id"));
            return Result.succ(pageData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/cancle")
    public Result cancle(@RequestBody Feedback feedback){
        try {
            return Result.succ(feedbackService.removeById(feedback.getId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
