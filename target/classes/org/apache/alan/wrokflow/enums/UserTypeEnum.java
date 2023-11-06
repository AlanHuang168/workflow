package org.apache.alan.wrokflow.enums;

import lombok.Getter;

/**
 * @Description:
 * @Author: Alan
 * @date: 2021-07-12 21:19
 */
@Getter
public enum UserTypeEnum {

    COMPANY(1,"企业"),
    SALE(2,"业务员"),
    AGENT(3,"经销商"),
    SYS_MANAGER(4,"后台运营人员"),
    API(5,"api对接");

    private Integer code;
    private String name;

    UserTypeEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public static UserTypeEnum valueOf(Integer code) {
        for (UserTypeEnum c : UserTypeEnum.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        return null;
    }

}
