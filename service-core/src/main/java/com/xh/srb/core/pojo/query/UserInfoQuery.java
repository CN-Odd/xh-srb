package com.xh.srb.core.pojo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
@ApiModel(value = "用户查询对象")
public class UserInfoQuery {

    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "1：出借人 2：借款人")
    private Integer userType;
    @ApiModelProperty(value = "状态（0：锁定 1：正常）")
    private Integer status;
}
