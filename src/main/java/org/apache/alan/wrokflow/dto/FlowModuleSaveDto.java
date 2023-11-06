package org.apache.alan.wrokflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlowModuleSaveDto {

    @ApiModelProperty(value = "父级业务菜单code")
    private String parentCode;

    @ApiModelProperty(value = "业务菜单名称")
    private String name;

    @ApiModelProperty(value = "业务菜单code")
    private String moduleCode;

    @ApiModelProperty(value = "排序")
    private Integer sort = 0;

    @ApiModelProperty(value = "备注")
    private String remark;
}
