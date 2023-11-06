package org.apache.alan.wrokflow.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "flow_node_user")
public class FlowNodeUser {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id或角色id或部门id
     */
    @TableField(value = "target_id")
    private Integer targetId;

    /**
     * 企业id
     */
    @TableField(value = "company_id")
    private Integer companyId;

    /**
     * 用户名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 类型1指定人2角色3部门
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 条件节点id
     */
    @TableField(value = "node_id")
    private Integer nodeId;

    /**
     * 用户类型 0审批人1发起人2抄送人
     */
    @TableField(value = "user_type")
    private Integer userType;

    /**
     * 是否删除：0否，1是
     */
    @TableField(value = "deleted")
    private Boolean deleted;

    /**
     *  状态：0待处理，1同意，2不同意等
     */
    @TableField(exist = false)
    private Integer actionStatus;


    public static final String COL_ID = "id";

    public static final String COL_TARGET_ID = "target_id";

    public static final String COL_COMPANY_ID = "company_id";

    public static final String COL_NAME = "name";

    public static final String COL_TYPE = "type";

    public static final String COL_NODE_ID = "node_id";

    public static final String COL_USER_TYPE = "user_type";

    public static final String COL_DELETED = "deleted";
}
