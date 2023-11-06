package org.apache.alan.wrokflow.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class FlowModuleOpenForm {

    @ApiModelProperty("模块id")
    @NotNull(message = "模块id不能为空")
    private Integer moduleId;
    @ApiModelProperty("是否开启")
    private boolean open;

}
