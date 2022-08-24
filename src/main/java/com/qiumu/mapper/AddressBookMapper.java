package com.qiumu.mapper;

import com.qiumu.pojo.AddressBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86134
* @description 针对表【address_book(地址管理)】的数据库操作Mapper
* @createDate 2022-08-20 19:47:16
* @Entity com.qiumu.pojo.AddressBook
*/
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}




