package com.qiumu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiumu.Dto.DishDto;
import com.qiumu.pojo.Dish;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
* @author 86134
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2022-08-18 21:10:10
*/
public interface DishService extends IService<Dish> {

    void saveWithFlavor(DishDto dishDto);

    void putWithFlavor(DishDto dishDto);

    void delWithFlavor(String ids);

    Page<DishDto> listPage(Page<Dish> page,String name);

}
