package org.apache.alan.wrokflow.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "flow_node_condition")
public class FlowNodeCondition {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     *  发起人
     */
    @TableField(value = "column_id")
    private Integer columnId;

    /**
     * 审批节点
     */
    @TableField(value = "node_id")
    private Integer nodeId;

    /**
     * 1 发起人 2其他
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 操作类型["", "<", ">", "≤", "=", "≥"]
     */
    @TableField(value = "opt_type")
    private String optType;

    /**
     * 左侧自定义内容
     */
    @TableField(value = "zdy1")
    private String zdy1;

    /**
     * 右侧自定义内容
     */
    @TableField(value = "zdy2")
    private String zdy2;

    /**
     * 左侧符号 < ≤
     */
    @TableField(value = "opt1")
    private String opt1;

    /**
     * 右侧符号 < ≤
     */
    @TableField(value = "opt2")
    private String opt2;

    /**
     * 条件字段名称
     */
    @TableField(value = "cloumn_db_name")
    private String cloumnDbName;

    /**
     * 条件字段类型
     */
    @TableField(value = "column_type")
    private String columnType;

    /**
     * checkBox多选 其他
     */
    @TableField(value = "show_type")
    private String showType;

    /**
     * 展示名
     */
    @TableField(value = "show_name")
    private String showName;

    /**
     * 多选数组
     */
    @TableField(value = "fixed_down_box_value")
    private String fixedDownBoxValue;

    /**
     * 是否删除：0否，1是
     */
    @TableField(value = "deleted")
    private Boolean deleted;

    @TableField(value = "created_time")
    private Date createdTime;

    @TableField(value = "updated_time")
    private Date updatedTime;

    /**
     * 指定人
     */
    @TableField(value = "user_ids")
    private String userIds;

    public static final String COL_ID = "id";

    public static final String COL_COLUMN_ID = "column_id";

    public static final String COL_NODE_ID = "node_id";

    public static final String COL_TYPE = "type";

    public static final String COL_OPT_TYPE = "opt_type";

    public static final String COL_ZDY1 = "zdy1";

    public static final String COL_ZDY2 = "zdy2";

    public static final String COL_OPT1 = "opt1";

    public static final String COL_OPT2 = "opt2";

    public static final String COL_CLOUMN_DB_NAME = "cloumn_db_name";

    public static final String COL_COLUMN_TYPE = "column_type";

    public static final String COL_SHOW_TYPE = "show_type";

    public static final String COL_SHOW_NAME = "show_name";

    public static final String COL_FIXED_DOWN_BOX_VALUE = "fixed_down_box_value";

    public static final String COL_DELETED = "deleted";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_USER_IDS = "user_ids";

    public static final String SHOW_TYPE_INPUT = "input";
    public static final String SHOW_TYPE_CHECKBOX = "checkBox";

}
