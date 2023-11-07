package org.apache.alan.wrokflow.controller.flow;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.alan.wrokflow.dto.Authentication;
import org.apache.alan.wrokflow.result.ActionResult;
import org.apache.alan.wrokflow.service.FlowModuleService;
import org.apache.alan.wrokflow.vo.FlowModuleGetCompanyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author: Alan
 * @date: 2023-11-03 16:19
 */
@RestController
@RequestMapping("/pc/flowModule")
@Api(tags = "企业端--业务审批功能相关接口")
public class FlowModuleController {

    @Autowired
    private FlowModuleService flowModuleService;


    @ApiOperation("获取企业业务审批功能列表")
    @PostMapping("/getCompanyFlowModule")
    public ActionResult<List<FlowModuleGetCompanyVo>> getCompanyFlowModule(Authentication auth) {
        List<FlowModuleGetCompanyVo> voList = flowModuleService.getCompanyFlowModule(auth.getCompanyId());
        return ActionResult.ok(voList);
    }

}
