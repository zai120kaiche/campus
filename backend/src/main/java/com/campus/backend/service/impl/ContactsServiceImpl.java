package com.campus.backend.service.impl;

import com.campus.backend.entity.Contacts;
import com.campus.backend.mapper.ContactsMapper;
import com.campus.backend.service.ContactsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author campus
 * @since 2023-01-28
 */
@Service
public class ContactsServiceImpl extends ServiceImpl<ContactsMapper, Contacts> implements ContactsService {

}
