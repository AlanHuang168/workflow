package org.apache.alan.wrokflow.result;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.alan.wrokflow.constant.ErrorCodes;
import org.apache.alan.wrokflow.exception.ApplicationException;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ApiModel
public class ActionResult<T> implements Serializable {

    @ApiModelProperty(name = "code", value = "状态码（0：成功，其他：失败）")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer code;

    @ApiModelProperty(name = "message",value = "状态说明")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @ApiModelProperty(name = "timestamp",value = "时间戳")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long timestamp;

    @ApiModelProperty(name = "data",value = "返回数据")
    @Setter(AccessLevel.PROTECTED)
    private T data;

    @JsonIgnore
    public boolean isOk() {
        return code == null || code == 0;
    }

    public static <T> ActionResult<T> ok() {
        ActionResult<T> ret = new ActionResult<T>();
        ret.code = ErrorCodes.OK;
        ret.message = "success";
        return ret;
    }
    public static <T> ActionResult<T> ok(Integer code) {
        ActionResult<T> ret = new ActionResult<T>();
        ret.code = code;
        ret.message = "success";
        return ret;
    }

    public static <T> ActionResult<T> ok(T content) {
        ActionResult<T> ret = ok();
        ret.data = content;
        return ret;
    }

    public static <T> ActionResult<T> ok(T content, String message) {
        ActionResult<T> ret = ok();
        ret.data = content;
        ret.setMessage(message);
        return ret;
    }


    public static <T> ActionResult<T> ok(Integer code, String message) {
        ActionResult<T> ret = ok();
        ret.message = message;
        ret.code = code;
        return ret;
    }

    public static <T> ActionResult<T> ok(Integer code, T content) {
        ActionResult<T> ret = ok();
        ret.data = content;
        ret.code = code;
        return ret;
    }

    public static <T> ActionResult<T> ok(Integer code, T content, String message) {
        ActionResult<T> ret = ok();
        ret.data = content;
        ret.code = code;
        ret.setMessage(message);
        return ret;
    }


    public static <T> ActionResult<T> error(int code, String message) {
        ActionResult<T> ret = new ActionResult<T>();
        ret.code = code;
        ret.message = message;
        return ret;
    }

    public static <T> ActionResult<T> error(int code, String message, T content) {
        ActionResult<T> ret = new ActionResult<T>();
        ret.code = code;
        ret.message = message;
        ret.data = content;
        return ret;
    }

    public static <T> ActionResult<T> error(String message) {
        return error(ErrorCodes.BUSINESS, message);
    }


    public static <T> ActionResult<T> error() {
        return error(ErrorCodes.BUSINESS,"Service exception");//业务异常
    }

    public static <T> ActionResult<T> error(ActionResult<?> result) {
        return error(result.getCode(), result.getMessage());
    }

    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return JSONUtils.toJSONString(this);
    }

    public T throwExIfError() {
        if (isOk()) {
            return data;
        }
        throw new ApplicationException(this);
    }

    public <NT> ActionResult<NT> newError() {
        return ActionResult.error(this);
    }

}

