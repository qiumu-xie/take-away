package com.qiumu.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiumu.Dto.DishDto;
import com.qiumu.pojo.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 86134
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2022-08-18 21:10:10
* @Entity com.qiumu.pojo.Dish
*/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

    Page<DishDto> findAllAndPage(Page<Dish> page,@Param("name") String name);

}




