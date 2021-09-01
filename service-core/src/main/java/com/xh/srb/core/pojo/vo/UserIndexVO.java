package com.xh.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "用户基础信息VO")
public class UserIndexVO {
    private Long id;
    private String name;
    private String headImg;
    private Integer userType;
    private Integer bindStatus;
    @ApiModelProperty(value = "帐户可用余额")
    private BigDecimal amount;
    @ApiModelProperty(value = "冻结金额")
    private BigDecimal freezeAmount;
    private LocalDateTime lastLoginTime;
}
