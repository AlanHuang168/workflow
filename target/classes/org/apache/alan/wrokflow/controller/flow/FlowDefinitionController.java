package org.apache.alan.wrokflow.controller.flow;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.alan.wrokflow.dto.Authentication;
import org.apache.alan.wrokflow.dto.FlowDefinitionCreateDto;
import org.apache.alan.wrokflow.form.FlowCodeForm;
import org.apache.alan.wrokflow.result.ActionResult;
import org.apache.alan.wrokflow.service.FlowDefinitionService;
import org.apache.alan.wrokflow.vo.FlowDefinitionDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: Alan
 * @date: 2023-11-03 16:19
 */
@Api(tags = "企业端--审批流定义")
@RestController
@RequestMapping("/pc/flowDefinition")
public class FlowDefinitionController {

    @Autowired
    private FlowDefinitionService flowDefinitionService;



    @ApiOperation("新建审批模板")
    @PostMapping("/createTemplate")
    public ActionResult<?> create(@Validated @RequestBody FlowDefinitionCreateDto form) {
        Authentication auth = null;
        flowDefinitionService.createTemplate(form, auth);
        return ActionResult.ok();
    }

    @ApiOperation("编辑审批模板")
    @PostMapping("/editTemplate")
    public ActionResult<Void> editTemplate(@Validated @RequestBody FlowDefinitionCreateDto form) {
        flowDefinitionService.editTemplate(form);
        return ActionResult.ok();
    }

    @ApiOperation("删除审批模板")
    @PostMapping("/deleteTemplate")
    public ActionResult<Void> deleteTemplate(@Validated @RequestBody FlowCodeForm form) {
        flowDefinitionService.deleteTemplate(form.getCode());
        return ActionResult.ok();
    }

    @ApiOperation("审批模板详情")
    @PostMapping("/templateDetail")
    public ActionResult<FlowDefinitionDetailVo> templateDetail(@Validated @RequestBody FlowCodeForm form) {
        FlowDefinitionDetailVo definitionDetail = flowDefinitionService.templateDetail(form.getCode());
        return ActionResult.ok(definitionDetail);
    }

    @ApiOperation("开启审批模板")
    @PostMapping("/openFlowDefinition")
    public ActionResult<Void> openFlowDefinition(@Validated @RequestBody FlowCodeForm form) {
        flowDefinitionService.openFlowDefinition(form.getCode());
        return ActionResult.ok();
    }
}
