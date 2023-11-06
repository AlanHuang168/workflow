package org.apache.alan.wrokflow.service;

import org.apache.alan.wrokflow.dao.po.FlowInstance;
import org.apache.alan.wrokflow.dao.po.FlowNodeCondition;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @create: 2022-12-05 17:30
 **/
public interface FlowNodeConditionService  extends BaseService<FlowNodeCondition>{

    void deleteByNodeIds(List<Integer> nodeIds);

    boolean checkCondition(Integer nodeId, Integer userId);

    FlowNodeCondition checkCondition(Integer nodeId, FlowInstance flowInstance);
}
