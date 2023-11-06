package org.apache.alan.wrokflow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class GetFlowModuleVo {

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

    @ApiModelProperty(value = "排序")
    private Integer sort;

//    @ApiModelProperty(value = "类路径")
//    private String relClass;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "子模块")
    private List<GetFlowModuleVo> children;
}
