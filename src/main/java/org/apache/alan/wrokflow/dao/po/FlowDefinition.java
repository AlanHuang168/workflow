package org.apache.alan.wrokflow.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "flow_definition")
public class FlowDefinition {
    /**
     * 审批id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 审批名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 审批主管最大层级
     */
    @TableField(value = "director_max_level")
    private Integer directorMaxLevel;

    /**
     * 审批code
     */
    @TableField(value = "definition_code")
    private String definitionCode;

    /**
     * 企业id
     */
    @TableField(value = "company_id")
    private Integer companyId;

    /**
     * 流程定义人
     */
    @TableField(value = "definition_user_id")
    private Integer definitionUserId;

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 定义人昵称
     */
    @TableField(value = "full_name")
    private String fullName;

    /**
     * 模块id
     */
    @TableField(value = "module_id")
    private Integer moduleId;

    /**
     * 模块名称
     */
    @TableField(value = "module_name")
    private String moduleName;

    /**
     * 是否开放（0未开启 1已开启）
     */
    @TableField(value = "status")
    private Boolean status;

    /**
     * 模板审批节点JSON
     */
    @TableField(value = "definition_node")
    private String definitionNode;

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

    @TableField(value = "deleted")
    private Boolean deleted;

    /**
     * 查询CODE
     */
    @TableField(value = "code")
    private String code;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_DIRECTOR_MAX_LEVEL = "director_max_level";

    public static final String COL_DEFINITION_CODE = "definition_code";

    public static final String COL_COMPANY_ID = "company_id";

    public static final String COL_DEFINITION_USER_ID = "definition_user_id";

    public static final String COL_USER_NAME = "user_name";

    public static final String COL_FULL_NAME = "full_name";

    public static final String COL_MODULE_ID = "module_id";

    public static final String COL_MODULE_NAME = "module_name";

    public static final String COL_STATUS = "status";

    public static final String COL_DEFINITION_NODE = "definition_node";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_DELETED = "deleted";

    public static final String COL_CODE = "code";
}