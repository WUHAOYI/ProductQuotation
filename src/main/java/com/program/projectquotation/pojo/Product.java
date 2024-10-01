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
 * 商品表
 * @TableName product
 */
@TableName(value ="product")
@Data
public class Product implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品目录id
     */
    private Integer categoryId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品最低价
     */
    private BigDecimal productLowPrice;

    /**
     * 商品最高价
     */
    private BigDecimal productHighPrice;

    /**
     * 商品示意图
     */
    private String productAvatar;

    /**
     * 商品介绍
     */
    private String productIntro;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}