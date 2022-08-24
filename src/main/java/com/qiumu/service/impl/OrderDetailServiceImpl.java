package com.qiumu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiumu.pojo.OrderDetail;
import com.qiumu.service.OrderDetailService;
import com.qiumu.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author 86134
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2022-08-22 10:53:57
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




