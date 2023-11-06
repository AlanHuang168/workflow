package org.apache.alan.wrokflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @Author: Alan
 * @date: 2023-02-06 16:39
 */
@Data
@ApiModel
public class FlowInstanceDetailDto {

    @ApiModelProperty(value = "业务审批记录查询code",required = true)
    @NotNull(message = "业务审批记录查询code不能为空")
    private String instanceCode;


}
