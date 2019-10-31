package com.microdev.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.microdev.type.UserType;
import lombok.Data;

import java.util.Date;

@Data
@TableName("privilege")
public class Privilege extends BaseEntity{

    private Double price;

    private String userType;

    private Integer type;
    //天数
    private Integer days;
    //会员描述
    private String content;

}
