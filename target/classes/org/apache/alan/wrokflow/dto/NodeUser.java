package org.apache.alan.wrokflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-02 16:12
 */
@Data
@ApiModel
public class NodeUser {

    private Integer companyId;

    @ApiModelProperty("目标id")
    private Integer targetId;

    @ApiModelProperty("操作类型 1-指定人 2-指定部门")
    private Integer type;

    @ApiModelProperty("目标名称")
    private String name;
}
