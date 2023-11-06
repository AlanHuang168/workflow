package org.apache.alan.wrokflow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FlowDefinitionDetailVo {
    @ApiModelProperty("模板id")
    private Integer id;

    @ApiModelProperty("模板查询code")
    private String code;

    @ApiModelProperty("审批模板编号")
    private String definitionCode;

    @ApiModelProperty("模板名称")
    private String name;

    @ApiModelProperty("模块Code")
    private String moduleCode;

    @ApiModelProperty("审批节点")
    private FlowDefinitionConfigVo nodeConfig;

    @ApiModelProperty("是否生效 true-保存并生效  false-保存")
    private Boolean status;
}
