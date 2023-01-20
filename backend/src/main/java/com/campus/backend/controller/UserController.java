package com.campus.backend.controller;


import com.campus.backend.common.lang.Result;
import com.campus.backend.entity.User;
import com.campus.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author campus
 * @since 2022-12-30
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/{id}")
    public Object test(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @PostMapping("/info")
    public Object test(@RequestBody User user) {
        return userService.getById(user.getId());
    }

    //修改个人信息
    @PostMapping("/change")
    public Result change(@RequestBody User user){
        if(user.getId() != null){
            userService.saveOrUpdate(user);
            return Result.succ();
        } else {
            return Result.fail("信息不存在");
        }
    }





}
