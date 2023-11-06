package org.apache.alan.wrokflow.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.alan.wrokflow.dao.mapper.FlowInstanceMapper;
import org.apache.alan.wrokflow.dao.mapper.FlowInstanceNodeMapper;
import org.apache.alan.wrokflow.dao.po.*;
import org.apache.alan.wrokflow.dto.*;
import org.apache.alan.wrokflow.enums.*;
import org.apache.alan.wrokflow.exception.ApplicationException;
import org.apache.alan.wrokflow.service.*;
import org.apache.alan.wrokflow.utils.MapperUtils;
import org.apache.alan.wrokflow.utils.SpringContextHolder;
import org.apache.alan.wrokflow.utils.StringUtils;
import org.apache.alan.wrokflow.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-07 18:47
 */
@Slf4j
@Service
public class FlowInstanceServiceImpl extends BaseServiceImpl<FlowInstanceMapper, FlowInstance> implements FlowInstanceService {

    @Autowired
    private FlowModuleService flowModuleService;
    @Autowired
    private FlowDefinitionService flowDefinitionService;
    @Autowired
    private FlowDefinitionNodeService flowDefinitionNodeService;
    @Autowired
    private FlowInstanceNodeService flowInstanceNodeService;
//    @Autowired
//    private FlowInstanceService flowInstanceService;
    @Autowired
    private FlowMyModuleService flowMyModuleService;
    @Autowired
    private FlowInstanceNodeMapper flowInstanceNodeMapper;
    @Autowired
    private FlowNodeUserService flowNodeUserService;
    @Autowired
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startEngine(String moduleCode,Integer bId, Authentication auth) {
        Integer userId = auth.getUserId();
        Integer companyId = auth.getCompanyId();
        Integer roleId = auth.getRoleId();
        Integer userType = auth.getUserType();
        //审批模块 子模块
        FlowModule flowModule = flowModuleService.getByCode(moduleCode);

        //审批流没开启， 不走审批，直接通知对应模块，流程结束
        if(flowModule == null) {
            finishFlow(FinishFlowEnum.OFF, bId, flowModule.getId(), flowModule.getModuleCode());
        }

        //Assert.notNull(flowModule);

        FlowDefinitionGetByUidAndMIdDto getFlowDefDto = new FlowDefinitionGetByUidAndMIdDto();
        FlowDefinition flowDefinition = null;
        //TODO 业务员
        if(UserTypeEnum.SALE.getCode() == userType){
           // userId = companyService.getById(companyId).getAdminId();
            flowDefinition.setName(auth.getUserName() + flowDefinition.getName());
        }
        getFlowDefDto.setModuleId(flowModule.getId());
        getFlowDefDto.setUserId(userId);
        flowDefinition =  flowDefinitionService.getByModuleIdAndUserId(getFlowDefDto);

        // 检查流程定义
        if(flowDefinition == null){
            //流程不存在，不走审批，直接告知业务层
            finishFlow(FinishFlowEnum.NO,bId,flowModule.getId(),flowModule.getModuleCode());
            return;
        }
        if(!flowDefinition.getStatus()) {
            //流程未开启，不走审批，直接告知业务层
            finishFlow(FinishFlowEnum.OFF,bId,flowModule.getId(),flowModule.getModuleCode());
        }else{
            // 生成流程实例
            FlowInstance flowInstance = saveInstance(flowDefinition, bId, userId,auth.getCompanyId());
            // 一个节点一个节点的走，获取下一个节点定义
            FlowDefinitionNode flowDefinitionNode = flowDefinitionNodeService.getNextDefinitionCode(flowDefinition.getId(),userId,0,0,0);
            if(flowDefinitionNode == null){
                finishFlow(FinishFlowEnum.OFF,bId,flowModule.getId(),flowModule.getModuleCode());
            }else {
                // 生成流程节点
                flowInstanceNodeService.saveInstanceNode(flowInstance, 0, flowDefinitionNode);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean startEngine(FlowEngineDto dto){
        log.info("开始执行审批，请求参数："+JSON.toJSONString(dto));
        //审批模块 子模块
        FlowModule flowModule = flowModuleService.getByCode(dto.getModuleCode());
        //审批流没开启， 不走审批，直接通知对应模块，流程结束
       if(Objects.nonNull(flowModule)){
           List<FlowMyModule> myModule =
                   flowMyModuleService.list(new QueryWrapper<FlowMyModule>().lambda().eq(FlowMyModule::getCompanyId,
                           dto.getCompanyId()).eq(FlowMyModule::getModuleId,flowModule.getId()).eq(FlowMyModule::getOpen,1).eq(FlowMyModule::getFlowSet,2));
           // 业务功能存一条审批流程有开启且是已发布的模板，当有多条记录时则不执行审批逻辑
           if(Objects.nonNull(myModule)&&myModule.size()==1){
               FlowDefinitionGetByUidAndMIdDto getFlowDefDto = new FlowDefinitionGetByUidAndMIdDto();
               getFlowDefDto.setCompanyId(dto.getCompanyId());
               getFlowDefDto.setModuleId(flowModule.getId());
               getFlowDefDto.setDefinitionId(myModule.get(0).getDefinitionId());
               FlowDefinition flowDefinition =  flowDefinitionService.getByModuleIdAndUserId(getFlowDefDto);
               if(Objects.nonNull(flowDefinition) && flowDefinition.getStatus()){
                   // 生成流程实例
                   FlowInstance flowInstance = saveInstance(flowDefinition, dto);
                   log.info("流程实例："+JSON.toJSONString(flowInstance));
                   // 一个节点一个节点的走，获取下一个节点定义
                   FlowDefinitionNode flowDefinitionNode =
                           flowDefinitionNodeService.getNextDefinitionCode(flowDefinition.getId(),0,0,0,flowInstance);
                   //如果第一个节点是抄送人，则继续找下一个节点的记录
                   if(Objects.nonNull(flowDefinitionNode) && flowDefinitionNode.getType().equals(2)){
                       flowInstanceNodeService.saveInstanceNode(flowInstance, flowDefinitionNode);// 保存抄送节点，
                       // 并继续找下一个节点的记录
                       flowDefinitionNode =
                               flowDefinitionNodeService.getNextDefinitionCode(flowDefinition.getId(),
                                       flowDefinitionNode.getId(),-1,0,flowInstance);
                   }
                   if(Objects.nonNull(flowDefinitionNode) && flowDefinitionNode.getType().equals(1)) {
                       flowInstanceNodeService.saveInstanceNode(flowInstance, flowDefinitionNode);
                   }
                   return true;
               }
           }
       }
       return false;
    }

    @Override
    public PageVo<FlowInstancePageVo> listPage(FlowInstancePageDto dto) {
        if (StringUtils.isNotEmpty(dto.getCode())){
            FlowMyModule flowMyModule = flowMyModuleService.getOne(new QueryWrapper<FlowMyModule>().lambda()
                    .eq(FlowMyModule::getCode,dto.getCode()));
            dto.setModuleId(flowMyModule.getModuleId());
        }
        PageVo<FlowInstancePageVo> pageVo = new PageVo<>();
        //我发起的
        if (dto.getStatus() == 3){
            pageVo = pagingQuery(dto, flowInstanceNodeMapper::getMyTaskPageNew);
        }else {
            pageVo = pagingQuery(dto, flowInstanceNodeMapper::getMyTaskPage);
        }
        if (Objects.nonNull(pageVo) && pageVo.getList().size() != 0){
            pageVo.getList().forEach(v->{
                FlowModule flowModule = flowModuleService.getByModuleId(v.getModuleId());
                if (Objects.nonNull(flowModule)){
                    FlowModule flowModule1 = flowModuleService.getByModuleId(flowModule.getParentId());
                    if (Objects.nonNull(flowModule1)){
                        v.setBusinessName(flowModule1.getName());
                    }
                }
            });
        }
        return pageVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDefinitionId(String definitCode,int definitionId, int id) {
        UpdateWrapper<FlowInstance> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(FlowInstance.COL_DEFINITION_CODE,definitCode);
        updateWrapper.set(FlowInstance.COL_DEFINITION_CODE,definitionId);
        updateWrapper.eq(FlowInstance.COL_ID,id);
        update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByDefinitionId(int definitionId){
        UpdateWrapper<FlowInstance> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(FlowInstance.COL_DEFINITION_ID,definitionId);
        remove(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(int id){
        UpdateWrapper<FlowInstance> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(FlowInstance.COL_DEFINITION_CODE,id);
        remove(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FlowInstanceUpdateDto dto, int userId) {
        FlowInstance flowInstance = getById(dto.getId());
        //Assert.notNull(flowInstance);
        flowInstance =  MapperUtils.map(dto,FlowInstance.class);
        updateById(flowInstance);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agree(String code, String note, Authentication auth) {
        // 1、检查当前节点
        FlowInstanceNode instanceNode = getWaitNode(code);
        if(null != instanceNode.getActionStatus() && instanceNode.getActionStatus() != NodeStatusEnum.WAIT.getStatus()){
            throw new ApplicationException("该节点已审批");
        }
        // 2、检查流程实例
        FlowInstance instance = getInProgressInstance(instanceNode.getInstanceCode());
        // 更新当前节点
        instanceNode.setActionStatus(NodeStatusEnum.AGREE.getStatus());
        instanceNode.setActionTime(new Date());
        instanceNode.setNote(note);
        flowInstanceNodeService.updateById(instanceNode);
        //3、通知发起人待审批任务xxx已审批通过
        //sendNotify(instance,NotifyEnum.FLOW_PC_AUDIT_AGREE,auth.getUserName());
        // 4、操作同根，检查平级的节点是否都审批完成
        //      4.1：通过当前流程的上级ID查找审批中的记录
        //通知发起人待审批任务xxx已审批通过  DictTypeConstant.approval_inform
        log.info("审批实例【"+instance.getCode()+"】结果通知发起人："+instance.getUserId());
/*        messageSendService.approvalSendMsgNotify(BusinessCodeEnum.MP_APPROVAL_RESULT.getCode(),
                instance.getCompanyId(),instanceNode,
                instance.getUserId(),
                DictTypeConstant.message_push_object_1,instance);*/
        // 操作同根，检查平级的节点是否都审批完成
        // 通过当前流程的上级ID查找审批中的记录
        FlowDefinitionNode nextFlowDefinitionNode = null;
        List<FlowInstanceNode> flowInstanceNodeList = flowInstanceNodeService.getUnAudit(instanceNode.getDefNodeId(),instance.getId());
        log.info("平级未审批的记录信息："+JSON.toJSONString(flowInstanceNodeList));
        // 5、或签  -- 只要其中一个人通过则通过否则需要全部审批人通过
        FlowDefinitionNode flowDefinitionNode = flowDefinitionNodeService.getById(instanceNode.getDefNodeId());
        if(flowDefinitionNode.getExamineMode().equals(1)&&flowInstanceNodeList.size()>0){
            // 将其它节点设置为已通过
            for (FlowInstanceNode node:flowInstanceNodeList){
                node.setActionStatus(NodeStatusEnum.UNTREATED.getStatus());
                node.setActionTime(new Date());
                StringBuffer sb = new StringBuffer();
                sb.append("审批人【" + auth.getUserName() + "】已通过");
                if(Objects.nonNull(note)){
                    sb.append("，通过原因【" + note + "】");
                }
                node.setNote(sb.toString());
                flowInstanceNodeService.saveOrUpdate(node);
            }
            flowInstanceNodeList.clear();//清空当前集合，这个节点全部默认处理完成，继续执行后续的节点逻辑
        }
        // 如果同级节点都处理完成，则继续下一节点处理
        if(Objects.isNull(flowInstanceNodeList) || flowInstanceNodeList.size()<=0){
            // 获取下一个节点定义
            nextFlowDefinitionNode =
                    flowDefinitionNodeService.getNextDefinitionCode(instance.getDefinitionId(),instanceNode.getDefNodeId(),-1,0,instance);
            // 如果是抄送节点，则保存之后继续寻找下一个审批节点，当没有下一个审批节点时则执行流程结束动作
            log.info("下一节点对象："+JSON.toJSONString(nextFlowDefinitionNode));
            if(Objects.nonNull(nextFlowDefinitionNode)&&nextFlowDefinitionNode.getType().equals(2)){
                flowInstanceNodeService.saveInstanceNode(instance, nextFlowDefinitionNode); //生成抄送节点数据记录
                // 根据抄送节点查到下一节点的记录
                nextFlowDefinitionNode =
                        flowDefinitionNodeService.getNextDefinitionCode(instance.getDefinitionId(),
                                nextFlowDefinitionNode.getId(),-1,0,instance);
            }
            if(nextFlowDefinitionNode != null) {
                // 生成下一个节点流程节点
                flowInstanceNodeService.saveInstanceNode(instance, nextFlowDefinitionNode);
            }
        }
        // 如果没有下一个审批节点，则审批流程结束。
        if(Objects.isNull(nextFlowDefinitionNode)){
            if(Objects.isNull(flowInstanceNodeList) || flowInstanceNodeList.size()<=0) {
                instance.setStatus(InstanceStatusEnum.FINISHED.getStatus());
                updateById(instance);
            }
        }
        log.info("nextFlowDefinitionNode:"+nextFlowDefinitionNode);
        // 条件节点找不到下级节点时则流程中止
        if(Objects.nonNull(nextFlowDefinitionNode)&&nextFlowDefinitionNode.getErrNode()){

            instance.setStatus(InstanceStatusEnum.TERMINATED.getStatus());
            updateById(instance);
            // 保存一条审批中止的记录
            FlowInstanceNode errFlowInstanceNode = flowInstanceNodeService.saveFlowInstanceNode(instance,
                    flowDefinitionNode);
            // 通知对应模块，流程终止
            FlowModule parentModule = flowModuleService.getParentModuleByChildId(instance.getModuleId());
            FlowModuleEnum flowModuleEnum = FlowModuleEnum.getById(parentModule.getModuleCode());
            FlowBaseService flowBaseService = SpringContextHolder.getBean(flowModuleEnum.getServiceClass());
            flowBaseService.flowTerminated(instance.getBId(), instance.getInstanceCode());

/*            messageSendService.approvalSendMsgNotify(BusinessCodeEnum.MP_APPROVAL_RESULT.getCode(),
                    instance.getCompanyId(),errFlowInstanceNode,instance.getUserId(),DictTypeConstant.message_push_object_1,instance);*/
        }
        // 当所有节点都审批完成之后，则更新审批实例状态为已完成
        if(instance.getStatus() == InstanceStatusEnum.FINISHED.getStatus()){
            FlowModule parentModule = flowModuleService.getParentModuleByChildId(instance.getModuleId());
            // 通知对应模块，流程结束
            FlowModuleEnum flowModuleEnum = FlowModuleEnum.getById(parentModule.getModuleCode());
            FlowBaseService flowBaseService = SpringContextHolder.getBean(flowModuleEnum.getServiceClass());
            //业务id
            flowBaseService.flowFinished(instance.getBId(),instance.getInstanceCode(),FinishFlowEnum.FINISHED);
        }
    }

//    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agreeOld(String code, String note, Authentication auth) {
        // 检查当前节点
        FlowInstanceNode instanceNode = getWaitNode(code);
        if(null != instanceNode.getActionStatus() && instanceNode.getActionStatus() != NodeStatusEnum.WAIT.getStatus()){
            throw new ApplicationException("该节点已审批");
        }
        // 检查流程实例
        FlowInstance instance = getInProgressInstance(instanceNode.getInstanceCode());
        // 更新当前节点
        instanceNode.setActionStatus(NodeStatusEnum.AGREE.getStatus());
        instanceNode.setActionTime(new Date());
        instanceNode.setNote(note);
        flowInstanceNodeService.updateById(instanceNode);

        //通知发起人待审批任务xxx已审批通过
        //sendNotify(instance,NotifyEnum.FLOW_PC_AUDIT_AGREE,auth.getUserName());
        // 操作同根，检查平级的节点是否都审批完成

        // 获取下一个节点定义 getNextDefinitionCode(int definitId,int parentId,int type,int instanceId, FlowInstance instance)
        FlowDefinitionNode nextFlowDefinitionNode =
                flowDefinitionNodeService.getNextDefinitionCode(instance.getDefinitionId(),instanceNode.getDefNodeId(),-1,0,instance);
        // 如果是抄送节点，则保存之后继续寻找下一个审批节点，当没有下一个审批节点时则执行流程结束动作
       if(Objects.nonNull(nextFlowDefinitionNode)&&nextFlowDefinitionNode.getType().equals(2)){
           flowInstanceNodeService.saveInstanceNode(instance, nextFlowDefinitionNode); //生成抄送节点数据记录
           // 根据抄送节点查到下一节点的记录
           nextFlowDefinitionNode =
                   flowDefinitionNodeService.getNextDefinitionCode(instance.getDefinitionId(),
                           nextFlowDefinitionNode.getId(),-1,0,instance);
       }
        if(nextFlowDefinitionNode != null) {
            // 生成下一个节点流程节点
//            flowInstanceNodeService.saveInstanceNode(instance, instanceNode.getNodeId(), nextFlowDefinitionNode);
            flowInstanceNodeService.saveInstanceNode(instance, nextFlowDefinitionNode);
//            if(nextFlowDefinitionNode.getLastNode()){
//                //最后一个节点
//                FlowInstanceNode lastInstanceNode = flowInstanceNodeService.getByDefinitionId(nextFlowDefinitionNode.getId());
//                if(lastInstanceNode != null && lastInstanceNode.getActionStatus() == NodeStatusEnum.AGREE.getStatus()){
//                    instance.setStatus(InstanceStatusEnum.FINISHED.getStatus()) ;
//                    updateById(instance);
//                }
//            }
        }else{
            //没有下一个节点时，操作同根
            FlowDefinitionNode sameRootDefinitionNode = flowDefinitionNodeService.findSameRootNode(instance.getDefinitionId(),instanceNode.getDefNodeId());
            if(sameRootDefinitionNode != null){
                if(sameRootDefinitionNode.getId() == instanceNode.getDefNodeId()){
                    //审批节点相同
                    List<FlowInstanceNode> flowInstanceNodeList =
                            flowInstanceNodeService.getUnAudit(instanceNode.getDefNodeId(),null);
                    if(flowInstanceNodeList.size() <= 0){
                        //最后的审批人 下一个节点
                        int parentId = flowDefinitionNodeService.getById(instanceNode.getDefNodeId()).getParentId();
                        sameRootDefinitionNode = flowDefinitionNodeService.getNextDefinitionCode(instance.getDefinitionId(),auth.getUserId(),parentId,-1,sameRootDefinitionNode.getId());
                        flowInstanceNodeService.saveInstanceNode(instance, instanceNode.getNodeId(), sameRootDefinitionNode);
                    }

                }else {
                    // 生成下一个节点流程节点
                    flowInstanceNodeService.saveInstanceNode(instance, instanceNode.getNodeId(), sameRootDefinitionNode);
                }
                if(sameRootDefinitionNode.getLastNode()){
                    //最后一个节点
                    FlowInstanceNode lastInstanceNode = flowInstanceNodeService.getByDefinitionId(sameRootDefinitionNode.getId());
                    if(lastInstanceNode != null && lastInstanceNode.getActionStatus() == NodeStatusEnum.AGREE.getStatus()){
                        instance.setStatus(InstanceStatusEnum.FINISHED.getStatus()) ;
                        updateById(instance);
                    }
                }
            }else {
                instance.setStatus(InstanceStatusEnum.FINISHED.getStatus());
                updateById(instance);
            }
        }

        if(instance.getStatus() == InstanceStatusEnum.FINISHED.getStatus()){
            FlowModule parentModule = flowModuleService.getParentModuleByChildId(instance.getModuleId());
            // 通知对应模块，流程结束
            FlowModuleEnum flowModuleEnum = FlowModuleEnum.getById(parentModule.getModuleCode());
            FlowBaseService flowBaseService = SpringContextHolder.getBean(flowModuleEnum.getServiceClass());
            //业务id
            flowBaseService.flowFinished(instance.getBId(),instance.getInstanceCode(),FinishFlowEnum.FINISHED);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disagree(String taskCode, String note, Authentication auth) {
        // 检查当前节点
        FlowInstanceNode instanceNode = getWaitNode(taskCode);
        log.info("【"+instanceNode.getUserId()+"】 != 【"+auth.getUserId()+"】"+(!instanceNode.getUserId().equals(auth.getUserId())));
        if(!instanceNode.getUserId().equals(auth.getUserId())){
            throw new ApplicationException("您无权审批此流程");
        }
        if(null != instanceNode.getActionStatus() && instanceNode.getActionStatus() != NodeStatusEnum.WAIT.getStatus()){
            throw new ApplicationException("该节点已审批");
        }
        // 检查流程实例
        FlowInstance instance = getInProgressInstance(instanceNode.getInstanceCode());
        // 更新当前节点
        instanceNode.setActionStatus(NodeStatusEnum.DISAGREE.getStatus());
        instanceNode.setActionTime(new Date());
        instanceNode.setNote(note);
        flowInstanceNodeService.updateById(instanceNode);
        // 处理会签逻辑，查询当前节点是会签还是或签
        FlowDefinitionNode flowDefinitionNode = flowDefinitionNodeService.getById(instanceNode.getDefNodeId());
        log.info("处理会签逻辑，当前节点信息："+JSON.toJSONString(flowDefinitionNode));
        // 会签 -- 只要一个人拒绝了整个流程结束
        boolean flag = false;
        if(flowDefinitionNode.getExamineMode().equals(2)){
            flag = true;
            // 其它节点默认为审批拒绝，操作同根
            List<FlowInstanceNode> flowInstanceNodeList =
                    flowInstanceNodeService.getUnAudit(instanceNode.getDefNodeId(),instance.getId());
            log.info("同级其它未审批节点："+JSON.toJSONString(flowInstanceNodeList));
            if(Objects.nonNull(flowInstanceNodeList)&&flowInstanceNodeList.size()>0){
                flowInstanceNodeList.forEach(v->{
                    if(!v.getId().equals(instanceNode.getId())) {
                        v.setActionStatus(NodeStatusEnum.UNTREATED.getStatus());
                        v.setActionTime(new Date());
                        StringBuffer sb = new StringBuffer();
                        sb.append("审批人【" + auth.getUserName() + "】已拒绝");
                        if(Objects.nonNull(note)){
                            sb.append("，拒绝原因【" + note + "】");
                        }
                        v.setNote(sb.toString());
                    }
                });
                flowInstanceNodeService.saveOrUpdateBatch(flowInstanceNodeList);
            }
        }
        // 或签 -- 有人拒绝则其它的流程继续
        if(flowDefinitionNode.getExamineMode().equals(1)){
            // 如果或签中只包含一个人，则流程结束，否则流程继续
            List<FlowNodeUser> nodeUsers = flowNodeUserService.getByNodeId(flowDefinitionNode.getId(),flowDefinitionNode.getType());
            log.info("流程中包含的审批信列表："+JSON.toJSONString(nodeUsers));
            if(Objects.nonNull(nodeUsers)){
                if(nodeUsers.size()>1){
                    // 如果审批人是多个且其它人已经全部拒绝时，流程结束
                    List<FlowInstanceNode> flowInstanceNodeList =
                            flowInstanceNodeService.getUnAudit(instanceNode.getDefNodeId(),instance.getId());
                    List<Integer> strats = flowInstanceNodeList.stream().map(FlowInstanceNode::getActionStatus).collect(Collectors.toList());
                    if(strats.indexOf(NodeStatusEnum.WAIT.getStatus())>-1){
                        flag = false;
                    }else{
                        flag = true;
                    }
                }else{
                    flag = true;
                }
            }
        }
        log.info("最终flag:"+flag);
        if(flag) {
            // 通知对应模块，流程终止
            instance.setStatus(InstanceStatusEnum.TERMINATED.getStatus());
            updateById(instance);
            FlowModule parentModule = flowModuleService.getParentModuleByChildId(instance.getModuleId());
            FlowModuleEnum flowModuleEnum = FlowModuleEnum.getById(parentModule.getModuleCode());
            FlowBaseService flowBaseService = SpringContextHolder.getBean(flowModuleEnum.getServiceClass());
            flowBaseService.flowTerminated(instance.getBId(), instance.getInstanceCode());
        }
        //通知发起人待审批任务xxx已审批通过
//        sendNotify(instance, NotifyEnum.FLOW_PC_AUDIT_REFUSE, auth.getUserName());
       /* messageSendService.approvalSendMsgNotify(BusinessCodeEnum.MP_APPROVAL_RESULT.getCode(),
                instance.getCompanyId(),instanceNode,instance.getUserId(),DictTypeConstant.message_push_object_1,instance);*/
    }

    @Override
    public void changeNode(String taskCode, String username, int userId) {
        FlowInstanceNode instanceNode = flowInstanceNodeService.getByTaskCode(taskCode);
        instanceNode.setUserName(username);
        instanceNode.setUserId(userId);
        instanceNode.setFullName(username);
        flowInstanceNodeService.updateById(instanceNode);
    }

    @Override
    public List<FlowTaskDetailVo> details(String instanceCode, Authentication auth) {
        List<FlowTaskDetailVo> voList = new ArrayList<>();
        FlowInstance flowInstance = getOne(new QueryWrapper<FlowInstance>().lambda()
                .eq(FlowInstance::getCode,instanceCode));
        if (Objects.isNull(flowInstance)){
            return voList;
        }
        FlowTaskDetailVo vo = new FlowTaskDetailVo();
        vo.setInstanceCreateTime(flowInstance.getCreatedTime());
        vo.setType(0);
        FlowTaskUserDetailVo userDetailVo = new FlowTaskUserDetailVo();
        List<FlowTaskUserDetailVo> userDetailVoList = new ArrayList<>();
        userDetailVo.setUserName(flowInstance.getUserName());
        userDetailVo.setNote(flowInstance.getModuleName());
        userDetailVoList.add(userDetailVo);
        vo.setFlowTaskUserDetailVos(userDetailVoList);
        voList.add(0,vo);
        //根据业务审批记录查询审批节点
        List<FlowInstanceNode> flowInstanceNodeLis = flowInstanceNodeService.list(new QueryWrapper<FlowInstanceNode>().lambda()
                .eq(FlowInstanceNode::getInstanceId,flowInstance.getId())
                .orderByAsc(FlowInstanceNode::getId));
        //根据nodeId去重,得到所有审批的节点
        List<Integer> nodeIds = flowInstanceNodeLis.stream().map(FlowInstanceNode::getNodeId).collect(Collectors.toList());
        List<Integer> distinctNodeIds = nodeIds.stream().distinct().collect(Collectors.toList());
        log.info("=====distinctNodeIds===="+ JSON.toJSONString(distinctNodeIds));
        for (int i = 0; i < distinctNodeIds.size(); i++) {
            FlowDefinitionNode flowDefinitionNode = flowDefinitionNodeService.findById(distinctNodeIds.get(i));
            FlowTaskDetailVo vo1 = new FlowTaskDetailVo();
            vo1.setType(flowDefinitionNode.getType());
            vo1.setExamineMode(flowDefinitionNode.getExamineMode());
            vo1.setNodeId(distinctNodeIds.get(i));
            voList.add(vo1);
        }
        log.info("=======voList======"+JSON.toJSONString(voList));
        for (int i = 0; i < voList.size(); i++) {
            if (voList.get(i).getType() != 0){
                List<FlowInstanceNode> flowInstanceNodeLisNew = flowInstanceNodeService.list(new QueryWrapper<FlowInstanceNode>().lambda()
                        .eq(FlowInstanceNode::getInstanceId,flowInstance.getId())
                        .eq(FlowInstanceNode::getNodeId,voList.get(i).getNodeId())
                        .orderByAsc(FlowInstanceNode::getId));
                voList.get(i).setInstanceCreateTime(flowInstanceNodeLisNew.get(0).getCreatedTime());
                List<FlowTaskUserDetailVo> flowTaskUserDetailVos = MapperUtils.mapList(flowInstanceNodeLisNew,FlowTaskUserDetailVo.class);
                voList.get(i).setFlowTaskUserDetailVos(flowTaskUserDetailVos);
            }
        }
        voList.forEach(v->{
            //如果节点是发起人
            if (v.getType() == 0){
                v.setStatus(1);
                //如果节点是审批
            }else if (v.getType() == 1){
                //或签
                if (v.getExamineMode() == 1){
                    List<Integer> actionStatus = v.getFlowTaskUserDetailVos().stream().map(FlowTaskUserDetailVo::getActionStatus).collect(Collectors.toList());
                    if (actionStatus.contains(1)){
                        v.setStatus(1);
                    }else {
                        List<FlowTaskUserDetailVo> juJueList = new ArrayList<>();
                        v.getFlowTaskUserDetailVos().forEach(u->{
                            if (u.getActionStatus() != null && u.getActionStatus() == 2){
                                juJueList.add(u);
                            }
                        });
                        if (juJueList.size() == v.getFlowTaskUserDetailVos().size()){
                            v.setStatus(2);
                        }else {
                            v.setStatus(0);
                        }
                    }
                }
                //会签
                if (v.getExamineMode() == 2){
                    List<Integer> actionStatus = v.getFlowTaskUserDetailVos().stream().map(FlowTaskUserDetailVo::getActionStatus).collect(Collectors.toList());
                    if (actionStatus.contains(2)){
                        v.setStatus(2);
                    }else {
                        List<FlowTaskUserDetailVo> tongyiList = new ArrayList<>();
                        v.getFlowTaskUserDetailVos().forEach(u->{
                            if (u.getActionStatus() != null && u.getActionStatus() == 1){
                                tongyiList.add(u);
                            }
                        });
                        if (tongyiList.size() == v.getFlowTaskUserDetailVos().size()){
                            v.setStatus(1);
                        }else {
                            v.setStatus(0);
                        }
                    }
                }
            }else if (v.getType() == 2){
                v.setStatus(1);
            }else {
                v.setStatus(1);
            }
        });
        return voList;
    }

    @Override
    public List<FlowTaskDetailVo> getDetailsByBid(Integer bid,String code) {
        List<FlowTaskDetailVo> voList = new ArrayList<>();
        FlowInstance flowInstance = getOne(new QueryWrapper<FlowInstance>().lambda()
                .eq(FlowInstance::getBId,bid).eq(FlowInstance::getBCode,code));
        if (Objects.isNull(flowInstance)){
            return voList;
        }
        FlowTaskDetailVo vo = new FlowTaskDetailVo();
        vo.setInstanceCreateTime(flowInstance.getCreatedTime());
        vo.setType(0);
        FlowTaskUserDetailVo userDetailVo = new FlowTaskUserDetailVo();
        List<FlowTaskUserDetailVo> userDetailVoList = new ArrayList<>();
        userDetailVo.setUserName(flowInstance.getUserName());
        userDetailVo.setNote(flowInstance.getModuleName());
        userDetailVoList.add(userDetailVo);
        vo.setFlowTaskUserDetailVos(userDetailVoList);
        voList.add(0,vo);
        //根据业务审批记录查询审批节点
        List<FlowInstanceNode> flowInstanceNodeLis = flowInstanceNodeService.list(new QueryWrapper<FlowInstanceNode>().lambda()
                .eq(FlowInstanceNode::getInstanceId,flowInstance.getId())
                .orderByAsc(FlowInstanceNode::getId));
        //根据nodeId去重,得到所有审批的节点
        List<Integer> nodeIds = flowInstanceNodeLis.stream().map(FlowInstanceNode::getNodeId).collect(Collectors.toList());
        List<Integer> distinctNodeIds = nodeIds.stream().distinct().collect(Collectors.toList());
        log.info("=====distinctNodeIds===="+ JSON.toJSONString(distinctNodeIds));
        for (int i = 0; i < distinctNodeIds.size(); i++) {
            FlowDefinitionNode flowDefinitionNode = flowDefinitionNodeService.findById(distinctNodeIds.get(i));
            FlowTaskDetailVo vo1 = new FlowTaskDetailVo();
            vo1.setInstanceCreateTime(flowDefinitionNode.getCreatedTime());
            vo1.setType(flowDefinitionNode.getType());
            vo1.setExamineMode(flowDefinitionNode.getExamineMode());
            vo1.setNodeId(distinctNodeIds.get(i));
            voList.add(vo1);
        }
        log.info("=======voList======"+JSON.toJSONString(voList));
        for (int i = 0; i < voList.size(); i++) {
            if (voList.get(i).getType() != 0){
                List<FlowInstanceNode> flowInstanceNodeLisNew = flowInstanceNodeService.list(new QueryWrapper<FlowInstanceNode>().lambda()
                        .eq(FlowInstanceNode::getInstanceId,flowInstance.getId())
                        .eq(FlowInstanceNode::getNodeId,voList.get(i).getNodeId())
                        .orderByAsc(FlowInstanceNode::getId));
                voList.get(i).setInstanceCreateTime(flowInstanceNodeLisNew.get(0).getCreatedTime());
                List<FlowTaskUserDetailVo> flowTaskUserDetailVos = MapperUtils.mapList(flowInstanceNodeLisNew,FlowTaskUserDetailVo.class);

                voList.get(i).setFlowTaskUserDetailVos(flowTaskUserDetailVos);
            }
        }
        voList.forEach(v->{
            //如果节点是发起人
            if (v.getType() == 0){
                v.setStatus(1);
                //如果节点是审批
            }else if (v.getType() == 1){
                //或签
                if (v.getExamineMode() == 1){
                    List<Integer> actionStatus = v.getFlowTaskUserDetailVos().stream().map(FlowTaskUserDetailVo::getActionStatus).collect(Collectors.toList());
                    if (actionStatus.contains(1)){
                        v.setStatus(1);
                    }else {
                        List<FlowTaskUserDetailVo> juJueList = new ArrayList<>();
                        v.getFlowTaskUserDetailVos().forEach(u->{
                            if (u.getActionStatus() != null && u.getActionStatus() == 2){
                                juJueList.add(u);
                            }
                        });
                        if (juJueList.size() == v.getFlowTaskUserDetailVos().size()){
                            v.setStatus(2);
                        }else {
                            v.setStatus(0);
                        }
                    }
                }
                //会签
                if (v.getExamineMode() == 2){
                    List<Integer> actionStatus = v.getFlowTaskUserDetailVos().stream().map(FlowTaskUserDetailVo::getActionStatus).collect(Collectors.toList());
                    if (actionStatus.contains(2)){
                        v.setStatus(2);
                    }else {
                        List<FlowTaskUserDetailVo> tongyiList = new ArrayList<>();
                        v.getFlowTaskUserDetailVos().forEach(u->{
                            if (u.getActionStatus() != null && u.getActionStatus() == 1){
                                tongyiList.add(u);
                            }
                        });
                        if (tongyiList.size() == v.getFlowTaskUserDetailVos().size()){
                            v.setStatus(1);
                        }else {
                            v.setStatus(0);
                        }
                    }
                }
            }else if (v.getType() == 2){
                v.setStatus(1);
            }else {
                v.setStatus(1);
            }
        });
        return voList;
    }

    @Override
    public FlowBusinessDetailVo businessDetail(String instanceCode, Authentication auth) {
        FlowBusinessDetailVo vo = new FlowBusinessDetailVo();
        FlowInstance flowInstance = getOne(new QueryWrapper<FlowInstance>().lambda()
                .eq(FlowInstance::getCode,instanceCode));
        //Assert.notNull(flowInstance,ErrorCodes.NOT_FOUND,"未查询到对应业务审批记录");
        vo.setUserName(flowInstance.getUserName());
        vo.setCreateTime(flowInstance.getCreatedTime());
        vo.setContent(flowInstance.getContent());
        vo.setBusinessTypeName(flowInstance.getModuleName());
        return vo;
    }

    @Override
    public void batchApprove(int action,List<String> codes, String note, Authentication auth) {
        if(Objects.nonNull(codes)&&codes.size()>0){
            codes.forEach(v->{
                if (action == NodeStatusEnum.AGREE.getStatus()) {
                    agree(v, note,auth);
                } else if (action == NodeStatusEnum.DISAGREE.getStatus()) {
                    disagree(v, note,auth);
                }
            });
        }
    }

    private FlowInstance saveInstance(FlowDefinition flowDefinition, int bId, int userId, int companyId){
        FlowInstance flowInstance = new FlowInstance();
        //考虑业务员
        User user = userService.getById(userId);
        if(Objects.isNull(user)){
           /* Sales sales = salesService.getById(userId);
            flowInstance.setUserId(sales.getId());
            flowInstance.setUserName(sales.getRealName());*/
        }else{
            flowInstance.setUserId(userId);
            flowInstance.setUserName(user.getUsername());
        }
        String instanceCode = RandomUtil.randomString(20);
        flowInstance.setTitle(flowDefinition.getName());
        flowInstance.setInstanceCode(instanceCode);
        flowInstance.setBId(bId);
        flowInstance.setDefinitionCode(flowDefinition.getDefinitionCode());
        flowInstance.setDefinitionId(flowDefinition.getId());
        flowInstance.setFullName(user.getNickname());
        flowInstance.setModuleId(flowDefinition.getModuleId());
        flowInstance.setModuleName(flowDefinition.getModuleName());
        flowInstance.setCompanyId(companyId);
        flowInstance.setStatus(InstanceStatusEnum.IN_PROGRESS.getStatus());
        save(flowInstance);
        return flowInstance;
    }


    private FlowInstance saveInstance(FlowDefinition flowDefinition,FlowEngineDto dto){
        FlowInstance flowInstance =
                getOne(new QueryWrapper<FlowInstance>().lambda().eq(FlowInstance::getBId,dto.getBid()).eq(FlowInstance::getBCode,dto.getBCode()));
        if(Objects.nonNull(flowInstance) && flowInstance.getStatus().equals(InstanceStatusEnum.IN_PROGRESS.getStatus())) {
            return flowInstance;
        }else{
            if(Objects.nonNull(flowInstance) && flowInstance.getStatus().equals(InstanceStatusEnum.TERMINATED.getStatus())){
                boolean result = removeById(flowInstance.getId());
                log.info("removeById(flowDefinition.getId())："+result);
            }
            if(Objects.nonNull(flowInstance) && flowInstance.getStatus().equals(InstanceStatusEnum.FINISHED.getStatus())){
                throw new ApplicationException("此业务已审批完成");
            }
            flowInstance = new FlowInstance();
            //考虑业务员
            User user = userService.getById(dto.getUserId());
            if(Objects.isNull(user)){
                /*Sales sales = salesService.getById(dto.getUserId());
                flowInstance.setUserId(sales.getId());
                flowInstance.setUserName(sales.getRealName());*/
            }else{
                flowInstance.setUserId(dto.getUserId());
                flowInstance.setUserName(user.getUsername());
            }
            String instanceCode = RandomUtil.randomString(20);
            flowInstance.setTitle(flowDefinition.getName());
            flowInstance.setInstanceCode(instanceCode);
            flowInstance.setBId(dto.getBid());
            flowInstance.setBCode(dto.getBCode());
            flowInstance.setContent(dto.getContent());
            flowInstance.setDefinitionCode(flowDefinition.getDefinitionCode());
            flowInstance.setDefinitionId(flowDefinition.getId());
            flowInstance.setFullName(user.getNickname());
            flowInstance.setModuleId(flowDefinition.getModuleId());
            flowInstance.setModuleName(flowDefinition.getModuleName());
            flowInstance.setCompanyId(dto.getCompanyId());
            flowInstance.setStatus(InstanceStatusEnum.IN_PROGRESS.getStatus());
            flowInstance.setBizAmount(dto.getAmount());
            save(flowInstance);
            return flowInstance;
        }
    }

    private FlowInstanceNode getWaitNode(String code) {
        FlowInstanceNode instanceNode = flowInstanceNodeService.getByTaskCode(code);
        if (instanceNode == null ) {
            throw new ApplicationException("任务不存在");
        }
        if(!NodeStatusEnum.canAgree(instanceNode.getActionStatus())){
            throw new ApplicationException("任务已经审批了");
        }
        return instanceNode;
    }

    private FlowInstance getInProgressInstance(String instanceCode) {
        FlowInstance instance = getOne(new LambdaQueryWrapper<FlowInstance>().eq(FlowInstance::getInstanceCode, instanceCode));
        if (instance == null ) {
            throw new ApplicationException("流程不存在");
        }
        if(!InstanceStatusEnum.inProgress(instance.getStatus())){
            throw new ApplicationException("流程还没启动");
        }
        return instance;
    }

    private void finishFlow(FinishFlowEnum finishFlowEnum,int bId,int moduleId,String moduleCode){
        FlowModule parentModule = flowModuleService.getParentModuleByChildId(moduleId);
        // 通知对应模块，流程结束
        FlowModuleEnum flowModuleEnum = FlowModuleEnum.getById(parentModule.getModuleCode());
        FlowBaseService flowBaseService = SpringContextHolder.getBean(flowModuleEnum.getServiceClass());
        //业务id
        flowBaseService.flowFinished(bId,moduleCode,finishFlowEnum);
    }


}
