package org.apache.alan.wrokflow.form;

import com.pooul.fenxiaoyi.common.form.PageForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class FlowInstancePageForm extends PageForm {

    @ApiModelProperty(value = "模块查询code")
    private String code;

    @ApiModelProperty(value = "申请开始时间")
    private String startTime;

    @ApiModelProperty(value = "申请结束时间")
    private String endTime;

    @ApiModelProperty(value = "状态：0全部，1待处理，2已处理，3我发起的，4抄送我的",required = true)
    @NotNull(message = "查询状态不能为空")
    @Range(min = 0,max = 4,message = "状态：0全部，1待处理，2已处理，3我发起的，4抄送我的")
    private Integer status;


}
