package com.qiumu.service;

import com.qiumu.pojo.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86134
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2022-08-22 10:53:57
*/
public interface OrdersService extends IService<Orders> {
    /**
     * 订单支付
     * @param orders
     */
    void submit(Orders orders);
}
