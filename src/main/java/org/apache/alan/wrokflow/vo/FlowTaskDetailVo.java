package org.apache.alan.wrokflow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Alan
 * @date: 2023-02-06 17:29
 */
@Data
@ApiModel
public class FlowTaskDetailVo {

    @ApiModelProperty(value = "审批节点发起时间")
    private Date instanceCreateTime;

    @ApiModelProperty(value = "节点类型：0发起人 1审批 2抄送 3条件 4路由")
    private Integer type;

    @ApiModelProperty(value = "节点审批方式 1或签 2会签")
    private Integer examineMode;

    @ApiModelProperty(value = "对应节点处理人")
    private List<FlowTaskUserDetailVo> flowTaskUserDetailVos;

    @ApiModelProperty(value = "处理状态:0待处理，1通过，2拒绝")
    private Integer status;

    @ApiModelProperty(value = "节点id")
    private Integer nodeId;




}
