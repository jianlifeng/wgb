package com.microdev.Controller;

import com.microdev.common.ResultDO;
import com.microdev.common.oss.ObjectStoreService;
import com.microdev.common.utils.FileUtil;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ObjectStoreService objectStoreService;

    @RequestMapping("/admin/register")
    public String hello(HttpServletRequest request, @RequestParam(required = false,value = "message") String message, @RequestParam(required = false, value = "username") String username,
                        @RequestParam(required = false,value = "tgCode",defaultValue ="adminregister") String tgCode, @RequestParam(required = false, value ="age") Integer age, @RequestParam(required =
            false, value = "nickname") String nickname, @RequestParam(required = false, value ="sex") String sex, @RequestParam(required = false, value = "mobile") String mobile
            , @RequestParam(required = false, value ="email") String email,@RequestParam(required = false,value = "areaCode") Integer areaCode, @RequestParam(required = false, value =
            "userType") String userType, @RequestParam(required = false,value = "companyName") String companyName, @RequestParam(required = false, value ="address") String address,@RequestParam(required = false,
            value = "addressCode") Integer addressCode, @RequestParam(required = false, value ="stature") Integer stature, @RequestParam(required = false, value ="weight") Integer weight, @RequestParam(required = false, value ="education") Integer education
            , @RequestParam(required = false, value ="area") String area, @RequestParam(required = false, value ="avatar") MultipartFile avatar, @RequestParam(required = false, value ="logo") MultipartFile logo
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
            if(avatar != null){
                user.setAvatar(uploadFile(avatar));
            }
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
                userService.modifyBaseInfo(userDTO);
            } else {
                Company company = (Company) rs.getData();
                company.setName(companyName);
                company.setAddress(address);
                company.setArea(area);
                company.setAddressCode(addressCode);
                if(logo != null){
                    company.setLogo(uploadFile(logo));
                }
                companyService.updateCompany(company);
            }
            request.setAttribute("message", "注册成功");
        }
        return "register";
    }

    public String uploadFile(MultipartFile file){
        String fileURI = "";
        String filePath = null;
        try {
            filePath = "avater".toLowerCase() + "/" + UUID.randomUUID() + file.getOriginalFilename ();
            //文件上传成功后返回的下载路径，比如: http://oss.xxx.com/avatar/3593964c85fd76f12971c82a411ef2a481c9c711.jpg
            fileURI = objectStoreService.uploadObject(filePath, file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileURI;
    }
}