package com.qiumu.mapper;

import com.qiumu.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86134
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2022-08-18 19:30:39
* @Entity com.qiumu.pojo.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




