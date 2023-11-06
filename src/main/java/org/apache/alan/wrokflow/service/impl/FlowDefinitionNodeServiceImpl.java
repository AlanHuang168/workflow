package org.apache.alan.wrokflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.alan.wrokflow.dao.mapper.FlowDefinitionNodeMapper;
import org.apache.alan.wrokflow.dao.po.FlowDefinitionNode;
import org.apache.alan.wrokflow.dao.po.FlowInstance;
import org.apache.alan.wrokflow.dao.po.FlowNodeCondition;
import org.apache.alan.wrokflow.enums.FlowDefinitionNodeTypeEnum;
import org.apache.alan.wrokflow.service.FlowDefinitionNodeService;
import org.apache.alan.wrokflow.service.FlowInstanceService;
import org.apache.alan.wrokflow.service.FlowNodeConditionService;
import org.apache.alan.wrokflow.service.FlowNodeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-03 16:02
 */
@Slf4j
@Service
public class FlowDefinitionNodeServiceImpl extends BaseServiceImpl<FlowDefinitionNodeMapper, FlowDefinitionNode> implements FlowDefinitionNodeService {

    @Autowired
    private FlowNodeConditionService flowNodeConditionService;
    @Autowired
    private FlowNodeUserService flowNodeUserService;
    @Autowired
    private FlowInstanceService flowInstanceService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByDefinitId(int definitId) {
        QueryWrapper<FlowDefinitionNode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowDefinitionNode.COL_DEFINITION_ID,definitId);
        List<FlowDefinitionNode> list = list(queryWrapper);
        List<Integer> nodesIds = list.stream().map(FlowDefinitionNode::getId).collect(Collectors.toList());
        flowNodeConditionService.deleteByNodeIds(nodesIds);
        flowNodeUserService.deleteByNodeIds(nodesIds);
        removeByIds(nodesIds);

    }

    @Override
    public FlowDefinitionNode getNextDefinitionCode(int definitId,int userId,int parentId,int type,int instanceId) {
        List<FlowDefinitionNode> nextNode = getCreatorNode(definitId,parentId,type);
        if(nextNode == null){
            return null;
        }
        FlowDefinitionNode creatorNode = nextNode.get(0);
        //路由 目前只支持一个条件
        QueryWrapper<FlowDefinitionNode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowDefinitionNode.COL_DEFINITION_ID,definitId);
        queryWrapper.eq(FlowDefinitionNode.COL_PARENT_ID,creatorNode.getId());
        //queryWrapper.eq(FlowDefinitionNode.COL_TYPE, FlowDefinitionNodeTypeEnum.ROUTE.getValue());
        List<FlowDefinitionNode> list = list(queryWrapper);

        if(list.size() <=0 ){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(FlowDefinitionNode.COL_DEFINITION_ID,definitId);
            queryWrapper.eq(FlowDefinitionNode.COL_PARENT_ID,creatorNode.getParentId());
            list = list(queryWrapper);
        }

        //获取最后一个定义节点
        FlowDefinitionNode lastDefNode = getLastNodeByDefinitId(definitId);
        //路由下的条件
        for(FlowDefinitionNode node:list){
            if(node.getId() == instanceId){
                continue;
            }
            if(node.getId() == lastDefNode.getId()){
                node.setLastNode(true);
            }
            if(node.getType().intValue() == FlowDefinitionNodeTypeEnum.AUDIO.getValue()){
                //指定审批人
                return node;
            }
            if(node.getType().intValue() == FlowDefinitionNodeTypeEnum.ROUTE.getValue()) {

                FlowDefinitionNode flowDefinitionNode = getByParentId(definitId,node.getId());
                if(flowDefinitionNode != null){
                    return flowDefinitionNode;
                }
            }
            if(node.getType().intValue() == FlowDefinitionNodeTypeEnum.CONDITION.getValue()){

                return node;
            }
            if(node.getType().intValue() == FlowDefinitionNodeTypeEnum.COPY.getValue()){

                return node;
            }
        }
        return null;
    }


    @Override
    public FlowDefinitionNode getNextDefinitionCode(int definitId,int parentId,int type,int instanceId,
                                                    FlowInstance instance) {
        // 获取下一个节点
        List<FlowDefinitionNode> nextNode = getCreatorNode(definitId,parentId,type);
        if(nextNode == null){
            return null;
        }
        List<FlowDefinitionNode> list = new ArrayList<>();
        // 如果是初始化节点的时候则寻找下一个节点
        if(parentId==0) {
            FlowDefinitionNode creatorNode = nextNode.get(0);
            //获取第一个节点
            QueryWrapper<FlowDefinitionNode> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(FlowDefinitionNode.COL_DEFINITION_ID, definitId);
            queryWrapper.eq(FlowDefinitionNode.COL_PARENT_ID, creatorNode.getId());
            //queryWrapper.eq(FlowDefinitionNode.COL_TYPE, FlowDefinitionNodeTypeEnum.ROUTE.getValue());
            list = list(queryWrapper);
            // 获取审批流程节点
            if (list.size() <= 0) {
                queryWrapper = new QueryWrapper<>();
                queryWrapper.eq(FlowDefinitionNode.COL_DEFINITION_ID, definitId);
                queryWrapper.eq(FlowDefinitionNode.COL_PARENT_ID, creatorNode.getParentId());
                list = list(queryWrapper);
            }
        }else{
            list.addAll(nextNode);
        }
        //条件
        Boolean flag = false;
        for(FlowDefinitionNode node:list){
            log.info("获取下一个节点数据："+ JSON.toJSONString(node));
            if(node.getType().intValue() == FlowDefinitionNodeTypeEnum.AUDIO.getValue()){
                //指定审批人
                log.info("node.getType().intValue() == FlowDefinitionNodeTypeEnum.AUDIO.getValue()："+ (node.getType().intValue() == FlowDefinitionNodeTypeEnum.AUDIO.getValue()));
                return node;
            }
            // 条件分支
            if(node.getType().intValue() == FlowDefinitionNodeTypeEnum.CONDITION.getValue()){
                log.info("node.getType().intValue() == FlowDefinitionNodeTypeEnum.CONDITION.getValue()："+ (node.getType().intValue() == FlowDefinitionNodeTypeEnum.CONDITION.getValue()));
                //根据业务参数获取条件节点
                FlowNodeCondition condition = flowNodeConditionService.checkCondition(node.getId(),instance);
                if(Objects.isNull(condition)){
                    flag = true;
                    continue;
                }else {
                    // 根据条件节点获取下一个审批节点
                    FlowDefinitionNode flowDefinitionNode = getByParentId(definitId, node.getId());
                    flag = false;
                    return flowDefinitionNode;
                }
            }
            if(node.getType().intValue() == FlowDefinitionNodeTypeEnum.COPY.getValue()){
                log.info("node.getType().intValue() == FlowDefinitionNodeTypeEnum.COPY.getValue()："+ (node.getType().intValue() == FlowDefinitionNodeTypeEnum.COPY.getValue()));
                return node;
            }
        }
        if(flag){
            FlowDefinitionNode errNode = new FlowDefinitionNode();
            errNode.setErrNode(true);
            errNode.setType(0);
            log.info("条件分支未命中下级节点");
            return errNode;
        }
        return null;
    }

    @Override
    public FlowDefinitionNode findNodeByNodeId(int definitId,int defNodeId){
        return null;
    }

    @Override
    public FlowDefinitionNode findById(Integer id) {
        return baseMapper.findById(id);
    }

    @Override
    public FlowDefinitionNode findSameRootNode(int definitId,int defNodeId){
        int parentId = getById(defNodeId).getParentId();
        QueryWrapper<FlowDefinitionNode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowDefinitionNode.COL_DEFINITION_ID,definitId);
        queryWrapper.eq(FlowDefinitionNode.COL_PARENT_ID,parentId);
        List<FlowDefinitionNode> list = list(queryWrapper);
        //获取最后一个定义节点
        FlowDefinitionNode lastDefNode = getLastNodeByDefinitId(definitId);
        //路由下的条件
        for(FlowDefinitionNode node:list){
            if(node.getId() == defNodeId){
                //1依次审批2会签
                if(node.getExamineMode()==2) {
                    continue;
                }else {
                    return node;
                }
            }
            if(node.getId() == lastDefNode.getId()){
                node.setLastNode(true);
            }
            if(node.getType().intValue() == FlowDefinitionNodeTypeEnum.AUDIO.getValue()){
                //指定审批人
                return node;
            }
            // 条件分支
            if(node.getType().intValue() == FlowDefinitionNodeTypeEnum.ROUTE.getValue()) {

                FlowDefinitionNode flowDefinitionNode = getByParentId(definitId,node.getId());
                if(flowDefinitionNode != null){
                    return flowDefinitionNode;
                }

            }
            if(node.getType().intValue() == FlowDefinitionNodeTypeEnum.CONDITION.getValue()){

                return node;
            }
            if(node.getType().intValue() == FlowDefinitionNodeTypeEnum.COPY.getValue()){

                return node;
            }
        }
        return null;
    }







    public List<FlowDefinitionNode> getCreatorNode(int definitId, int parentId, int type){
        QueryWrapper<FlowDefinitionNode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowDefinitionNode.COL_DEFINITION_ID,definitId);
        queryWrapper.eq(FlowDefinitionNode.COL_PARENT_ID,parentId);
        if(type != -1) {
            queryWrapper.eq(FlowDefinitionNode.COL_TYPE, type);
        }
        queryWrapper.orderByAsc(FlowDefinitionNode.COL_ID);
        List<FlowDefinitionNode> list = list(queryWrapper);
        return list;
//        return list.size() > 0 ? list.get(0) : null;
    }

    private FlowDefinitionNode getByParentId(int definitId,int nodeId){
        QueryWrapper<FlowDefinitionNode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowDefinitionNode.COL_DEFINITION_ID,definitId);
        queryWrapper.eq(FlowDefinitionNode.COL_PARENT_ID,nodeId);
        queryWrapper.orderByAsc(FlowDefinitionNode.COL_ID);
        return list(queryWrapper).get(0);
    }
    public FlowDefinitionNode getLastNodeByDefinitId(int definitId){
        QueryWrapper<FlowDefinitionNode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowDefinitionNode.COL_DEFINITION_ID,definitId);
        queryWrapper.orderByDesc(FlowDefinitionNode.COL_ID);
        queryWrapper.last("limit 0,1");
        return getOne(queryWrapper);
    }
}
