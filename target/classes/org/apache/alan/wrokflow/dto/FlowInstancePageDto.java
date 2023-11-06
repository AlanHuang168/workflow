package org.apache.alan.wrokflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-06 10:20
 */
@Data
@ApiModel
public class FlowInstancePageDto extends PageDto {

    @ApiModelProperty(value = "模块查询code")
    private String code;

    @ApiModelProperty(value = "申请开始时间")
    private String startTime;

    @ApiModelProperty(value = "申请结束时间")
    private String endTime;

    @ApiModelProperty(value = "状态：0全部，1待处理，2已处理，3我发起的，4抄送我的")
    private Integer status;

    private Integer companyId;
    private Integer userId;
    private Integer moduleId;
}
