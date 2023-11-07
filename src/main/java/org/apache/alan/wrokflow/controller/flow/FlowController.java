package org.apache.alan.wrokflow.controller.flow;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.alan.wrokflow.dto.Authentication;
import org.apache.alan.wrokflow.dto.FlowInstanceDetailDto;
import org.apache.alan.wrokflow.dto.FlowInstancePageDto;
import org.apache.alan.wrokflow.enums.NodeStatusEnum;
import org.apache.alan.wrokflow.form.FlowInstanceApproveForm;
import org.apache.alan.wrokflow.form.FlowInstancePageForm;
import org.apache.alan.wrokflow.result.ActionResult;
import org.apache.alan.wrokflow.service.FlowInstanceService;
import org.apache.alan.wrokflow.service.FlowMyModuleService;
import org.apache.alan.wrokflow.utils.MapperUtils;
import org.apache.alan.wrokflow.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api(tags = "企业端--审批任务")
@RestController
@RequestMapping("/flow")
public class FlowController {

    @Autowired
    private FlowInstanceService flowInstanceService;
    @Autowired
    private FlowMyModuleService fowMyModuleService;

    @ApiOperation("查询企业审批类型列表")
    @PostMapping("/getCompanyModuleList")
    public ActionResult<List<FlowModuleListVo>> getCompanyModuleList(Authentication auth) {
        return ActionResult.ok(fowMyModuleService.get(auth.getCompanyId()));
    }

    @ApiOperation("审批记录")
    @PostMapping("/myTask")
    public ActionResult<PageVo<FlowInstancePageVo>> myTask(@Validated @RequestBody FlowInstancePageForm form, Authentication auth) {
        FlowInstancePageDto dto = MapperUtils.map(form,FlowInstancePageDto.class);
        dto.setCompanyId(auth.getCompanyId());
        dto.setUserId(auth.getUserId());
        PageVo<FlowInstancePageVo> pageVo = flowInstanceService.listPage(dto);
        return ActionResult.ok(pageVo);
    }


    @ApiOperation("审批通过/拒绝")
    @PostMapping("/approve")
    public ActionResult<?> approve(@Validated @RequestBody FlowInstanceApproveForm form, Authentication auth) {
        int action = form.getOpinion();
        if (action == NodeStatusEnum.AGREE.getStatus()) {
            flowInstanceService.agree(form.getCode(), form.getNote(),auth);
        } else if (action == NodeStatusEnum.DISAGREE.getStatus()) {
            flowInstanceService.disagree(form.getCode(), form.getNote(),auth);
        } else {
            return ActionResult.error("操作不合法");
        }
        return ActionResult.ok();
    }

    @ApiOperation("审批流程记录详情")
    @PostMapping("/detail")
    public ActionResult<List<FlowTaskDetailVo>> detail(@Validated @RequestBody FlowInstanceDetailDto form, Authentication auth) {
        List<FlowTaskDetailVo> voList = flowInstanceService.details(form.getInstanceCode(),auth);
        return ActionResult.ok(voList);
    }

    @ApiOperation("业务功能详情")
    @PostMapping("/businessDetail")
    public ActionResult<FlowBusinessDetailVo> businessDetail(@Validated @RequestBody FlowInstanceDetailDto form, Authentication auth) {
        return ActionResult.ok(flowInstanceService.businessDetail(form.getInstanceCode(),auth));
    }

    @ApiOperation("批量审批通过/拒绝")
    @PostMapping("/batchApprove")
    public ActionResult<?> batchApprove(@Validated @RequestBody FlowInstanceApproveForm form, Authentication auth) {
        int action = form.getOpinion();
        if (action == NodeStatusEnum.AGREE.getStatus()||action==NodeStatusEnum.DISAGREE.getStatus()) {
            flowInstanceService.batchApprove(action,form.getCodes(),form.getNote(),auth);
        } else {
            return ActionResult.error("操作不合法");
        }
        return ActionResult.ok();
    }
}
