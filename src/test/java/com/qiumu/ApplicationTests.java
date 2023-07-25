package com.qiumu;

import com.qiumu.mapper.AddressBookMapper;
import com.qiumu.mapper.DishMapper;
import com.qiumu.pojo.AddressBook;
import com.qiumu.service.AddressBookService;
import com.qiumu.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class ApplicationTests {
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private AddressBookMapper addressBookMapper;

    @Test
    void test01() {
        List<AddressBook> list = addressBookService.list();
        System.out.println(list);

    }
}
