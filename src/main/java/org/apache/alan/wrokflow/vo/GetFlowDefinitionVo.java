package org.apache.alan.wrokflow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class GetFlowDefinitionVo {

    @ApiModelProperty("审批模板名称")
    private String defName;

    @ApiModelProperty("审批模板id")
    private Integer definitionId;

    @ApiModelProperty(value = "是否开启 false未开启  true已开启")
    private Boolean open;

    @ApiModelProperty(value = "是否生效（false未开启 true已开启）")
    private Boolean status;

    @ApiModelProperty("查询code")
    private String code;

}
