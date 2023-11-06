package org.apache.alan.wrokflow.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: Alan
 * @date: 2023-04-25 17:06
 */
@Data
@ApiModel
public class FlowModuleListVo {

    private Integer id;
    @ApiModelProperty("企业id")
    private Integer companyId;
    @ApiModelProperty("模块id")
    private Integer moduleId;
    @ApiModelProperty("模块名称")
    private String moduleName;
    @ApiModelProperty(value = "查询code")
    private String code;
    @ApiModelProperty(value = "是否开启")
    private Boolean open;

}
