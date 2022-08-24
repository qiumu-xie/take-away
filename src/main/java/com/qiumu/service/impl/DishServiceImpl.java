package com.qiumu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiumu.Dto.DishDto;
import com.qiumu.mapper.DishFlavorMapper;
import com.qiumu.pojo.Dish;
import com.qiumu.pojo.DishFlavor;
import com.qiumu.service.DishFlavorService;
import com.qiumu.service.DishService;
import com.qiumu.mapper.DishMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.datatransfer.DataFlavor;
import java.util.List;

/**
* @author 86134
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2022-08-18 21:10:10
*/

@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorService flavorService;

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);

        Long id = dishDto.getId();

        dishDto.getFlavors().forEach(objs -> objs.setDishId(id));

        flavorService.saveBatch(dishDto.getFlavors());

    }

    @Override
    @Transactional
    public void putWithFlavor(DishDto dishDto) {
        this.updateById(dishDto);

        Long id = dishDto.getId();

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,id);
        flavorService.remove(queryWrapper);

        dishDto.getFlavors().forEach(objs -> objs.setDishId(id));
        flavorService.saveBatch(dishDto.getFlavors());

    }

    @Override
    @Transactional
    public void delWithFlavor(String ids) {
        String[] strings = ids.split(",");
        for (String id : strings) {
            Long Id = Long.valueOf(id);
            this.removeById(Id);
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId,Id);
            flavorService.remove(queryWrapper);
        }
    }

    @Override
    public Page<DishDto> listPage(Page<Dish> page, String name) {

        if (StringUtils.isNotBlank(name)){
            name = "%" + name + "%";
        }

        return dishMapper.findAllAndPage(page,name);

    }
}




