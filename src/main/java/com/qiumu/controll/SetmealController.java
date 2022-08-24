package com.qiumu.controll;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiumu.Dto.DishDto;
import com.qiumu.Dto.SetmealDto;
import com.qiumu.common.R;
import com.qiumu.pojo.*;
import com.qiumu.service.CategoryService;
import com.qiumu.service.DishService;
import com.qiumu.service.SetmealDishService;
import com.qiumu.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealDishService setmealDishService;

    @GetMapping("/page")
    public R<Page<SetmealDto>> list(Integer page, Integer pageSize, String name) {

        Page<Setmeal> pages = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), Setmeal::getName, name);
        setmealService.page(pages, queryWrapper);

        Page<SetmealDto> dtoPage = new Page<>();

        BeanUtils.copyProperties(pages, dtoPage, "records");

        List<Setmeal> records = pages.getRecords();

        List<SetmealDto> list = records.stream().map((temp) -> {
            Category category = categoryService.getById(temp.getCategoryId());
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(temp, setmealDto);
            setmealDto.setCategoryName(category.getName());
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);

        return R.success(dtoPage);
    }

    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable String id) {

        Setmeal setmeal = setmealService.getById(id);

        SetmealDto setmealDto = new SetmealDto();

        BeanUtils.copyProperties(setmeal, setmealDto);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmeal.getId());

        List<SetmealDish> list = setmealDishService.list(queryWrapper);

        setmealDto.setSetmealDishes(list);

        return R.success(setmealDto);
    }

    @GetMapping("/dish/{id}")
    public R<List<DishDto>> getDish(@PathVariable String id) {

        Setmeal setmeal = setmealService.getById(id);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmeal.getId());

        List<SetmealDish> list = setmealDishService.list(queryWrapper);

        List<DishDto> dishes = list.stream().map(temp -> {

            Dish dish = dishService.getById(temp.getDishId());

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish,dishDto);

            Integer copies = temp.getCopies();
            BigDecimal price = dishDto.getPrice();
            BigDecimal multiply = BigDecimal.valueOf(copies).multiply(price);

            dishDto.setCopies(copies);
            dishDto.setPrice(multiply);

            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishes);
    }

    @GetMapping("/list")
    public R<List<Setmeal>> listR(Setmeal setmeal) {

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getCategoryId, setmeal.getCategoryId()).eq(Setmeal::getStatus, 1);
        List<Setmeal> list = setmealService.list(queryWrapper);

//        List<SetmealDto> setmealDtos = list.stream().map(temp -> {
//
//            SetmealDto setmealDto = new SetmealDto();
//
//            BeanUtils.copyProperties(temp,setmealDto);
//
//            LambdaQueryWrapper<SetmealDish> queryWrapper1 = new LambdaQueryWrapper<>();
//            queryWrapper1.eq(SetmealDish::getSetmealId,temp.getId());
//            List<SetmealDish> setmealDishes = setmealDishService.list(queryWrapper1);
//            setmealDto.setSetmealDishes(setmealDishes);
//
//            return setmealDto;
//
//        }).collect(Collectors.toList());


        return R.success(list);
    }

    @PostMapping
    public R<String> add(@RequestBody SetmealDto setmealDto) {

        setmealService.saveWithFlavor(setmealDto);

        return R.success("添加成功");
    }

    @PostMapping("/status/{status}")
    public R<String> up(@PathVariable String status, @RequestParam String ids) {
        LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
        String[] idss = ids.split(",");
        for (String id :
                idss) {
            updateWrapper.clear();
            updateWrapper.eq(Setmeal::getId, id);
            updateWrapper.set(Setmeal::getStatus, status);
            setmealService.update(updateWrapper);
        }
        return R.success("ok");
    }

    @PutMapping
    public R<String> put(@RequestBody SetmealDto setmealDto) {
        setmealService.putWithFlavor(setmealDto);

        return R.success("修改成功");
    }

    @DeleteMapping
    public R<String> up(@RequestParam String ids) {

        setmealService.delWithFlavor(ids);

        return R.success("ok");
    }

}
