package org.apache.alan.wrokflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlowUserDto {
    @ApiModelProperty("角色id")
    private Integer roleId;

    @ApiModelProperty("部门id")
    private Integer departmentId;

    private Integer companyId;
}
