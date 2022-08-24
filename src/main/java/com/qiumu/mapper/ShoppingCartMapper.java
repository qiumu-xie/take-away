package com.qiumu.mapper;

import com.qiumu.pojo.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86134
* @description 针对表【shopping_cart(购物车)】的数据库操作Mapper
* @createDate 2022-08-22 07:50:40
* @Entity com.qiumu.pojo.ShoppingCart
*/
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}




