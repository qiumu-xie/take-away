package com.qiumu.controll;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiumu.Dto.OrdersDto;
import com.qiumu.common.R;
import com.qiumu.pojo.OrderDetail;
import com.qiumu.pojo.Orders;
import com.qiumu.service.OrderDetailService;
import com.qiumu.service.OrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){

        ordersService.submit(orders);

        return R.success("ok");
    }
    @GetMapping("/userPage")
    public R<Page<OrdersDto>> list(Integer page, Integer pageSize) {

        Page<Orders> pages = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Orders::getOrderTime);
        ordersService.page(pages, queryWrapper);

        Page<OrdersDto> dtoPage = new Page<>();

        BeanUtils.copyProperties(pages, dtoPage, "records");

        List<Orders> records = pages.getRecords();

        List<OrdersDto> list = records.stream().map((temp) -> {

            LambdaQueryWrapper<OrderDetail> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(OrderDetail::getOrderId,temp.getId());
            List<OrderDetail> orderDetails = orderDetailService.list(queryWrapper1);
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(temp, ordersDto);
            ordersDto.setOrderDetails(orderDetails);
            return ordersDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);

        return R.success(dtoPage);
    }
}
