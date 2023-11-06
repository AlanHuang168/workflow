package org.apache.alan.wrokflow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class FlowModuleGetCompanyVo {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "随机COE（查询详情时使用）")
    private String code;

    @ApiModelProperty(value = "父模块ID 一级模块为0")
    private Integer parentId;

    @ApiModelProperty(value = "父模块code")
    private String parentCode;

    @ApiModelProperty(value = "业务菜单名称")
    private String name;

    @ApiModelProperty(value = "业务菜单code")
    private String moduleCode;

    @ApiModelProperty(value = "子模块")
    private List<FlowModuleGetCompanyVo> children;

    @ApiModelProperty(value = "审批模板列表")
    private List<FlowModuleGetCompanyVo> flowDefinitionList;
}
