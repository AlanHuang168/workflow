package org.apache.alan.wrokflow.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.alan.wrokflow.dao.mapper.FlowInstanceNodeMapper;
import org.apache.alan.wrokflow.dao.po.*;
import org.apache.alan.wrokflow.enums.FlowDefinitionNodeTypeEnum;
import org.apache.alan.wrokflow.enums.FlowModuleEnum;
import org.apache.alan.wrokflow.enums.InstanceStatusEnum;
import org.apache.alan.wrokflow.enums.NodeStatusEnum;
import org.apache.alan.wrokflow.service.*;
import org.apache.alan.wrokflow.utils.MapperUtils;
import org.apache.alan.wrokflow.utils.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-13 19:16
 */
@Slf4j
@Service
public class FlowInstanceNodeServiceImpl extends BaseServiceImpl<FlowInstanceNodeMapper, FlowInstanceNode> implements FlowInstanceNodeService {

    @Autowired
    private FlowModuleService flowModuleService;
    @Autowired
    private FlowNodeUserService flowNodeUserService;
    @Autowired
    private FlowInstanceService flowInstanceService;


    @Override
    public void saveInstanceNode(FlowInstance flowInstance, int parentNodeId, FlowDefinitionNode flowDefinitionNode) {
        List<FlowNodeUser> nodeList = new ArrayList<>();
        if(FlowDefinitionNodeTypeEnum.AUDIO.getValue() == flowDefinitionNode.getType().intValue() ){
            //审批人
            List<FlowNodeUser> list = flowNodeUserService.getByNodeId(flowDefinitionNode.getId());
            nodeList.addAll(list);
        }
        if(FlowDefinitionNodeTypeEnum.CONDITION.getValue() == flowDefinitionNode.getType().intValue()){
            //条件指定人
            List<FlowNodeUser> list = flowNodeUserService.getByNodeId(flowDefinitionNode.getId());
            //todo
            if(flowDefinitionNode.getExamineMode() == 1) {
                //审批人会签
                nodeList.addAll(list);
            }else{
                //审批人或签
                nodeList.add(list.get(0));
            }
        }
        if(FlowDefinitionNodeTypeEnum.COPY.getValue() == flowDefinitionNode.getType().intValue()){
            //抄送人
            List<FlowNodeUser> list = flowNodeUserService.getByNodeId(flowDefinitionNode.getId());
            for(FlowNodeUser u : list){
                u.setActionStatus(NodeStatusEnum.AGREE.getStatus());
            }
            nodeList.addAll(list);
        }
        int nodeId = parentNodeId + 1000;
        List<FlowInstanceNode> instanceNodeList = new ArrayList<>(nodeList.size());
        for (FlowNodeUser user : nodeList) {
            FlowInstanceNode instanceNode = new FlowInstanceNode();
            instanceNode.setUserId(user.getTargetId());
            instanceNode.setUserName(user.getName());
            instanceNode.setFullName(user.getName());
            instanceNode.setTaskCode(RandomUtil.randomString(20));
            instanceNode.setNodeId(nodeId);
            instanceNode.setNodeParentId(parentNodeId);
            instanceNode.setCompanyId(flowInstance.getCompanyId());
            instanceNode.setInstanceId(flowInstance.getId());
            instanceNode.setInstanceCode(flowInstance.getInstanceCode());
            instanceNode.setDefNodeCode(flowDefinitionNode.getNodeCode());
            instanceNode.setDefNodeId(flowDefinitionNode.getId());
            if(user.getActionStatus() != null ){
                instanceNode.setActionStatus(user.getActionStatus());
                instanceNode.setActionTime(new Date());
                if(user.getUserType() == 2) {
                    instanceNode.setNote("抄送");

                    //sendNotify(user.getTargetId(),flowInstance.getCompanyId(),flowInstance.getTitle(),0);
//                    //通知 抄送人
//                    messageSendService.approvalSendMsgNotify(BusinessCodeEnum.MP_APPROVAL_REMIND.getCode(),
//                            flowInstance.getCompanyId(),instanceNode,instanceNode.getUserId(),DictTypeConstant.message_push_object_3);
                }
            }else {
               // sendNotify(user.getTargetId(),flowInstance.getCompanyId(),flowInstance.getTitle(),1);
                instanceNode.setActionStatus(NodeStatusEnum.WAIT.getStatus());
                //通知 审批人
//                messageSendService.approvalSendMsgNotify(BusinessCodeEnum.MP_APPROVAL_REMIND.getCode(),
//                        flowInstance.getCompanyId(),instanceNode,instanceNode.getUserId(),DictTypeConstant.message_push_object_2);
            }
            instanceNodeList.add(instanceNode);
        }
        if(instanceNodeList.size() > 0) {
            saveBatch(instanceNodeList, instanceNodeList.size());
        }
    }

    @Override
    public void saveInstanceNode(FlowInstance flowInstance, FlowDefinitionNode flowDefinitionNode) {
        // 各节点对应的审批人
        List<FlowNodeUser> nodeList = new ArrayList<>();
        //审批人
        List<FlowNodeUser> list = flowNodeUserService.getByNodeId(flowDefinitionNode.getId(),flowDefinitionNode.getType());
        // 审批节点的逻辑处理
        if(FlowDefinitionNodeTypeEnum.AUDIO.getValue() == flowDefinitionNode.getType().intValue() ) {
            nodeList.addAll(list);
        }
        log.info("节点中的审批人："+JSON.toJSONString(nodeList));
        log.info("当前节点信息："+JSON.toJSONString(flowDefinitionNode));
        if(Objects.nonNull(flowDefinitionNode.getSettype()) && flowDefinitionNode.getSettype().equals(2)){
            List<FlowNodeUser> newUserList = new ArrayList<>();
            List<FlowNodeUser> oldUserList = new ArrayList<>();
            nodeList.clear();
            oldUserList.addAll(list);
            log.info("oldUserList.size："+oldUserList.size());
            for (FlowNodeUser user : oldUserList){
                if(user.getType().equals(2)){
                  /*  List<DepartmentUser> userList =
                            departmentUserService
                                    .list(new QueryWrapper<DepartmentUser>()
                                            .lambda()
                                            .eq(DepartmentUser::getCompanyId,user.getCompanyId())
                                            .eq(DepartmentUser::getDepartmentId,user.getTargetId())
                                            .eq(DepartmentUser::getState,1)
                                            .isNotNull(DepartmentUser::getUserId));
                    userList.forEach(v->{
                        FlowNodeUser user1 = MapperUtils.map(user,FlowNodeUser.class);
                        user1.setTargetId(v.getUserId());
                        user1.setName(userService.getById(v.getUserId()).getUsername());
                        newUserList.add(user1);
                    });*/
                }else{
                    nodeList.add(user);
                }
            }
            nodeList.addAll(newUserList);
        }
        // 去重
        if(Objects.nonNull(nodeList) && nodeList.size()>0) {
            nodeList = nodeList.stream().filter(distinctByKey(b -> b.getTargetId())).collect(Collectors.toList());
        }
        // 抄送
        if(FlowDefinitionNodeTypeEnum.COPY.getValue() == flowDefinitionNode.getType().intValue()){
            for(FlowNodeUser u : list){
                u.setActionStatus(NodeStatusEnum.AGREE.getStatus());
            }
            nodeList.addAll(list);
        }
        //保存待审批记录
        log.info("节点中实际审批人："+JSON.toJSONString(nodeList));
        //保存待审批记录---new
        addFlowInstanceNode(nodeList, flowInstance, flowDefinitionNode);
    }


    @Override
    public FlowInstanceNode getByTaskCode(String code) {
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<FlowInstanceNode>().eq(FlowInstanceNode::getCode, code);
        return getOne(queryWrapper);
    }

    @Override
    public FlowInstanceNode getByDefinitionId(int definitionId) {
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<FlowInstanceNode>().eq(FlowInstanceNode::getDefNodeId, definitionId);
        return getOne(queryWrapper);
    }

    @Override
    public List<FlowInstanceNode> getUnAudit(int definitionId,Integer instancdId) {
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<FlowInstanceNode>()
                .eq(FlowInstanceNode::getDefNodeId, definitionId)
                .eq(FlowInstanceNode::getInstanceId,instancdId)
                .eq(FlowInstanceNode::getActionStatus,0)
                .orderByAsc(FlowInstanceNode::getId);
        return list(queryWrapper);
    }

    @Override
    public FlowInstanceNode saveFlowInstanceNode(FlowInstance flowInstance, FlowDefinitionNode flowDefinitionNode){
        FlowInstanceNode instanceErrNode = new FlowInstanceNode();
//            instanceErrNode.setUserId(user.getTargetId());
//            instanceErrNode.setUserName(user.getName());
//            instanceErrNode.setFullName(user.getName());
        instanceErrNode.setTaskCode(RandomUtil.randomString(20));
        instanceErrNode.setNodeId(flowDefinitionNode.getId());
        instanceErrNode.setNodeParentId(flowDefinitionNode.getParentId());
        instanceErrNode.setCompanyId(flowInstance.getCompanyId());
        instanceErrNode.setInstanceId(flowInstance.getId());
        instanceErrNode.setInstanceCode(flowInstance.getInstanceCode());
        instanceErrNode.setDefNodeCode(flowDefinitionNode.getNodeCode());
        instanceErrNode.setDefNodeId(flowDefinitionNode.getId());
        instanceErrNode.setActionStatus(NodeStatusEnum.DISAGREE.getStatus());
        instanceErrNode.setNote("未找到符合的条件节点，流程异常中止");
        instanceErrNode.setActionTime(new Date());
        save(instanceErrNode);
        return instanceErrNode;
    }





    private void addFlowInstanceNode(List<FlowNodeUser> nodeList,FlowInstance flowInstance, FlowDefinitionNode flowDefinitionNode){
        List<FlowInstanceNode> instanceNodeList = new ArrayList<>(nodeList.size());
        for (FlowNodeUser user : nodeList) {
            FlowInstanceNode instanceNode = new FlowInstanceNode();
            instanceNode.setUserId(user.getTargetId());
            instanceNode.setUserName(user.getName());
            instanceNode.setFullName(user.getName());
            instanceNode.setTaskCode(RandomUtil.randomString(20));
            instanceNode.setNodeId(flowDefinitionNode.getId());
            instanceNode.setNodeParentId(flowDefinitionNode.getParentId());
            instanceNode.setCompanyId(flowInstance.getCompanyId());
            instanceNode.setInstanceId(flowInstance.getId());
            instanceNode.setInstanceCode(flowInstance.getInstanceCode());
            instanceNode.setDefNodeCode(flowDefinitionNode.getNodeCode());
            instanceNode.setDefNodeId(flowDefinitionNode.getId());
            if(user.getActionStatus() != null ){
                instanceNode.setActionStatus(user.getActionStatus());
                instanceNode.setActionTime(new Date());
                if(user.getUserType() == 2) {
                    instanceNode.setNote("抄送");
                    //通知 抄送人
                    /*List<DepartmentUser> departmentUsers =
                            departmentUserService.list(new QueryWrapper<DepartmentUser>().lambda().eq(DepartmentUser::getCompanyId,flowInstance.getCompanyId()).eq(DepartmentUser::getUserId,user.getTargetId()));
                    if(Objects.nonNull(departmentUsers)&&departmentUsers.size()>0) {
                        log.info("抄送人实例【"+flowInstance.getCode()+"】，发送给抄送人："+user.getTargetId());
                        messageSendService.approvalSendMsgNotify(BusinessCodeEnum.MP_APPROVAL_REMIND.getCode(),
                                flowInstance.getCompanyId(),instanceNode,user.getTargetId(),
                                DictTypeConstant.message_push_object_3,flowInstance);
                    }*/
                }
            }else {
//                sendNotify(user.getTargetId(),flowInstance.getCompanyId(),flowInstance.getTitle(),1);
                //判断当前节点的审批人状态，是否离职，如果离职则
/*                List<DepartmentUser> departmentUsers =
                        departmentUserService.list(new QueryWrapper<DepartmentUser>().lambda()
                                .eq(DepartmentUser::getCompanyId,flowInstance.getCompanyId())
                                .eq(DepartmentUser::getUserId,user.getTargetId()));*/
                // 如果员工存在，则是待审批流程继续
               /* if(Objects.nonNull(departmentUsers)&&departmentUsers.size()>0) {
                    instanceNode.setActionStatus(NodeStatusEnum.WAIT.getStatus());
                    //通知 审批人
                    log.info("审批实例【"+flowInstance.getCode()+"】，通知审批人："+user.getTargetId());
                   *//* messageSendService.approvalSendMsgNotify(BusinessCodeEnum.MP_APPROVAL_REMIND.getCode(),
                            flowInstance.getCompanyId(),instanceNode,user.getTargetId(),
                            DictTypeConstant.message_push_object_2,flowInstance);*//*
                }else{
                    // 设置当前节点为审批拒绝，并说清楚拒绝原因
                    User userDimission = userService.getById(user.getTargetId());
                    instanceNode.setActionStatus(NodeStatusEnum.UNTREATED.getStatus());
                    instanceNode.setActionTime(new Date());
                    instanceNode.setNote("员工【"+userDimission.getUsername()+"】已离职，当前节点结束");
                }*/
            }
            instanceNodeList.add(instanceNode);
        }
        if(instanceNodeList.size() > 0) {
            Long num = instanceNodeList.stream().filter( v-> v.getActionStatus().equals(NodeStatusEnum.UNTREATED.getStatus())).count();
            // 如果是会签节点，则只要有一个是未处理的，则流程结束
            if(Objects.nonNull(flowDefinitionNode.getExamineMode())&&flowDefinitionNode.getExamineMode().equals(2)){
                if(num>0){
                    // 通知对应模块，流程终止 其它节点全部设置为未处理，员工已离职
                    instanceNodeList.forEach(v->{
                        if(v.getActionStatus().equals(NodeStatusEnum.WAIT.getStatus())) {
                            v.setActionStatus(NodeStatusEnum.UNTREATED.getStatus());
                            v.setActionTime(new Date());
                            v.setNote("其它节点有离职员工，当前节点结束");
                            return;
                        }
                    });
                    // 通知对应模块，流程终止
                    flowInstance.setStatus(InstanceStatusEnum.TERMINATED.getStatus());
                    flowInstanceService.updateById(flowInstance);
                    FlowModule parentModule = flowModuleService.getParentModuleByChildId(flowInstance.getModuleId());
                    FlowModuleEnum flowModuleEnum = FlowModuleEnum.getById(parentModule.getModuleCode());
                    FlowBaseService flowBaseService = SpringContextHolder.getBean(flowModuleEnum.getServiceClass());
                    flowBaseService.flowTerminated(flowInstance.getBId(), flowInstance.getInstanceCode());
                    log.info("会签流程，有审批人状态异常，流程结束");
                    //通知发起人待审批任务xxx已审批通过
                    /*messageSendService.approvalSendMsgNotify(BusinessCodeEnum.MP_APPROVAL_RESULT.getCode(),
                            flowInstance.getCompanyId(),instanceNodeList.get(0),flowInstance.getUserId(),
                            DictTypeConstant.message_push_object_1,flowInstance);*/

                }
            }else{
                // 如果未处理的节点数等于集合的总数，则流程结束
                int size = instanceNodeList.size();
                Long lSize = Long.valueOf(String.valueOf(size));
                if(lSize.equals(num)){
                    // 通知对应模块，流程终止
                    flowInstance.setStatus(InstanceStatusEnum.TERMINATED.getStatus());
                    flowInstanceService.updateById(flowInstance);
                    FlowModule parentModule = flowModuleService.getParentModuleByChildId(flowInstance.getModuleId());
                    FlowModuleEnum flowModuleEnum = FlowModuleEnum.getById(parentModule.getModuleCode());
                    FlowBaseService flowBaseService = SpringContextHolder.getBean(flowModuleEnum.getServiceClass());
                    flowBaseService.flowTerminated(flowInstance.getBId(), flowInstance.getInstanceCode());
                    log.info("没有对应的审批人，流程结束");
                    //通知发起人待审批任务xxx已审批通过
                   /* messageSendService.approvalSendMsgNotify(BusinessCodeEnum.MP_APPROVAL_RESULT.getCode(),
                            flowInstance.getCompanyId(),instanceNodeList.get(0),flowInstance.getUserId(),DictTypeConstant.message_push_object_1
                            ,flowInstance);*/
                }
            }
        }
        // 保存当前节点
        saveBatch(instanceNodeList, instanceNodeList.size());
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
