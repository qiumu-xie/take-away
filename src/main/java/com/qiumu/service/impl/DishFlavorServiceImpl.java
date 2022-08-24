package com.qiumu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiumu.pojo.DishFlavor;
import com.qiumu.service.DishFlavorService;
import com.qiumu.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author 86134
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2022-08-19 14:36:12
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




