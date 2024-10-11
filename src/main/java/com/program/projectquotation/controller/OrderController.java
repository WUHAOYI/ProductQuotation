package com.program.projectquotation.controller;

import com.program.projectquotation.pojo.Order;
import com.program.projectquotation.pojo.OrderDetail;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.result.ResultCodeEnum;
import com.program.projectquotation.service.OrderDetailService;
import com.program.projectquotation.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by WHY on 2024/10/10.
 * Functions: 订单相关接口
 */
@RestController
@RequestMapping("order")
@CrossOrigin
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 创建订单
     *
     * @param data
     * @return
     */
    @Transactional
    @PostMapping()
    public Result createOrder(@RequestBody HashMap<String, Object> data) {

        String orderId = UUID.randomUUID().toString();
        Order order = new Order();
        order.setId(orderId);
        order.setOrderPrice(BigDecimal.valueOf(((Number) data.get("orderPrice")).doubleValue()));
//        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) data.get("products");
        if (Objects.isNull(products) || products.size() == 0) {
            return Result.build(null, 50011, "订单不能为空");
        }
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (HashMap<String, Object> product : products) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setProductName(String.valueOf(product.get("name")));
            orderDetail.setProductPrice(BigDecimal.valueOf(((Number) product.get("price")).doubleValue()));
            orderDetail.setProductNum((Integer) product.get("num"));
            Object norm = product.get("norm");
            if (Objects.nonNull(norm)) {
                orderDetail.setProductNorm(String.valueOf(norm));
            } else {
                orderDetail.setProductNorm(null);
            }
            orderDetail.setProductPriceAll(BigDecimal.valueOf(((Number) product.get("priceAll")).doubleValue()));
            orderDetails.add(orderDetail);
        }
        try {
            if (orderService.createOrder(order) != 0 && orderDetailService.createOrderDetail(orderDetails)) {
                Map<String, String> res = Map.of("orderId", orderId);
                return Result.build(res, ResultCodeEnum.CREATE_ORDER_SUCCESS);
            }
            return Result.build(null, ResultCodeEnum.CREATE_ORDER_ERROR);
        } catch (Exception e) {
            return Result.build(null, ResultCodeEnum.CREATE_ORDER_ERROR);
        }
    }

    /**
     * 获取所有订单
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping()
    public Result getOrder(@RequestParam int page, @RequestParam int size) {
        try {
            List<Order> orders = orderService.getOrders(page, size);
            List<Object> res = new ArrayList<>();
            for (Order order : orders) {
                String orderId = order.getId();
                List<OrderDetail> orderDetails = orderDetailService.getOrderDetail(orderId);
                Map<String, Object> data = new HashMap<>();
                data.put("id", order.getId());
                data.put("orderPrice", order.getOrderPrice());
                data.put("createTime", order.getCreateTime());
                if (Objects.isNull(orderDetails) || orderDetails.isEmpty()) {
                    data.put("products", null);
                    res.add(data);
                    continue;
                }
                List<Map<String, Object>> products = new ArrayList<>();
                for (OrderDetail orderDetail : orderDetails) {
                    Map<String, Object> product = new HashMap<>();
                    product.put("name", orderDetail.getProductName());
                    product.put("price", orderDetail.getProductPrice());
                    product.put("num", orderDetail.getProductNum());
                    product.put("norm", orderDetail.getProductNorm());
                    product.put("priceAll", orderDetail.getProductPriceAll());
                    products.add(product);
                }
                data.put("products", products);
                res.add(data);
            }
            return Result.build(res, ResultCodeEnum.GET_ORDER_SUCCESS);
        } catch (Exception e) {
            return Result.build(null, ResultCodeEnum.GET_ORDER_ERROR);
        }
    }
}
