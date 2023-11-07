package org.apache.alan.wrokflow.service;


import org.apache.alan.wrokflow.dto.FlowTestOrderInvalidDto;
import org.apache.alan.wrokflow.service.impl.FlowTestServiceImpl;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-07 18:27
 */
public interface FlowTestService  {

    /**
     * 订单创建
     * @param userId
     * @param orderId
     * @return
     */
    FlowTestServiceImpl.FlowTestCreateVo created(Integer userId, Integer orderId);

    /**
     * 订单作废
     * @param dto
     * @return
     */
    int invalid(FlowTestOrderInvalidDto dto);


    void finish();

    /**
     * 创建客户
     * @param userId
     * @param customerId
     * @return
     */
    FlowTestServiceImpl.FlowTestCreateVo createCustomer(Integer userId, Integer customerId);
}
