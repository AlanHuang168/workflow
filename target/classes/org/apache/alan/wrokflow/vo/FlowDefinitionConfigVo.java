package org.apache.alan.wrokflow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class FlowDefinitionConfigVo {

    @ApiModelProperty("发起人节点名称")
    private String nodeName;

    @ApiModelProperty("审批节点类型：0-发起人 1-审批 2-抄送 3-条件 4-路由")
    private Integer type;

    @ApiModelProperty("审批人类型: 1-指定人 2-指定部门 3-角色")
    private Integer settype;

    @ApiModelProperty("审批人方式: 1-或签 2-会签")
    private Integer examineMode;

    @ApiModelProperty("操作人")
    private List<FlowNodeUserVo> nodeUserList;

    @ApiModelProperty("条件节点")
    private List<FlowNodeConditionVo> conditionNodeList;

    @ApiModelProperty("子节点")
    private FlowDefinitionConfigVo childNode;
}
