package org.apache.alan.wrokflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InstanceStatusEnum {
    //0创建,1进行中,2已完成,3已终止
    CREATE(0, "0创建"),
    IN_PROGRESS(1, "进行中"),
    FINISHED(2, "已完成"),
    TERMINATED(3, "已终止"),
    ;

    int status;

    String name;

    public static boolean inProgress(int status) {
        return status == IN_PROGRESS.status;
    }

}
