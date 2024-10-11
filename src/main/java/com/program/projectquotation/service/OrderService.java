package com.program.projectquotation.service;

import com.program.projectquotation.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【order】的数据库操作Service
* @createDate 2024-10-10 15:49:32
*/
public interface OrderService extends IService<Order> {
    public int createOrder(Order order);

    public List<Order> getOrders(int page,int size);
}
