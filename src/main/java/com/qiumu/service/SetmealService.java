package com.qiumu.service;

import com.qiumu.Dto.SetmealDto;
import com.qiumu.pojo.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86134
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2022-08-18 21:10:10
*/
public interface SetmealService extends IService<Setmeal> {

    void saveWithFlavor(SetmealDto setmealDto);

    void putWithFlavor(SetmealDto setmealDto);

    void delWithFlavor(String ids);
}
