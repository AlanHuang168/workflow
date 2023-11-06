package org.apache.alan.wrokflow.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-06 10:29
 */
@Data
@ApiModel
public class FlowDefinitionGetDto {

    private Integer id;

    private Integer definitionUserId;

    private String code;
}
