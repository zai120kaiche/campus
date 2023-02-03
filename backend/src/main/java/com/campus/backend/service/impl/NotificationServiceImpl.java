package com.campus.backend.service.impl;

import com.campus.backend.entity.Notification;
import com.campus.backend.mapper.NotificationMapper;
import com.campus.backend.service.NotificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author campus
 * @since 2023-02-03
 */
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {

}
