package com.program.projectquotation.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户表（只能更新，不能新增）
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户头像
     */
    private String avatar;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}