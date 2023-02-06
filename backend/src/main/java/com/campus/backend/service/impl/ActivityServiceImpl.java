package com.campus.backend.service.impl;

import com.campus.backend.entity.Activity;
import com.campus.backend.mapper.ActivityMapper;
import com.campus.backend.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author campus
 * @since 2023-02-06
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

}
