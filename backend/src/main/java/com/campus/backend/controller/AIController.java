package com.campus.backend.controller;

import com.campus.backend.annotation.AccessLimit;
import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.Ai;
import com.campus.backend.entity.Identifier;
import com.campus.backend.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.campus.AIDraw.AIservice.openAIUtil.interactive;

@RestController
public class AIController {
    @Autowired
    AiService aiService;

    /* AI客服调用接口 */
    /* 访问次数限制：3s一次 */
    @PostMapping("/ai/send")
    @AccessLimit(seconds=3,days = 60*60*24, perCount = 1, dayCount = 50000, needLogin = false)
    public Object AISend(@RequestBody Ai ai){
        String context = ai.getRequest();
        String result = interactive(context);
        return Result.succ(result);
    }

}
