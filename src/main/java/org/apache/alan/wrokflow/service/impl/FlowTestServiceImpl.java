package org.apache.alan.wrokflow.service.impl;


import lombok.Data;
import org.apache.alan.wrokflow.annotation.WorkFlow;
import org.apache.alan.wrokflow.dao.po.FlowModule;
import org.apache.alan.wrokflow.dto.FlowTestOrderInvalidDto;
import org.apache.alan.wrokflow.enums.FinishFlowEnum;
import org.apache.alan.wrokflow.enums.FlowModuleEnum;
import org.apache.alan.wrokflow.exception.ApplicationException;
import org.apache.alan.wrokflow.service.FlowBaseService;
import org.apache.alan.wrokflow.service.FlowModuleService;
import org.apache.alan.wrokflow.service.FlowTestService;
import org.apache.alan.wrokflow.utils.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-07 18:27
 */
@Service
public class FlowTestServiceImpl implements FlowTestService {

    @Autowired
    private FlowModuleService flowModuleService;

    @Override
    @WorkFlow(moduleValue =  "order.valid",spelValue = "#orderId")
    public FlowTestCreateVo created(Integer userId,Integer orderId) {
        System.out.println("==============>执行订单生效逻辑order.valid");
        if(userId < 0){
            throw new ApplicationException("异常");
        }
        FlowTestCreateVo vo = new FlowTestCreateVo();
        vo.setBId(1);
        //创建订单的业务逻辑
        return vo;
    }

    @Override
    @WorkFlow(moduleValue =  "order.invalid",spelValue = "#dto.orderId")
    public int invalid(FlowTestOrderInvalidDto dto) {
        System.out.println("==============>执行订单作废逻辑order.invalid");
        //业务逻辑
        return 0;
    }

    @Override
    public void finish(){
        FlowModule parentModule = flowModuleService.getParentModuleByChildId(17);
        // 通知对应模块，流程结束
        FlowModuleEnum flowModuleEnum = FlowModuleEnum.getById(parentModule.getModuleCode());
        FlowBaseService flowBaseService = SpringContextHolder.getBean(flowModuleEnum.getServiceClass());
        flowBaseService.flowFinished(1,"order.valid", FinishFlowEnum.FINISHED);
    }

    @Override
    @WorkFlow(moduleValue =  "customer.create",spelValue = "#customerId")
    public FlowTestCreateVo createCustomer(Integer userId, Integer customerId) {
        System.out.println("==============>执行业务员邀请客户审批逻辑");

        return null;
    }

    @Data
    public static class FlowTestCreateVo{
        public int bId;
    }
}

