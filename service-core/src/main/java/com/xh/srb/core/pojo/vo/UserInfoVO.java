package com.xh.srb.core.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoVO {
    @ApiModelProperty(value = "1：出借人 2：借款人")
    private Integer userType;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "JWT访问令牌")
    private String token;

    @ApiModelProperty(value = "头像")
    private String headImg;
}
