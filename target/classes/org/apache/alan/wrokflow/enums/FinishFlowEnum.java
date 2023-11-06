package org.apache.alan.wrokflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinishFlowEnum {

    OFF(0, "未开启"),
    NO(1, "流程不存在"),
    FINISHED(2, "已完成"),
    ;

    int status;

    String name;


}
