package com.qiumu.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiumu.pojo.AddressBook;
import com.qiumu.service.AddressBookService;
import com.qiumu.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author 86134
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2022-08-20 19:47:16
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

    @Override
    @Transactional
    public void updateDefault(Long id) {

        LambdaUpdateWrapper<AddressBook> updateWrapper =new LambdaUpdateWrapper<>();
        updateWrapper.eq(AddressBook::getIsDefault,1).set(AddressBook::getIsDefault,0);
        this.update(updateWrapper);

        updateWrapper.clear();

        updateWrapper.eq(AddressBook::getId,id).set(AddressBook::getIsDefault,1);
        this.update(updateWrapper);
    }
}




