package org.apache.alan.wrokflow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class FlowUserVo {
    @ApiModelProperty("业务id")
    private Integer businessId;

    @ApiModelProperty("业务名称")
    private String businessName;

    @ApiModelProperty("企业id")
    private Integer companyId;

    @ApiModelProperty("符合条件的用户信息")
    private List<FlowUserDescVo> userDescVoList;
}
