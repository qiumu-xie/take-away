package com.qiumu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiumu.common.BaseContext;
import com.qiumu.exception.CostException;
import com.qiumu.pojo.*;
import com.qiumu.service.*;
import com.qiumu.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 86134
 * @description 针对表【orders(订单表)】的数据库操作Service实现
 * @createDate 2022-08-22 10:53:57
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
        implements OrdersService {

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void submit(Orders orders) {
        //获取用户信息
        Long userId = BaseContext.getEmpId();
        User user = userService.getById(userId);
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        if (addressBook == null) {
            throw new CostException("收货地址不存在");
        }
        //实收金额
        AtomicInteger atomic = new AtomicInteger(0);
        //OrderId
        long orderId = IdWorker.getId();
        //获取购物车信息
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(queryWrapper);
        //订单明细信息
        List<OrderDetail> details = shoppingCarts.stream().map(t -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setAmount(t.getAmount());
            orderDetail.setDishFlavor(t.getDishFlavor());
            orderDetail.setDishId(t.getDishId());
            orderDetail.setImage(t.getImage());
            orderDetail.setName(t.getName());
            orderDetail.setNumber(t.getNumber());
            orderDetail.setSetmealId(t.getSetmealId());
            atomic.addAndGet(t.getAmount().multiply(new BigDecimal(t.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());

        //添加订单
        orders.setAmount(new BigDecimal(atomic.get()));
        orders.setNumber(String.valueOf(orderId));
        orders.setOrderTime(LocalDateTime.now());
        orders.setConsignee(addressBook.getConsignee());
        orders.setCheckoutTime((LocalDateTime.now()));
        orders.setId(orderId);
        orders.setUserName(user.getName());
        orders.setUserId(userId);
        orders.setStatus(2);
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        this.save(orders);
        //添加订单明细
        orderDetailService.saveBatch(details);
        //清空购物车
        shoppingCartService.remove(queryWrapper);
    }
}




