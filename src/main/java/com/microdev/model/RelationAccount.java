package com.microdev.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.microdev.type.UserType;
import lombok.Data;

/**
 * 账单
 *
 */
@Data
@TableName("relation_account")
public class RelationAccount extends BaseEntity{
    /**
     * 手机唯一标识
     */
    private String mobileKey;
    /**
     * 手机号
     */
    private String mobile;
    /**
     *  用户类型
     */
    private UserType userType;
    /**
     *  用户id
     */
    private String userId;
    /**
     *  用户名称
     */
    @TableField(exist = false)
    private String name;
    /**
     *  头像
     */
    @TableField(exist = false)
    private String logo;
}
