package com.campus.backend.mapper;

import com.campus.backend.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author campus
 * @since 2022-12-30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
