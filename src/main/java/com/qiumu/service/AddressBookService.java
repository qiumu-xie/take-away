package com.qiumu.service;

import com.qiumu.pojo.AddressBook;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86134
* @description 针对表【address_book(地址管理)】的数据库操作Service
* @createDate 2022-08-20 19:47:16
*/
public interface AddressBookService extends IService<AddressBook> {

    void updateDefault(Long id);
}
