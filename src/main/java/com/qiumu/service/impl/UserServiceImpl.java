package com.qiumu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiumu.pojo.User;
import com.qiumu.service.UserService;
import com.qiumu.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 86134
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2022-08-20 13:18:54
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




