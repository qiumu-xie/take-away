package com.qiumu.mapper;

import com.qiumu.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86134
* @description 针对表【user(用户信息)】的数据库操作Mapper
* @createDate 2022-08-20 13:18:54
* @Entity com.qiumu.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




