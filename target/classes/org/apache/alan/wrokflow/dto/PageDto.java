package org.apache.alan.wrokflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class PageDto {

    @Min(value = 1L, message = "当前页码不合法")
    @ApiModelProperty(value = "当前页码",required = true)
    private Long currentPage=1L;

    @Min(value = 1L, message = "每页展示数量不合法")
    @Max(value = 100L, message = "每页最多展示100条")
    @ApiModelProperty(value = "每页展示的记录数",required = true)
    private Long pageSize = 10L;

}
