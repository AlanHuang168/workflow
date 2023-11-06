package org.apache.alan.wrokflow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class ConditionVo {

    @ApiModelProperty("条件类型：1-发起人 2-其他")
    private Integer type;

    @ApiModelProperty("操作类型 <, >, ≤, =, ≥")
    private String optType;

    @ApiModelProperty("左侧自定义内容")
    private String zdy1;

    @ApiModelProperty("操作人")
    private List<NodeUserVo> nodeUserList;
}
