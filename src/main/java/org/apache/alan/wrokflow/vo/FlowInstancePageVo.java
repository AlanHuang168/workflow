package org.apache.alan.wrokflow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-06 10:13
 */
@Data
@ApiModel
public class FlowInstancePageVo {

    @ApiModelProperty("审批节点记录id")
    private Integer id;

    @ApiModelProperty("审批名称")
    private String title;

    @ApiModelProperty("审批内容")
    private String content;

    @ApiModelProperty("申请人用户id")
    private Integer applyUserId;

    @ApiModelProperty("申请人用户名")
    private String applyUserName;

    @ApiModelProperty("处理人用户id")
    private Integer userId;

    @ApiModelProperty("处理人用户名")
    private String userName;

    @ApiModelProperty("模块id")
    private Integer moduleId;

    @ApiModelProperty("模块名称")
    private String moduleName;

    @ApiModelProperty("所属业务")
    private String businessName;

    @ApiModelProperty("申请状态：0创建,1进行中,2已完成,3已终止")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("批阅状态：0待处理，1同意，2不同意等")
    private Integer actionStatus;

    @ApiModelProperty("审批节点查询code")
    private String code;

    @ApiModelProperty("业务审批记录查询code")
    private String instanceCode;

}
