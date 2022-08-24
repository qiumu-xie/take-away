package com.qiumu.controll;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiumu.Dto.DishDto;
import com.qiumu.common.R;
import com.qiumu.pojo.*;
import com.qiumu.service.DishFlavorService;
import com.qiumu.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService flavorService;

    @GetMapping("/page")
    public R<Page<DishDto>> list(Integer page, Integer pageSize,String name) {

        Page<Dish> pages = new Page<>(page, pageSize);
        Page<DishDto> listPage = dishService.listPage(pages, name);

        return R.success(listPage);
    }
    @GetMapping("/list")
    public R<List<DishDto>> listR(Dish dish){

        LambdaQueryWrapper<Dish> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId,dish.getCategoryId()).eq(Dish::getStatus,1);
        List<Dish> list = dishService.list(queryWrapper);

        List<DishDto> dishDtos = list.stream().map(temp -> {

            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(temp,dishDto);

            LambdaQueryWrapper<DishFlavor> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(DishFlavor::getDishId,temp.getId());
            List<DishFlavor> flavors = flavorService.list(queryWrapper1);
            dishDto.setFlavors(flavors);

            return dishDto;

        }).collect(Collectors.toList());


        return R.success(dishDtos);
    }

    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable String id){

        Dish dish =dishService.getById(id);

        DishDto dishDto = new DishDto();

        BeanUtils.copyProperties(dish,dishDto);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());

        List<DishFlavor> list = flavorService.list(queryWrapper);

        dishDto.setFlavors(list);

        return R.success(dishDto);
    }

    @PostMapping
    public R<String> add(@RequestBody DishDto dishDto){

        dishService.saveWithFlavor(dishDto);

        return R.success("添加成功");
    }

    @PostMapping("/status/{status}")
    public R<String> up(@PathVariable String status,@RequestParam String ids){
        LambdaUpdateWrapper<Dish> updateWrapper = new LambdaUpdateWrapper<>();
        String[] idss = ids.split(",");
        for (String id :
                idss) {
            updateWrapper.clear();
            updateWrapper.eq(Dish::getId, Long.valueOf(id));
            updateWrapper.set(Dish::getStatus,status);
            dishService.update(updateWrapper);
        }
        return R.success("ok");
    }

    @PutMapping
    public R<String> put(@RequestBody DishDto dishDto){

        dishService.putWithFlavor(dishDto);

        return R.success("修改成功");
    }

    @DeleteMapping
    public R<String> up(@RequestParam String ids){

        dishService.delWithFlavor(ids);

        return R.success("ok");
    }

}
