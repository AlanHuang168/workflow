package org.apache.alan.wrokflow.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "flow_node_permission")
public class FlowNodePermission {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 发起人
     */
    @TableField(value = "name")
    private String name;

    /**
     * 类型1个人 3部门
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 目标id（操作人id）
     */
    @TableField(value = "target_id")
    private Integer targetId;

    /**
     * 流程id
     */
    @TableField(value = "definition_id")
    private Integer definitionId;

    @TableField(value = "created_time")
    private Date createdTime;

    @TableField(value = "updated_time")
    private Date updatedTime;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_TYPE = "type";

    public static final String COL_TARGET_ID = "target_id";

    public static final String COL_DEFINITION_ID = "definition_id";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";
}