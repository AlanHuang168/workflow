package org.apache.alan.wrokflow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: Alan
 * @date: 2023-04-25 17:06
 */
@Data
@ApiModel
public class FlowModuleVo {

    @ApiModelProperty("模块id")
    private Integer id;
    @ApiModelProperty("模块名称")
    private String name;
    @ApiModelProperty("模块父id")
    private Integer parentId;
    @ApiModelProperty("模块编号")
    private String moduleCode;
    @ApiModelProperty("备注描述")
    private String remark;
    @ApiModelProperty("设置审批流状态 0未设置 1已设置 2已发布")
    private Integer flowSet;
    @ApiModelProperty("是否开启")
    private Boolean open=false;
    @ApiModelProperty("审批定义id")
    private Integer definitionId;
    @ApiModelProperty("审批定义名称")
    private String defName;

}
