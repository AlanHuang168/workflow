package org.apache.alan.wrokflow.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "user")
public class User {
    /**
     * 用户主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 手机号
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 联系电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 省份区域代码
     */
    @TableField(value = "province_regioncode")
    private String provinceRegioncode;

    /**
     * 地市区域代码
     */
    @TableField(value = "city_regioncode")
    private String cityRegioncode;

    /**
     * 地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 角色id,可多个逗号隔开
     */
    @TableField(value = "role_id")
    private String roleId;

    /**
     * 所属企业id，如果存在多个用逗号隔开
     */
    @TableField(value = "company_id")
    private String companyId;

    /**
     * 是否通知系统消息 0：关 1：开
     */
    @TableField(value = "notify_message")
    private Byte notifyMessage;

    /**
     * 是否通知待办任务 0：关 1：开
     */
    @TableField(value = "notify_todo")
    private Byte notifyTodo;

    /**
     * 修改手机确认函
     */
    @TableField(value = "confirmation_image")
    private String confirmationImage;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 租户编码
     */
    @TableField(value = "tenant_code")
    private String tenantCode;

    /**
     * 用户修改密码时间
     */
    @TableField(value = "update_pwd_time")
    private Date updatePwdTime;

    /**
     * 查询code
     */
    @TableField(value = "code")
    private String code;

    public static final String COL_ID = "id";

    public static final String COL_USERNAME = "username";

    public static final String COL_PASSWORD = "password";

    public static final String COL_EMAIL = "email";

    public static final String COL_MOBILE = "mobile";

    public static final String COL_NICKNAME = "nickname";

    public static final String COL_PHONE = "phone";

    public static final String COL_PROVINCE_REGIONCODE = "province_regioncode";

    public static final String COL_CITY_REGIONCODE = "city_regioncode";

    public static final String COL_ADDRESS = "address";

    public static final String COL_AVATAR = "avatar";

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_COMPANY_ID = "company_id";

    public static final String COL_NOTIFY_MESSAGE = "notify_message";

    public static final String COL_NOTIFY_TODO = "notify_todo";

    public static final String COL_CONFIRMATION_IMAGE = "confirmation_image";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_TENANT_CODE = "tenant_code";

    public static final String COL_UPDATE_PWD_TIME = "update_pwd_time";

    public static final String COL_CODE = "code";
}