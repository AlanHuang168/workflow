package org.apache.alan.wrokflow.service;

import org.apache.alan.wrokflow.dao.po.FlowDefinitionNode;
import org.apache.alan.wrokflow.dao.po.FlowInstance;
import org.apache.alan.wrokflow.dao.po.FlowInstanceNode;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @create: 2022-12-13 19:14
 **/
public interface FlowInstanceNodeService extends BaseService<FlowInstanceNode>{

    void saveInstanceNode(FlowInstance flowInstance, int parentNodeId, FlowDefinitionNode flowDefinitionNode);

    void saveInstanceNode(FlowInstance flowInstance, FlowDefinitionNode flowDefinitionNode);

    FlowInstanceNode getByTaskCode(String taskCode);

    FlowInstanceNode getByDefinitionId(int definitionId);

    List<FlowInstanceNode> getUnAudit(int definitionId, Integer instancdId);

    FlowInstanceNode saveFlowInstanceNode(FlowInstance flowInstance, FlowDefinitionNode flowDefinitionNode);
}
