package com.program.projectquotation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName order
 */
@TableName(value ="`order`")
@Data
public class Order implements Serializable {
    /**
     * 订单id
     */
    @TableId
    private String id;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 订单总价
     */
    private BigDecimal orderPrice;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}