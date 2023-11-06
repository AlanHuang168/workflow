package org.apache.alan.wrokflow.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "flow_definition_node")
public class FlowDefinitionNode {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 定义审批id
     */
    @TableField(value = "definition_id")
    private Integer definitionId;

    /**
     * 节点code
     */
    @TableField(value = "node_code")
    private String nodeCode;

    /**
     * 节点名称
     */
    @TableField(value = "node_name")
    private String nodeName;

    /**
     * 父节点id
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     * 父节点code
     */
    @TableField(value = "parent_code")
    private String parentCode;

    /**
     * 发起人 1审批 2抄送 3条件 4路由
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 条件优先级
     */
    @TableField(value = "priority_level")
    private Integer priorityLevel;

    /**
     * 审批人设置 1指定成员 2主管 4发起人自选 5发起人自己 7连续多级主管
     */
    @TableField(value = "settype")
    private Integer settype;

    /**
     * 审批人数 1选一个人 2选多个人
     */
    @TableField(value = "select_mode")
    private Integer selectMode;

    /**
     * 选择范围 1.全公司 2指定成员 2指定角色
     */
    @TableField(value = "select_range")
    private Integer selectRange;

    /**
     * 审批终点  最高层主管数
     */
    @TableField(value = "director_level")
    private Integer directorLevel;

    /**
     * 多人审批时采用的审批方式 1依次审批 2会签
     */
    @TableField(value = "examine_mode")
    private Integer examineMode;

    /**
     * 审批人为空时 1自动审批通过/不允许发起 2转交给审核管理员
     */
    @TableField(value = "no_hander_action")
    private Integer noHanderAction;

    /**
     * 审批终点 第n层主管
     */
    @TableField(value = "examine_end_director_level")
    private Integer examineEndDirectorLevel;

    /**
     * 是否删除：0否，1是
     */
    @TableField(value = "deleted")
    private Boolean deleted;

    /**
     * 允许发起人自选抄送人
     */
    @TableField(value = "cc_self_select_flag")
    private Integer ccSelfSelectFlag;

    @TableField(value = "created_time")
    private Date createdTime;

    @TableField(value = "updated_time")
    private Date updatedTime;

    /**
     * 发起人是否所有人
     */
    @TableField(value = "sponsor_all_flag")
    private Boolean sponsorAllFlag;

    /**
     * 节点顺序
     */
    @TableField(value = "definition_sort")
    private Integer definitionSort;

    @TableField(exist = false)
    private Boolean lastNode=false;

    @TableField(exist = false)
    private Boolean errNode = false;


    public static final String COL_ID = "id";

    public static final String COL_DEFINITION_ID = "definition_id";

    public static final String COL_NODE_CODE = "node_code";

    public static final String COL_NODE_NAME = "node_name";

    public static final String COL_PARENT_ID = "parent_id";

    public static final String COL_PARENT_CODE = "parent_code";

    public static final String COL_TYPE = "type";

    public static final String COL_PRIORITY_LEVEL = "priority_level";

    public static final String COL_SETTYPE = "settype";

    public static final String COL_SELECT_MODE = "select_mode";

    public static final String COL_SELECT_RANGE = "select_range";

    public static final String COL_DIRECTOR_LEVEL = "director_level";

    public static final String COL_EXAMINE_MODE = "examine_mode";

    public static final String COL_NO_HANDER_ACTION = "no_hander_action";

    public static final String COL_EXAMINE_END_DIRECTOR_LEVEL = "examine_end_director_level";

    public static final String COL_DELETED = "deleted";

    public static final String COL_CC_SELF_SELECT_FLAG = "cc_self_select_flag";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_SPONSOR_ALL_FLAG = "sponsor_all_flag";

    public static final String COL_DEFINITION_SORT = "definition_sort";
}
