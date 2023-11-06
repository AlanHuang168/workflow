package org.apache.alan.wrokflow.enums;

import lombok.Getter;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-13 18:41
 */
@Getter
public enum FlowDefinitionNodeTypeEnum {

    AUDIO(1,"审批"),
    COPY(2,"抄送"),
    CONDITION(3,"条件"),
    ROUTE(4,"路由");

    private Integer value;
    private String name;

    FlowDefinitionNodeTypeEnum(Integer value, String name){
        this.value = value;
        this.name = name;
    }

    public static FlowDefinitionNodeTypeEnum valueOf(Integer value) {
        for (FlowDefinitionNodeTypeEnum c : FlowDefinitionNodeTypeEnum.values()) {
            if (c.getValue().equals(value)) {
                return c;
            }
        }
        return null;
    }
}
