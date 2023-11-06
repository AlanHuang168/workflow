package org.apache.alan.wrokflow.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "flow_module")
public class FlowModule {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 查询code
     */
    @TableField(value = "code")
    private String code;

    /**
     * 模块名
     */
    @TableField(value = "name")
    private String name;

    @TableField(value = "parent_id")
    private Integer parentId;

    @TableField(value = "module_code")
    private String moduleCode;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    @TableField(value = "remark")
    private String remark;

    /**
     * 关联类的全路径
     */
    @TableField(value = "rel_class")
    private String relClass;

    @TableField(value = "deleted")
    private Boolean deleted;

    @TableField(value = "logo")
    private String logo;

    public static final String COL_ID = "id";

    public static final String COL_CODE = "code";

    public static final String COL_NAME = "name";

    public static final String COL_PARENT_ID = "parent_id";

    public static final String COL_MODULE_CODE = "module_code";

    public static final String COL_SORT = "sort";

    public static final String COL_REMARK = "remark";

    public static final String COL_REL_CLASS = "rel_class";

    public static final String COL_DELETED = "deleted";

    public static final String COL_LOGO = "logo";
}