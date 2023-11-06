package org.apache.alan.wrokflow.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FlowUserForm {
    @ApiModelProperty("角色id")
    private Integer roleId;

    @ApiModelProperty("部门id")
    private Integer departmentId;

}
