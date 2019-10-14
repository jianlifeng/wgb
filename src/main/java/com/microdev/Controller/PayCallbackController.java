package com.microdev.Controller;

import com.alibaba.fastjson.JSONObject;
import com.microdev.common.ApiResult;
import com.microdev.common.PayCallbackForm;
import com.microdev.common.utils.PaySignUtil;
import com.microdev.mapper.UserMapper;
import com.microdev.mapper.UserPrivilegeMapper;
import com.microdev.model.User;
import com.microdev.model.UserPrivilege;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/wgb/pay", produces = "application/json")
public class PayCallbackController {


    private static final Logger log = LoggerFactory.getLogger(PayCallbackController.class);

    @Autowired
    private UserPrivilegeMapper userPrivilegeMapper;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/paySuccess", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult<String> paySuccess(@RequestParam("sign") String sign, @RequestParam("data") String data) {
        if (StringUtils.isBlank(data) || StringUtils.isBlank(sign)) {
            return ApiResult.createError("参数错误！");
        }
        if (!PaySignUtil.verifyNotifySign(data, sign)) {
            return ApiResult.createError("签名错误！");
        } else {
            // 通知成功
            log.debug("通知返回参数：" + data);
            PayCallbackForm form = JSONObject.parseObject(data, PayCallbackForm.class);
            try {
                //支付成功 生成服务订单
                UserPrivilege userPrivilege = userPrivilegeMapper.selectById(form.getOrderId());
                if(userPrivilege != null){
                    if(userPrivilege.getPayStatus() == 1) {
                        return ApiResult.createSuccess();
                    }
                    User user = userMapper.selectById(userPrivilege.getUserId());
                    user.setPrivilege(1);
                    user.setPrivilegeEndTime(userPrivilege.getEndTime());
                    userPrivilege.setPayStatus(1);
                    userPrivilegeMapper.updateById(userPrivilege);
                    userMapper.updateById(user);
                    return ApiResult.createSuccess();
                }
                log.error("orderId 不存在");
                return ApiResult.createError("orderId 不存在");
            } catch (RuntimeException e) {
                log.error("paySuccess error, orderNo : {},orderId :{} ", form.getOrderNo(), form.getOrderId(), e);
                return ApiResult.createError(e.getMessage());
            }
        }
    }




}
