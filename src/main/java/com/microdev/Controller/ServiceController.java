package com.microdev.Controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.microdev.common.ResultDO;
import com.microdev.mapper.PrivilegeMapper;
import com.microdev.mapper.UserPrivilegeMapper;
import com.microdev.model.Privilege;
import com.microdev.model.User;
import com.microdev.model.UserPrivilege;
import com.microdev.param.CreateServiceParam;
import com.microdev.service.UserService;
import com.microdev.type.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户前台相关Api
 */
@RestController
public class ServiceController {

    @Autowired
    private UserService userService;

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Autowired
    private UserPrivilegeMapper userPrivilegeMapper;

	/**
     *  购买会员
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user/createService")
    public ResultDO create(@RequestBody CreateServiceParam param) throws Exception {
        return userService.createService(param);
    }

    /**
     *  会员价格表
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/privilege/List/{userType}")
    public ResultDO privilegeList(@PathVariable String userType) throws Exception {
        List<Privilege> result = privilegeMapper.selectList(new EntityWrapper<Privilege>().eq("user_type",userType));
        return ResultDO.buildSuccess(result);
    }

    /**
     *  会员价格表
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user/privilege/{userId}")
    public ResultDO useprivilegeList(@PathVariable String userId) throws Exception {
        List<UserPrivilege> result = userPrivilegeMapper.selectList(new EntityWrapper<UserPrivilege>().eq("user_id",userId).orderBy("create_time",false));
        return ResultDO.buildSuccess(result);
    }


}
