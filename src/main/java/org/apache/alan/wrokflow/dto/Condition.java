package org.apache.alan.wrokflow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-02 16:15
 */
@Data
@ApiModel
public class Condition {

    @ApiModelProperty("发起人")
    private Integer columnId;

    @ApiModelProperty("发起人 2其他")
    private Integer type;

    @ApiModelProperty("< > ≤ =")
    private String optType;

    @ApiModelProperty("左侧自定义内容")
    private String zdy1;

    @ApiModelProperty("右侧自定义内容")
    private String zdy2;

    @ApiModelProperty("左侧符号 < ≤")
    private String opt1;

    @ApiModelProperty("右侧符号 < ≤")
    private String opt2;

    @ApiModelProperty("条件字段名称")
    private String columnDbname;

    @ApiModelProperty("条件字段类型")
    private String columnType;

    @ApiModelProperty("checkBox多选")
    private String showType;

    @ApiModelProperty("展示名")
    private String showName;

    @ApiModelProperty("多选数组")
    private String fixedDownBoxValue;



}
