package org.apache.alan.wrokflow.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "flow_my_module")
public class FlowMyModule {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 查询code
     */
    @TableField(value = "code")
    private String code;

    /**
     * 客户企业id
     */
    @TableField(value = "company_id")
    private Integer companyId;

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
     * 是否开启
     */
    @TableField(value = "open")
    private Boolean open;

    /**
     * 定义名称
     */
    @TableField(value = "def_name")
    private String defName;

    /**
     * 审批定义id
     */
    @TableField(value = "definition_id")
    private Integer definitionId;

    /**
     * 描述
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 设置审批流状态 0未设置 1已设置 2已发布
     */
    @TableField(value = "flow_set")
    private Integer flowSet;

    /**
     * 是否删除：0否，1是
     */
    @TableField(value = "deleted")
    private Boolean deleted;

    @TableField(value = "created_time")
    private Date createdTime;

    @TableField(value = "updated_time")
    private Date updatedTime;

    public static final String COL_ID = "id";

    public static final String COL_CODE = "code";

    public static final String COL_COMPANY_ID = "company_id";

    public static final String COL_MODULE_ID = "module_id";

    public static final String COL_MODULE_NAME = "module_name";

    public static final String COL_OPEN = "open";

    public static final String COL_DEF_NAME = "def_name";

    public static final String COL_DEFINITION_ID = "definition_id";

    public static final String COL_REMARK = "remark";

    public static final String COL_FLOW_SET = "flow_set";

    public static final String COL_DELETED = "deleted";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";
}