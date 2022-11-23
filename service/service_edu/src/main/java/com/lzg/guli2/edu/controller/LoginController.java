package com.lzg.guli2.edu.controller;

import com.lzg.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduService")
@CrossOrigin   //跨域注解
@Api(description = "登录接口")
public class LoginController {

    @PostMapping("login")
    public R teacherLogin() {
        return R.ok().data("token","admin");
    }


    @GetMapping("info")
    public R teacherInfo() {
        return R.ok().data("roles","[admin]").data("name","王小美").data("avatar","https://api.ixiaowai.cn/mcapi/mcapi.php");
    }

}
