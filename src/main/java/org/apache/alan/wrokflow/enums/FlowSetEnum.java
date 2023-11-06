package org.apache.alan.wrokflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FlowSetEnum {

    NO(0, "未设置"),
    SET(1, "已设置"),
    FINISHED(2, "已发布"),
    ;

    int status;

    String name;


}
