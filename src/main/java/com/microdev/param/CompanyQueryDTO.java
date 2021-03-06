package com.microdev.param;

import lombok.Data;

/**
 * @author 数据查询对象
 */
@Data
public class CompanyQueryDTO {
    /**
     * 公司ID
     */
    private String id;

    /**
     * 公司名称
     */
    private String name;
    /**
     * 公司类型，1：用人单位，2：人力公司
     */
    private Integer companyType;
    /**
     * 状态
     */
    private Integer status = 1;
    /**
     * 负责人
     */
    private String leader;
    /**
     * 负责人电话
     */
    private String leaderMobile;
    /**
     * 逻辑删除  默认：false，true为删除
     */
    private Boolean deleted=false;
    /**
     * 查看列表者ID
     */
    private String observerId = "0a58ac0e33314984aba534021655b7af";
    /**
     * 查看列表者类型
     * 0:小时工(用户ID) 2:人力公司 1:用人单位
     */
    private Integer observertype = 1;

    private String serviceType;

    private String pollCode;
    //0 工厂 1门店
    private Integer type = 1;
}
