package org.apache.alan.wrokflow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-09 18:32
 */
@Data
@ApiModel
public class FlowCreateVo {
    @ApiModelProperty("流程定义id")
    private Integer flowDefinitionId;
}
