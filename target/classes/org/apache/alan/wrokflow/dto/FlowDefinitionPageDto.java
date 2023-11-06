package org.apache.alan.wrokflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-06 10:20
 */
@Data
@ApiModel
public class FlowDefinitionPageDto extends PageDto {

    @ApiModelProperty("审批名称")
    private String name;

    @ApiModelProperty(hidden = true)
    private Integer userId;

    @ApiModelProperty(hidden = true)
    private Integer companyId;
}
