package org.apache.alan.wrokflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FlowEngineDto {

    @ApiModelProperty(value = "业务流水ID")
    private Integer bid;

    @ApiModelProperty(value = "业务流水CODE")
    private String bCode;

    @ApiModelProperty(value = "业务模块CODE")
    private String moduleCode;

    @ApiModelProperty(value = "条件分支：金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "审批内容")
    private String content;

    private Integer userId;

    private String userName;

    private Integer companyId;

}
