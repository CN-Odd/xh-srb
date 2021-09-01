package com.xh.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "借款信息审核批准VO")
@Data
public class BorrowerApprovalVO {
    @ApiModelProperty(value = "借款信息id")
    private Long borrowerId;
    @ApiModelProperty(value = "审批状态 2:通过 -1:不通过")
    private Integer status;
    @ApiModelProperty(value = "信息积分")
    private Integer infoIntegral;
    @ApiModelProperty(value = "证件信息是否通过")
    private Boolean isIdCardOk;
    @ApiModelProperty(value = "房产信息是否通过")
    private Boolean isHouseOk;
    @ApiModelProperty(value = "车辆信息是否通过")
    private Boolean isCarOk;
}
