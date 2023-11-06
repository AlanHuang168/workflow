package org.apache.alan.wrokflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.alan.wrokflow.service.FlowBaseService;
import org.apache.alan.wrokflow.service.ITWithdrawInfoService;

@Getter
@AllArgsConstructor
public enum FlowModuleEnum {

    WITHDRAW("withdraw.deposit", "提现", ITWithdrawInfoService.class);


    public static FlowModuleEnum getById(String moduleCode) {
        for (FlowModuleEnum moduleEnum : values()) {
            if (moduleEnum.getModuleCode().equals(moduleCode)) {
                return moduleEnum;
            }
        }
        return null;
    }

    /**
     * 模块id，唯一
     */
    private String moduleCode;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 模块对应的核心服务接口，用于回调
     */
    private Class<? extends FlowBaseService> serviceClass;
}
