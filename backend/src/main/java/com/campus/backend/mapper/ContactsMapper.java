package com.campus.backend.mapper;

import com.campus.backend.entity.Contacts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author campus
 * @since 2023-01-28
 */
@Mapper
public interface ContactsMapper extends BaseMapper<Contacts> {

}
