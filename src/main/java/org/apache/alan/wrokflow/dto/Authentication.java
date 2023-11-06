package org.apache.alan.wrokflow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Authentication {

    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 客户id
     */
    private Integer customerId;
    /**
     * 企业id
     */
    private Integer companyId;
    /**
     * 业务员id
     */
    private Integer salesmanId;

    private Integer userType;

    private Integer roleId;

    private List<Integer> deptList;

    @ApiModelProperty("所属部门和所有的下属部门id")
    private List<Integer> allDeptList;

}
