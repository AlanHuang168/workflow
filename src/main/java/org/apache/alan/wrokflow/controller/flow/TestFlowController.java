package org.apache.alan.wrokflow.controller.flow;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.alan.wrokflow.dto.Authentication;
import org.apache.alan.wrokflow.dto.FlowTestOrderInvalidDto;
import org.apache.alan.wrokflow.result.ActionResult;
import org.apache.alan.wrokflow.service.FlowTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: Alan
 * @date: 2023-11-07 17:15
 */
@Api(tags = "企业端--测试审批流触发")
@RestController
@RequestMapping("/flowDefinition")
public class TestFlowController {

    @Autowired
    private FlowTestService flowTestService;

    @Deprecated
    @ApiOperation("测试订单生效")
    @PostMapping("/testOrderCreated")
    public ActionResult<?> created() {
        flowTestService.created(1,1);
        return ActionResult.ok();
    }

    @Deprecated
    @ApiOperation("测试订单作废")
    @PostMapping("/testOrderInvalid")
    public ActionResult<?> test() {
        FlowTestOrderInvalidDto dto = new FlowTestOrderInvalidDto();
        dto.setOrderId(1);
        dto.setUserId(1);
        flowTestService.invalid(dto);
        return ActionResult.ok();
    }

    @Deprecated
    @ApiOperation("测试订单")
    @PostMapping("/testOrderfinish")
    public ActionResult<?> finish() {
        flowTestService.finish();
        return ActionResult.ok();
    }

    @Deprecated
    @ApiOperation("测试订业务员邀请客户")
    @PostMapping("/testCustomerCreate")
    public ActionResult<?> testCustomerCreate() {
        flowTestService.createCustomer(1000036,38);
        return ActionResult.ok();
    }


}
