package com.microdev.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.microdev.type.UserType;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_privilege")
public class UserPrivilege extends BaseEntity{
    private String userId;

    private Double price;
    //会员开始时间
    private Date beginTime;
    //会员结束时间
    private Date endTime;

    private UserType userType;

    //支付类型  1微信 2支付宝
    private Integer payType;
    //支付类型  0未支付 1已支付 2 申请退款 3 已退款
    private Integer payStatus;
    //会员描述
    private String privilegeContent;
    //会员类型
    private Integer privilegeType;
}
