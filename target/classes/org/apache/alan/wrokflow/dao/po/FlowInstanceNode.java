package org.apache.alan.wrokflow.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "flow_instance_node")
public class FlowInstanceNode {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 节点id
     */
    @TableField(value = "node_id")
    private Integer nodeId;

    /**
     * 父节点id
     */
    @TableField(value = "node_parent_id")
    private Integer nodeParentId;

    /**
     * 实例id
     */
    @TableField(value = "instance_id")
    private Integer instanceId;

    /**
     * 实例编码
     */
    @TableField(value = "instance_code")
    private String instanceCode;

    /**
     * 任务编码
     */
    @TableField(value = "task_code")
    private String taskCode;

    /**
     * 企业id
     */
    @TableField(value = "company_id")
    private Integer companyId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 用户名称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 申请人姓名
     */
    @TableField(value = "full_name")
    private String fullName;

    /**
     * 定义节点id
     */
    @TableField(value = "def_node_id")
    private Integer defNodeId;

    /**
     * 定义节点名称
     */
    @TableField(value = "def_node_code")
    private String defNodeCode;

    /**
     *  状态：0待处理，1同意，2不同意等
     */
    @TableField(value = "action_status")
    private Integer actionStatus;

    /**
     * 批阅时间
     */
    @TableField(value = "action_time")
    private Date actionTime;

    /**
     * 批注
     */
    @TableField(value = "note")
    private String note;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 修改时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

    /**
     * 查询code
     */
    @TableField(value = "code")
    private String code;

    public static final String COL_ID = "id";

    public static final String COL_NODE_ID = "node_id";

    public static final String COL_NODE_PARENT_ID = "node_parent_id";

    public static final String COL_INSTANCE_ID = "instance_id";

    public static final String COL_INSTANCE_CODE = "instance_code";

    public static final String COL_TASK_CODE = "task_code";

    public static final String COL_COMPANY_ID = "company_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USER_NAME = "user_name";

    public static final String COL_FULL_NAME = "full_name";

    public static final String COL_DEF_NODE_ID = "def_node_id";

    public static final String COL_DEF_NODE_CODE = "def_node_code";

    public static final String COL_ACTION_STATUS = "action_status";

    public static final String COL_ACTION_TIME = "action_time";

    public static final String COL_NOTE = "note";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_CODE = "code";
}