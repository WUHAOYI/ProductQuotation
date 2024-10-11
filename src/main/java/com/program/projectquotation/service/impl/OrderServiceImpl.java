package com.program.projectquotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.program.projectquotation.pojo.Order;
import com.program.projectquotation.pojo.Product;
import com.program.projectquotation.service.OrderService;
import com.program.projectquotation.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【order】的数据库操作Service实现
* @createDate 2024-10-10 15:49:32
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int createOrder(Order order) {
        try {
            int insert = orderMapper.insert(order);
            return insert;
        } catch (Exception e) {
            log.error("create order error", e);
            return 0;
        }
    }

    @Override
    public List<Order> getOrders(int page,int size) {
        try {
            Page<Order> thisPage = new Page<>(page, size);
            LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(Order::getCreateTime);
            Page<Order> orderPage = orderMapper.selectPage(thisPage, wrapper);
            List<Order> orders = orderPage.getRecords();
            return orders;
        } catch (Exception e) {
            log.error("get order error", e);
            return null;
        }
    }
}




