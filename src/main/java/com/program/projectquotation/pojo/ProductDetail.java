package com.program.projectquotation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品详情表（存储商品详情界面展示的多张图片）
 * @TableName product_detail
 */
@TableName(value ="product_detail")
@Data
public class ProductDetail implements Serializable {
    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品详情图片链接
     */
    private String productDetail;

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