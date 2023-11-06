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
public class FlowDefinitionVo {

    @ApiModelProperty("审批id")
    private Integer id;

    @ApiModelProperty("审批名称")
    private String name;

    @ApiModelProperty("审批主管最大层级")
    private Integer directorMaxLevel;

    @ApiModelProperty("审批code")
    private String definitionCode;

    @ApiModelProperty("流程定义人id")
    private String definitionUserId;

    @ApiModelProperty("流程定义人姓名")
    private String definitionUserName;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("修改时间")
    private Date updatedTime;

    @ApiModelProperty(value = "开启状态 0未  1已")
    private Boolean status;

    private String code;

}
