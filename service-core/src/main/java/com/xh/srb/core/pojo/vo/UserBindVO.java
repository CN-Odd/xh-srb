package com.xh.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@ApiModel("用户绑定值对象")
@Data
public class UserBindVO {
    @ApiModelProperty("证件号")
    private String idCard;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("银行卡类型")
    private String bankType;
    @ApiModelProperty("银行卡号")
    private String bankNo;
    @ApiModelProperty("手机号")
    private String mobile;

}
