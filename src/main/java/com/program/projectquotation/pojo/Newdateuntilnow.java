package com.program.projectquotation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName newDateUntilNow
 */
@TableName(value ="newDateUntilNow")
@Data
public class Newdateuntilnow implements Serializable {
    /**
     * 上新时间范围
     */
    private Integer newdateuntilnow;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}