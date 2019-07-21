package com.microdev.Controller;

import com.microdev.common.ResultDO;
import com.microdev.model.Company;
import com.microdev.model.Worker;
import com.microdev.param.UserDTO;
import com.microdev.service.CompanyService;
import com.microdev.service.UserService;
import com.microdev.type.UserSex;
import com.microdev.type.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @RequestMapping("/admin/register")
    public String hello(HttpServletRequest request, @RequestParam(required = false,value = "message") String message, @RequestParam(required = false, value = "username") String username,
                        @RequestParam(required = false,value = "tgCode",defaultValue ="adminregister") String tgCode, @RequestParam(required = false, value ="age") Integer age, @RequestParam(required =
            false, value = "nickname") String nickname, @RequestParam(required = false, value ="sex") String sex, @RequestParam(required = false, value = "mobile") String mobile
            , @RequestParam(required = false, value ="email") String email,@RequestParam(required = false,value = "areaCode") Integer areaCode, @RequestParam(required = false, value =
            "userType") String userType, @RequestParam(required = false,value = "companyName") String companyName, @RequestParam(required = false, value ="address") String address,@RequestParam(required = false,
            value = "addressCode") Integer addressCode, @RequestParam(required = false, value ="stature") Integer stature, @RequestParam(required = false, value ="weight") Integer weight, @RequestParam(required = false, value ="education") Integer education
            , @RequestParam(required = false, value ="area") String area
    ) {
        if (mobile != null) {
            ResultDO rs = null;
            UserDTO user = new UserDTO();
            user.setUsername(username);
            user.setTgCode(tgCode);
            user.setAge(age);
            user.setNickname(nickname);
            user.setSex(UserSex.valueOf(sex));
            user.setMobile(mobile);
            user.setEmail(email);
            user.setPassword("123456");
            user.setUserType(UserType.valueOf(userType));
            try {
                rs = userService.adminRegister(user);
            } catch (Exception e) {
                message = e.getMessage();
                request.setAttribute("message", message);
                return "register";
            }
            if (user.getUserType().equals(UserType.worker)) {
                Worker worker = (Worker) rs.getData();
                UserDTO userDTO = new UserDTO();
                userDTO.setStature(stature);
                userDTO.setWeight(weight);
                userDTO.setEducation(education);
                userDTO.setUserType(UserType.worker);
                userDTO.setTgCode(tgCode);
                userDTO.setId(worker.getPid());
            } else {
                Company company = (Company) rs.getData();
                company.setName(companyName);
                company.setAddress(address);
                company.setArea(area);
                company.setAddressCode(areaCode);
                companyService.updateCompany(company);
            }
            request.setAttribute("message", "注册成功");
        }
        return "register";
    }
}