package org.apache.alan.wrokflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-07 18:39
 */
@Data
@ApiModel
public class FlowCreateDto {

    @ApiModelProperty("审批流名称")
    private String name;

    @ApiModelProperty("模块id")
    private Integer moduleId;

    @ApiModelProperty("模块父id")
    private Integer moduleParentId;
}
