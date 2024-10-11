package com.program.projectquotation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.program.projectquotation.pojo.OrderDetail;
import com.program.projectquotation.service.OrderDetailService;
import com.program.projectquotation.mapper.OrderDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【order_detail】的数据库操作Service实现
* @createDate 2024-10-10 15:49:32
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public boolean createOrderDetail(List<OrderDetail> orderDetails) {
        try {
            boolean saved = saveBatch(orderDetails);
            return saved;
        }
        catch (Exception e) {
            log.error("create order details error", e);
            return false;
        }
    }

    @Override
    public List<OrderDetail> getOrderDetail(String orderId) {
        try {
            LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OrderDetail::getOrderId, orderId);
            List<OrderDetail> orderDetails = orderDetailMapper.selectList(wrapper);
            return orderDetails;
        } catch (Exception e) {
            log.error("get order details error", e);
            return null;
        }
    }
}




