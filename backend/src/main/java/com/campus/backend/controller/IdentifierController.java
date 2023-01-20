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
import static com.campus.backend.tool.PhoneUtil.send_phone;

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

    /* 用户验证码发送 */
    /* 限制次数：60s一次 */
    @AccessLimit(seconds=60,days = 60*60*24, perCount = 1, dayCount = 50000, needLogin = false)
    @PostMapping("/identify/send")
    public Object emailSend(@RequestBody Identifier identifier) {
        String code=get_code_6bit();
        String phoneOrEmail = identifier.getPhoneOrEmail();

        redisOperator.set(phoneOrEmail, code, 60);
        if(phoneOrEmail.contains("@")){
            send_email(phoneOrEmail, code);
        } else {
            send_phone(phoneOrEmail, code);
        }

        return Result.succ();
    }

    /* 用户验证码检验 */
    /* 限制次数：60s十次 */
    @AccessLimit(seconds=60,days = 60*60*24, perCount = 10, dayCount = 50000, needLogin = false)
    @PostMapping("/identify/check")
    public Object emailCheck(@RequestBody Identifier identifier) {

        String code = String.valueOf(identifier.getIdentifier());
        String phoneOrEmail = identifier.getPhoneOrEmail();
        String identify = redisOperator.get(phoneOrEmail);
        if(code.equals(identify))
            return Result.succ();
        else
            return Result.fail(String.valueOf(400));
    }

}
