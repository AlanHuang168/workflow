package org.apache.alan.wrokflow.service;


import org.apache.alan.wrokflow.dao.po.FlowDefinitionNode;
import org.apache.alan.wrokflow.dao.po.FlowInstance;

/**
 * @description:
 * @author: Administrator
 * @create: 2022-12-03 15:55
 **/
public interface FlowDefinitionNodeService  extends BaseService<FlowDefinitionNode>{

    void deleteByDefinitId(int definitId);

    /**
     * 获取下一个节点
     * @param definitId 流程定义id
     * @param userId 用户id
     * @param parentId 父节定义节点
     * @param type 发起人 1审批 2抄送 3条件 4路由
     * @return
     */
    FlowDefinitionNode getNextDefinitionCode(int definitId, int userId, int parentId, int type, int instanceId) ;

    /**
     * 获取下一个节点
     * @param definitId 流程定义id
     * @param parentId 父节定义节点
     * @param type 发起人 1审批 2抄送 3条件 4路由
     * @param instanceId
     * @return
     */
    FlowDefinitionNode getNextDefinitionCode(int definitId, int parentId, int type, int instanceId, FlowInstance instance);

    /**
     * 同一个节点 审批人
     * @param definitId
     * @param defNodeId
     * @return
     */
    FlowDefinitionNode findSameRootNode(int definitId, int defNodeId);

    /**
     * 得到这个节点的相关信息
     * @param definitId
     * @param defNodeId
     * @return
     */
    FlowDefinitionNode findNodeByNodeId(int definitId, int defNodeId);


    FlowDefinitionNode findById(Integer id);
}
