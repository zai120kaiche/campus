package com.campus.backend.service.impl;

import com.campus.backend.entity.Type;
import com.campus.backend.mapper.TypeMapper;
import com.campus.backend.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author campus
 * @since 2023-01-13
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

}
