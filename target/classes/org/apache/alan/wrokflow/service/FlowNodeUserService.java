package org.apache.alan.wrokflow.service;

import org.apache.alan.wrokflow.dao.po.FlowNodeUser;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @create: 2022-12-05 17:39
 **/
public interface FlowNodeUserService extends BaseService<FlowNodeUser>{

    void deleteByNodeIds(List<Integer> nodesIds);

    List<FlowNodeUser> getByTargetIdAndNodeId(Integer targetId, Integer nodeId);

    boolean checkCondition(Integer nodeId, Integer userId);

    List<FlowNodeUser> getByNodeId(Integer nodeId);
    /**
     * 根据节点ID和审批人类型获取当前节点的处理人
     * @param nodeId
     * @param type
     * @return
     */
    List<FlowNodeUser> getByNodeId(Integer nodeId, Integer type);

    void updateUserType(Integer targetId, Integer nodeId, int userType);
}
