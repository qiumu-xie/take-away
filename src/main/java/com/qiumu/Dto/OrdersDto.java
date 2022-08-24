package com.qiumu.Dto;

import com.qiumu.pojo.OrderDetail;
import com.qiumu.pojo.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private List<OrderDetail> orderDetails;
	
}
