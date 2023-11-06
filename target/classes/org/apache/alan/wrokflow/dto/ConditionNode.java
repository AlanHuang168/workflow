package org.apache.alan.wrokflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-02 16:15
 */
@Data
@ApiModel
public class ConditionNode {

    @ApiModelProperty("发起人节点名称")
    private String nodeName;

    @ApiModelProperty("条件优先级")
    private String priorityLevel;

    @ApiModelProperty("条件优先级")
    private Integer type;

    @ApiModelProperty("条件优先级")
    private String settype;

    @ApiModelProperty("条件优先级")
    private String selectMode;

    @ApiModelProperty("条件优先级")
    private String selectRange;

    @ApiModelProperty("条件优先级")
    private String directorLevel;

    @ApiModelProperty("多人审批时采用的审批方式 1依次审批 2会签")
    private Integer examineMode;

    @ApiModelProperty("审批人为空时 1自动审批通过/不允许发起 2转交给审核管理员")
    private Integer noHanderAction;

    @ApiModelProperty("审批终点 第n层主管")
    private Integer examineEndDirectorLevel;

    @ApiModelProperty("允许发起人自选抄送人")
    private Integer ccSelfSelectFlag;

    @ApiModelProperty("当前条件")
    private List<Condition> conditionList;

    @ApiModelProperty("操作人")
    private List<NodeUser> nodeUserList;

    @ApiModelProperty("审核子节点")
    private NodeConfig childNode;

    @ApiModelProperty("条件节点")
    private List<ConditionNode> conditionNodes;

    @ApiModelProperty("当前审批是否通过校验")
    private Boolean error;
}
