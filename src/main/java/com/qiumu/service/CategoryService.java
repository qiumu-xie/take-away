package com.qiumu.service;

import com.qiumu.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 86134
 * @description 针对表【category(菜品及套餐分类)】的数据库操作Service
 * @createDate 2022-08-18 19:30:39
 */
public interface CategoryService extends IService<Category> {

    void delete(Long id);

}
