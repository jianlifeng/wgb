package com.microdev.param;

import com.microdev.type.UserType;
import lombok.Data;

import java.util.Date;

@Data
public class CreateServiceParam {
    private String userId;

    private Double price;

    //支付类型  1微信 2支付宝
    private Integer payType;
    //会员类型
    private String privilegeId;
}
