package org.apache.alan.wrokflow.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class FlowBusinessDetailVo {
    @ApiModelProperty(value = "发起人")
    private String userName;

    @ApiModelProperty(value = "业务类型名称")
    private String businessTypeName;

    @ApiModelProperty(value = "发起时间")
    private Date createTime;

    @ApiModelProperty(value = "内容")
    private String content;


}
