package com.campus.backend.service.impl;

import com.campus.backend.entity.Chat;
import com.campus.backend.mapper.ChatMapper;
import com.campus.backend.service.ChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author campus
 * @since 2023-01-25
 */
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {

}
