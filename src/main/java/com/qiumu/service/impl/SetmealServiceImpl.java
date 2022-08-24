package com.qiumu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiumu.Dto.SetmealDto;
import com.qiumu.pojo.DishFlavor;
import com.qiumu.pojo.Setmeal;
import com.qiumu.pojo.SetmealDish;
import com.qiumu.service.SetmealDishService;
import com.qiumu.service.SetmealService;
import com.qiumu.mapper.SetmealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 86134
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2022-08-18 21:10:10
*/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{

    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    @Transactional
    public void saveWithFlavor(SetmealDto setmealDto) {
        this.save(setmealDto);

        Long id = setmealDto.getId();

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes.forEach(temp -> temp.setSetmealId(id.toString()));

        setmealDishService.saveBatch(setmealDishes);

    }

    @Override
    @Transactional
    public void putWithFlavor(SetmealDto setmealDto) {
        this.updateById(setmealDto);

        Long id = setmealDto.getId();

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        setmealDishService.remove(queryWrapper);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.forEach(objs -> objs.setSetmealId(id.toString()));
        setmealDishService.saveBatch(setmealDishes);

    }

    @Override
    @Transactional
    public void delWithFlavor(String ids) {
        String[] strings = ids.split(",");
        for (String id : strings) {
            this.removeById(id);
            LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SetmealDish::getSetmealId,id);
            setmealDishService.remove(queryWrapper);
        }
    }
}




