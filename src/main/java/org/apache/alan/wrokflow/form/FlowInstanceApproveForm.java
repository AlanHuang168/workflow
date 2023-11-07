package org.apache.alan.wrokflow.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-14 17:02
 */
@Data
@ApiModel
public class FlowInstanceApproveForm {

    @ApiModelProperty(value = "操作类型 1同意 2不同意",required = true)
    @NotNull(message = "操作类型不能为空")
    @Range(max = 2,min = 1,message = "操作类型 1同意 2不同意")
    private Integer opinion;

    @ApiModelProperty(value = "审批留言")
    private String note;

    @ApiModelProperty(value = "审批节点查询code")
    private String code;

    @ApiModelProperty(value = "审批节点查询code集合")
    private List<String> codes;

}
