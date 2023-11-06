package org.apache.alan.wrokflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-07 18:39
 */
@Data
@ApiModel
public class FlowDefinitionCreateDto {

    @ApiModelProperty("模板id")
    private Integer id;

    @ApiModelProperty("模板查询code")
    private String code;

    @ApiModelProperty("审批模板编号")
    @NotBlank(message = "审批模板编号不能为空")
    private String definitionCode;

    @ApiModelProperty("模板名称")
    private String name;

    @ApiModelProperty("模块Code")
    @NotBlank(message = "模块Code不能为空")
    private String moduleCode;

    @ApiModelProperty("审批节点")
    private FlowDefinitionConfigDto nodeConfig;

    @ApiModelProperty("是否生效 true-保存并生效  false-保存")
    @NotNull(message = "生效类型不能为空")
    private Boolean status;
}
