package com.qiumu.mapper;

import com.qiumu.pojo.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86134
* @description 针对表【order_detail(订单明细表)】的数据库操作Mapper
* @createDate 2022-08-22 10:53:57
* @Entity com.qiumu.pojo.OrderDetail
*/
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}




