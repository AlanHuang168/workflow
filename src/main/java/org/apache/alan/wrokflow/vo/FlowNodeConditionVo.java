package org.apache.alan.wrokflow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class FlowNodeConditionVo {

    @ApiModelProperty("节点id")
    private Integer id;

    @ApiModelProperty("节点code")
    private String code;

    @ApiModelProperty("发起人节点名称")
    private String nodeName;

    @ApiModelProperty("审批节点类型：0-发起人 1-审批 2-抄送 3-条件 4-路由")
    private Integer type;

    @ApiModelProperty("审批人设置 1指定人 2指定部门 3角色")
    private Integer settype;

    @ApiModelProperty("条件优先级")
    private Integer priorityLevel;

    @ApiModelProperty("当前条件")
    private List<ConditionVo> conditionList;

    @ApiModelProperty("审核子节点")
    private FlowDefinitionConfigVo childNode;

    @ApiModelProperty("条件节点")
    private List<FlowNodeConditionVo> conditionNodeList;
}
