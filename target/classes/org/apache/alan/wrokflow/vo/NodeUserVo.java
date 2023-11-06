package org.apache.alan.wrokflow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class NodeUserVo {

    @ApiModelProperty("目标id")
    private Integer targetId;

    @ApiModelProperty("操作类型 1-指定人 2-指定部门")
    private Integer type;

    @ApiModelProperty("目标名称")
    private String name;
}
