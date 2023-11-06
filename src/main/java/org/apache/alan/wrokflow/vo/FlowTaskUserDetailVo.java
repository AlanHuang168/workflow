package org.apache.alan.wrokflow.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class FlowTaskUserDetailVo {
    @ApiModelProperty(value = "处理人")
    private String userName;

    @ApiModelProperty(value = "处理人")
    private Integer userId;

    @ApiModelProperty(value = "处理状态:0待处理，1同意，2不同意，3无需处理")
    private Integer actionStatus;

    @ApiModelProperty(value = "处理时间")
    private Date actionTime;

    @ApiModelProperty(value = "处理批注")
    private String note;
}
