package org.apache.alan.wrokflow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FlowUserDescVo {
    @ApiModelProperty("用户id")
    private Integer userId;
    @ApiModelProperty("用户名称")
    private String userName;
}
