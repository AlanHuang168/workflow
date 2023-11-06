package org.apache.alan.wrokflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author shenggongjie
 * @date 2021/7/9 18:42
 */
@Data
@ApiModel
public class QueryDto {

    @ApiModelProperty("当前页")
    @NotNull(message = "当前页不能为空")
    private Integer page;

    @ApiModelProperty("每页数量")
    @NotNull(message = "每页数量不能为空")
    private Integer size;

}
