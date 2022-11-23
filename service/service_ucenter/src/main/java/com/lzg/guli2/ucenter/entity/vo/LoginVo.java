package com.lzg.guli2.ucenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ApiModel(value = "前端视图登录对象",description = "方便前端登录的视图对象")
@ToString
public class LoginVo {
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "邮箱地址")
    private String mail;

}
