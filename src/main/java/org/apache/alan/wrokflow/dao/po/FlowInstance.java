package org.apache.alan.wrokflow.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "flow_instance")
public class FlowInstance {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 流程名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 实例代码
     */
    @TableField(value = "instance_code")
    private String instanceCode;

    /**
     * 定义id
     */
    @TableField(value = "definition_id")
    private Integer definitionId;

    /**
     * 定义代码
     */
    @TableField(value = "definition_code")
    private String definitionCode;

    /**
     * 申请人用户id
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 申请人用户名称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 申请人企业id
     */
    @TableField(value = "company_id")
    private Integer companyId;

    /**
     * 申请人姓名
     */
    @TableField(value = "full_name")
    private String fullName;

    /**
     * 模块id 配置表
     */
    @TableField(value = "module_id")
    private Integer moduleId;

    /**
     * 模块名称
     */
    @TableField(value = "module_name")
    private String moduleName;

    /**
     * 业务id
     */
    @TableField(value = "b_id")
    private Integer bId;

    @TableField(value = "deleted")
    private Boolean deleted;

    /**
     * 状态：0创建,1进行中,2已完成,3已终止
     */
    @TableField(value = "status")
    private Integer status;

    @TableField(value = "created_time")
    private Date createdTime;

    @TableField(value = "updated_time")
    private Date updatedTime;

    /**
     * 查询CODE
     */
    @TableField(value = "code")
    private String code;

    /**
     * 业务金额
     */
    @TableField(value = "biz_amount")
    private BigDecimal bizAmount;

    /**
     * 审批内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 业务记录查询CODE
     */
    @TableField(value = "b_code")
    private String bCode;

    public static final String COL_ID = "id";

    public static final String COL_TITLE = "title";

    public static final String COL_INSTANCE_CODE = "instance_code";

    public static final String COL_DEFINITION_ID = "definition_id";

    public static final String COL_DEFINITION_CODE = "definition_code";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USER_NAME = "user_name";

    public static final String COL_COMPANY_ID = "company_id";

    public static final String COL_FULL_NAME = "full_name";

    public static final String COL_MODULE_ID = "module_id";

    public static final String COL_MODULE_NAME = "module_name";

    public static final String COL_B_ID = "b_id";

    public static final String COL_DELETED = "deleted";

    public static final String COL_STATUS = "status";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_CODE = "code";

    public static final String COL_BIZ_AMOUNT = "biz_amount";

    public static final String COL_CONTENT = "content";

    public static final String COL_B_CODE = "b_code";
}
