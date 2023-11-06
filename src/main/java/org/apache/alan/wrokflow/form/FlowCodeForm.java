package org.apache.alan.wrokflow.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FlowCodeForm {

    @ApiModelProperty("唯一code")
    @NotBlank(message = "code不能为空")
    private String code;

}
