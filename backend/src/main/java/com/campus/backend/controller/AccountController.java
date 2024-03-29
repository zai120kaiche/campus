package com.campus.backend.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.campus.backend.annotation.AccessLimit;
import com.campus.backend.service.UserService;

import com.campus.backend.common.dto.LoginDto;
import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.User;
import com.campus.backend.service.UserService;
import com.campus.backend.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static com.campus.backend.tool.EmailUtil.get_code_6bit;
import static com.campus.backend.tool.EmailUtil.send_email;
import static com.campus.backend.tool.PhoneUtil.send_phone;


@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/AlipayTest")
    public Result test(){
        String code=get_code_6bit();
        String phoneOrEmail = "19801283183";


        send_phone(phoneOrEmail, code);


        return Result.succ();
    }

    @PostMapping("userStandardCheck")
    public Result check(@RequestBody User user){
        try{
            User u = userService.getById(user.getId());
            if (u.getStandard() == 9){
                return Result.succ();
            }else{
                return Result.fail("");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AccessLimit(seconds=30,days = 60*60*24, perCount = 5, dayCount = 500, needLogin = false)
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        String phoneOrEmail = loginDto.getPhoneOrEmail();
        Integer flag = loginDto.getFlag();
        User user;
        System.out.println(loginDto);
        if(phoneOrEmail.contains("@")){
            user = userService.getOne(new QueryWrapper<User>().eq("email", phoneOrEmail));
        } else {
            user = userService.getOne(new QueryWrapper<User>().eq("phone", phoneOrEmail));
        }
        Assert.notNull(user, "用户不存在");
        if(flag == null){
            System.out.println();
            if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
                return Result.fail("密码不正确");
            }
        }

        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        System.out.print(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map());
        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .put("phone", user.getPhone())
                .map()
        );
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

    @PostMapping("register")
    public Result register(@RequestBody User user){
        int count = 0;
        if(user.getPhone() != "")
            count += userService.count(new QueryWrapper<User>().eq("phone", user.getPhone()));
        if(user.getEmail() != "")
            count += userService.count(new QueryWrapper<User>().eq("email", user.getEmail()));
        if(user.getUsername() == null || user.getPassword() == null){
            return Result.fail("账号信息缺失");
        }
        else if(count > 0){
            return Result.succ(1);
        } else {
            user.setPassword(SecureUtil.md5(user.getPassword()));
            user.setMask(getMask(user.getUsername()));
            user.setScore(0);
            user.setStandard(0);
            userService.saveOrUpdate(user);
        }
        return Result.succ();
    }

    @PostMapping("user/index")
    public Result getUserInfo(@RequestBody User user){
        User re = userService.getById(user.getId());
        re.setPassword("");
        return Result.succ(re);
    }

    private String getMask(String username)
    {
        int num = (int)(Math.random()*10000);
        String s1 = SecureUtil.md5(String.valueOf(num));
        String s2 = SecureUtil.md5(username);
        String s="";
        for(int i=0;i<s1.length();i++)
        {
            s+=s1.charAt(i);
            s+=s2.charAt(i);
        }
        int r = num%(s1.length()*2-15);
        return s.substring(r , r + 10);
    }
}
