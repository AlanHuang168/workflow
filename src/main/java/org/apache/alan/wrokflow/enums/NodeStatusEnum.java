package org.apache.alan.wrokflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NodeStatusEnum {

    WAIT(0, "待处理"),
    AGREE(1, "同意"),
    DISAGREE(2, "不同意"),
    UNTREATED(3, "未处理")
    ;

    int status;

    String name;

    public static boolean canAgree(int status) {
        return status == WAIT.status;
    }

}
