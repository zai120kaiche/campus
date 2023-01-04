package com.campus.backend.controller;


import com.campus.backend.annotation.AccessLimit;
import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.Identifier;
import com.campus.backend.entity.User;
import com.campus.backend.redis.RedisOperator;
import com.campus.backend.service.IdentifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import static com.campus.backend.tool.EmailUtil.get_code_6bit;
import static com.campus.backend.tool.EmailUtil.send_email;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author campus
 * @since 2023-01-04
 */
@RestController
public class IdentifierController {
    @Autowired
    IdentifierService identifierService;

    @Autowired
    RedisOperator redisOperator;

    /* 用户邮箱验证码发送 */
    /* 限制次数：30s一次 */
    @AccessLimit(seconds=30,days = 60*60*24, perCount = 1, dayCount = 50000, needLogin = false)
    @PostMapping("/identify/email/send")
    public Object emailSend(@RequestBody Identifier identifier) {
        String code=get_code_6bit();
        String emailAddress = identifier.getEmailAddress();

        redisOperator.set(emailAddress, code, 30);

        send_email(emailAddress, code);
        return Result.succ();
    }

    /* 用户邮箱验证码检验 */
    /* 限制次数：30s十次 */
    @AccessLimit(seconds=30,days = 60*60*24, perCount = 10, dayCount = 50000, needLogin = false)
    @PostMapping("/identify/email/check")
    public Object emailCheck(@RequestBody Identifier identifier) {

        String code = String.valueOf(identifier.getIdentifier());
        String emailAddress = identifier.getEmailAddress();
        String identify = redisOperator.get(emailAddress);
        if(code.equals(identify))
            return Result.succ();
        else
            return Result.fail(String.valueOf(400));
    }

}
