package com.qiumu;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiumu.Dto.DishDto;
import com.qiumu.mapper.DishMapper;
import com.qiumu.pojo.Dish;
import com.qiumu.pojo.PageBean;
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
    private DishService dishService;
    @Autowired
    private DishMapper dishMapper;

    @Test
    void test01() {


    }
}
