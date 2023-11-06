package org.apache.alan.wrokflow.service;


import org.apache.alan.wrokflow.dto.FlowTestOrderInvalidDto;
import org.apache.alan.wrokflow.service.impl.FlowTestServiceImpl;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-07 18:27
 */
public interface FlowTestService  {

    FlowTestServiceImpl.FlowTestCreateVo created(Integer userId, Integer orderId);

    int invalid(FlowTestOrderInvalidDto dto);

    void finish();

    FlowTestServiceImpl.FlowTestCreateVo createCustomer(Integer userId, Integer customerId);
}
