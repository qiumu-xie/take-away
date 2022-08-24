package com.qiumu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiumu.exception.CostException;
import com.qiumu.pojo.Category;
import com.qiumu.pojo.Dish;
import com.qiumu.pojo.Setmeal;
import com.qiumu.service.CategoryService;
import com.qiumu.mapper.CategoryMapper;
import com.qiumu.service.DishService;
import com.qiumu.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 86134
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2022-08-18 19:30:39
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    DishService dishService;

    @Autowired
    SetmealService setmealService;

    @Override
    public void delete(Long id){
        LambdaQueryWrapper<Dish> queryWrapper1 =new LambdaQueryWrapper<>();
        queryWrapper1.eq(Dish::getCategoryId,id);
        long count1 = dishService.count(queryWrapper1);
        if (count1 > 0) {
            throw new CostException("此分类绑定了菜品，无法删除");
        }

        LambdaQueryWrapper<Setmeal> queryWrapper2 =new LambdaQueryWrapper<>();
        queryWrapper2.eq(Setmeal::getCategoryId,id);
        long count2 = setmealService.count(queryWrapper2);
        if (count2 > 0) {
            throw new CostException("此分类绑定了套餐，无法删除");
        }

        super.removeById(id);

    }

}




