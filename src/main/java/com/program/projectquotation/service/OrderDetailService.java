package com.program.projectquotation.service;

import com.program.projectquotation.pojo.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【order_detail】的数据库操作Service
* @createDate 2024-10-10 15:49:32
*/
public interface OrderDetailService extends IService<OrderDetail> {
    public boolean createOrderDetail(List<OrderDetail> orderDetails);

    //获取订单详情
    public List<OrderDetail> getOrderDetail(String orderId);
}
