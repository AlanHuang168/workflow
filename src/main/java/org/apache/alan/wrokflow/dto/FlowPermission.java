package org.apache.alan.wrokflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-02 10:32
 */
@Data
@ApiModel
public class FlowPermission {

    @ApiModelProperty("发起人 2其他")
    private Integer type;

    @ApiModelProperty("发起人id")
    private String targetId;

    @ApiModelProperty("发起人")
    private String name;


}
