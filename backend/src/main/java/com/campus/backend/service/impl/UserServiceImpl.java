package com.campus.backend.service.impl;

import com.campus.backend.entity.User;
import com.campus.backend.mapper.UserMapper;
import com.campus.backend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author campus
 * @since 2022-12-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
