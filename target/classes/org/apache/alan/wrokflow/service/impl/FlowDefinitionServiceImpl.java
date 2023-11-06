package org.apache.alan.wrokflow.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import org.apache.alan.wrokflow.constant.ErrorCodes;
import org.apache.alan.wrokflow.dao.mapper.FlowDefinitionMapper;
import org.apache.alan.wrokflow.dao.po.*;
import org.apache.alan.wrokflow.dto.*;
import org.apache.alan.wrokflow.exception.ApplicationException;
import org.apache.alan.wrokflow.service.*;
import org.apache.alan.wrokflow.utils.MapperUtils;
import org.apache.alan.wrokflow.utils.StringUtils;
import org.apache.alan.wrokflow.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date: 2022-12-03 14:11
 */
@Service
public class FlowDefinitionServiceImpl extends BaseServiceImpl<FlowDefinitionMapper, FlowDefinition> implements FlowDefinitionService {

    @Autowired
    private FlowDefinitionService flowDefinitionService;

    @Autowired
    private FlowInstanceNodeService flowInstanceNodeService;

    @Autowired
    private FlowDefinitionNodeService flowDefinitionNodeService;
    @Autowired
    private FlowNodeConditionService flowNodeConditionService;
    @Autowired
    private FlowNodeUserService flowNodeUserService;
    @Autowired
    private FlowModuleService flowModuleService;

    @Autowired
    private FlowMyModuleService fowMyModuleService;

    @javax.annotation.Resource
    private FlowDefinitionMapper flowDefinitionMapper;

    @Autowired
    private FlowDefinitionServiceImpl flowDefinitionServiceImpl;

    @Override
    public FlowDefinition getByModuleIdAndUserId(FlowDefinitionGetByUidAndMIdDto dto) {
//        QueryWrapper<FlowDefinition> queryWrapper = new QueryWrapper();
//        queryWrapper.eq(FlowDefinition.COL_COMPANYID,dto.getCompanyId());
//        queryWrapper.eq(FlowDefinition.COL_MODULE_ID,dto.getModuleId());
//        queryWrapper.eq()
        return getById(dto.getDefinitionId());
    }

    @Override
    public FlowUserVo findFlowUserList(FlowUserDto dto) {
        FlowUserVo vo = new FlowUserVo();
        vo.setCompanyId(dto.getCompanyId());
        List<FlowUserDescVo> list = new ArrayList<>();
        //角色
        if (Objects.nonNull(dto.getRoleId()) && dto.getRoleId() != 0){
           /* Role role = roleService.getOne(new QueryWrapper<Role>().lambda()
                    .eq(Role::getId,dto.getRoleId())
                    .eq(Role::getCompanyId,dto.getCompanyId()));
            if (Objects.isNull(role)){
                throw new ApplicationException(ErrorCodes.NOT_FOUND,"未查询到企业角色信息");
            }
            vo.setBusinessId(role.getId());
            vo.setBusinessName(role.getRoleName());
            //根据角色id和企业id获取角色信息
            List<CompanyUserRole> companyUserRoleList = companyUserRoleService.list(new QueryWrapper<CompanyUserRole>().lambda()
                    .eq(CompanyUserRole::getCompanyId,dto.getCompanyId())
                    .eq(CompanyUserRole::getRoleId,dto.getRoleId()));
            if (companyUserRoleList.size() != 0){
                List<Integer> userId = companyUserRoleList.stream().map(CompanyUserRole::getUserId).collect(Collectors.toList())
                        .stream().distinct().collect(Collectors.toList());
                userId.forEach(id->{
                    FlowUserDescVo flowUserDescVo = new FlowUserDescVo();
                    User user = userService.getById(id);
                    flowUserDescVo.setUserId(user.getId());
                    flowUserDescVo.setUserName(user.getUsername());
                    list.add(flowUserDescVo);
                });
            }*/
        //部门
        }else if (Objects.nonNull(dto.getDepartmentId()) && dto.getDepartmentId() != 0){
           /* Department department = departmentService.getOne(new QueryWrapper<Department>().lambda()
                    .eq(Department::getCompanyId,dto.getCompanyId())
                    .eq(Department::getId,dto.getDepartmentId()));
            if (Objects.isNull(department)){
                throw new ApplicationException(ErrorCodes.NOT_FOUND,"未查询到企业部门信息");
            }
            vo.setBusinessId(department.getId());
            vo.setBusinessName(department.getName());
            //获取当前部门下面的所有管理员信息
            List<DepartmentUser> departmentUserList = departmentUserService.list(new QueryWrapper<DepartmentUser>().lambda()
                    .eq(DepartmentUser::getCompanyId,dto.getCompanyId())
                    .eq(DepartmentUser::getDepartmentId,dto.getDepartmentId())
                    .isNotNull(DepartmentUser::getUserId));
            departmentUserList.forEach(departmentUser -> {
                FlowUserDescVo flowUserDescVo = new FlowUserDescVo();
                User user = userService.getById(departmentUser.getUserId());
                flowUserDescVo.setUserId(user.getId());
                flowUserDescVo.setUserName(user.getUsername());
                list.add(flowUserDescVo);
            });*/
        }else {
            throw new ApplicationException(ErrorCodes.BUSINESS,"请求参数异常");
        }
        vo.setUserDescVoList(list);
        return vo;
    }

    @Override
    public List<GetFlowDefinitionVo> getFlowDefinitionByModuleAndCompany(Integer moduleId, Integer companyId) {
        return null;//flowDefinitionMapper.getFlowDefinitionByModuleAndCompany(moduleId,companyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createTemplate(FlowDefinitionCreateDto form, Authentication auth) {
        FlowModule flowModule = flowModuleService.getOne(Wrappers.lambdaQuery(FlowModule.class).eq(FlowModule::getCode, form.getModuleCode()));
       // Assert.notNull(flowModule,ErrorCodes.NOT_FOUND,"未查询到模块信息");

        List<FlowDefinition> definitionList = flowDefinitionService.list(Wrappers.lambdaQuery(FlowDefinition.class).eq(FlowDefinition::getName, form.getName()));
        //Assert.isEmpty(definitionList, ErrorCodes.BUSINESS,"已存在相同名称的模板");

        FlowDefinition flowDefinition = new FlowDefinition();
        flowDefinition.setName(form.getName());
        flowDefinition.setDefinitionCode(form.getDefinitionCode());
        //flowDefinition.setCompanyId(auth.getCompanyId());
        //flowDefinition.setDefinitionUserId(auth.getUserId());
       // flowDefinition.setUserName(auth.getUserName());
        flowDefinition.setModuleId(flowModule.getId());
        flowDefinition.setModuleName(flowModule.getName());
        flowDefinition.setStatus(form.getStatus());
        FlowDefinitionConfigDto nodeConfig = form.getNodeConfig();
        if (Objects.nonNull(nodeConfig) && Objects.nonNull(nodeConfig.getType())) {
            flowDefinition.setDefinitionNode(JSONObject.toJSONString(nodeConfig));
        }
        flowDefinitionService.save(flowDefinition);

        Integer flowSet = Objects.isNull(form.getNodeConfig()) ? 0 : (form.getStatus() ? 2 : 1);
        FlowMyModule flowMyModule = new FlowMyModule();
        //flowMyModule.setCompanyId(auth.getCompanyId());
        flowMyModule.setDefinitionId(flowDefinition.getId());
        flowMyModule.setModuleId(flowModule.getId());
        flowMyModule.setModuleName(flowModule.getName());
        flowMyModule.setCreatedTime(new Date());
        flowMyModule.setUpdatedTime(new Date());
        flowMyModule.setFlowSet(flowSet);
        fowMyModuleService.save(flowMyModule);
        if (Objects.nonNull(nodeConfig) && Objects.nonNull(nodeConfig.getType())){
            flowDefinitionServiceImpl.saveFlowDefinitionNode(nodeConfig, null, flowDefinition, null);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editTemplate(FlowDefinitionCreateDto form) {
        FlowDefinition flowDefinition = flowDefinitionService.getOne(Wrappers.lambdaQuery(FlowDefinition.class).eq(FlowDefinition::getCode, form.getCode()));
        //Assert.notNull(flowDefinition, ErrorCodes.NOT_FOUND, "未查询到模板信息");
        if (!StringUtils.equals(flowDefinition.getName(), form.getName())){
            LambdaQueryWrapper<FlowDefinition> definitionWrapper = Wrappers.lambdaQuery(FlowDefinition.class)
                    .eq(FlowDefinition::getName, form.getName()).ne(FlowDefinition::getId, flowDefinition.getId());
            List<FlowDefinition> definitionList = flowDefinitionService.list(definitionWrapper);
           // Assert.isEmpty(definitionList, ErrorCodes.BUSINESS,"已存在相同名称的模板");
        }

        flowDefinition.setName(form.getName());
        flowDefinition.setStatus(form.getStatus());
        flowDefinition.setDefinitionNode("");
        flowDefinition.setDefinitionCode(form.getDefinitionCode());

        //删除模板关联信息
        List<FlowDefinitionNode> definitionNodeList = flowDefinitionNodeService.list(Wrappers.lambdaQuery(FlowDefinitionNode.class).eq(FlowDefinitionNode::getDefinitionId, flowDefinition.getId()));
        if (CollectionUtils.isNotEmpty(definitionNodeList)){
            List<Integer> nodeIdList = definitionNodeList.stream().map(FlowDefinitionNode::getId).collect(Collectors.toList());
            LambdaQueryWrapper<FlowInstanceNode> instanceNodeWrapper = Wrappers.lambdaQuery(FlowInstanceNode.class)
                    .in(FlowInstanceNode::getNodeId, nodeIdList).eq(FlowInstanceNode::getActionStatus, 0);
            List<FlowInstanceNode> flowInstanceNodeList = flowInstanceNodeService.list(instanceNodeWrapper);
            //Assert.isEmpty(flowInstanceNodeList, ErrorCodes.BUSINESS,"模板内有未完成的审批节点不能编辑");

            flowDefinitionNodeService.removeByIds(nodeIdList);
            flowNodeConditionService.remove(Wrappers.lambdaQuery(FlowNodeCondition.class).in(FlowNodeCondition::getNodeId, nodeIdList));
            flowNodeUserService.remove(Wrappers.lambdaQuery(FlowNodeUser.class).in(FlowNodeUser::getNodeId, nodeIdList));
        }
        Integer flowSet = 0;
        FlowDefinitionConfigDto nodeConfig = form.getNodeConfig();
        if (Objects.nonNull(nodeConfig)){
            flowSet = form.getStatus() ? 2 : 1;
            flowDefinition.setDefinitionNode(JSONObject.toJSONString(nodeConfig));
            flowDefinitionServiceImpl.saveFlowDefinitionNode(nodeConfig, null, flowDefinition, null);
        }
        FlowMyModule flowMyModule = fowMyModuleService.getOne(Wrappers.lambdaQuery(FlowMyModule.class).eq(FlowMyModule::getDefinitionId, flowDefinition.getId()));
        flowMyModule.setFlowSet(flowSet);
        flowDefinitionService.updateById(flowDefinition);
        fowMyModuleService.updateById(flowMyModule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTemplate(String code) {
        LambdaQueryWrapper<FlowDefinition> definitionWrapper = Wrappers.lambdaQuery(FlowDefinition.class).eq(FlowDefinition::getCode, code);
        FlowDefinition flowDefinition = flowDefinitionService.getOne(definitionWrapper);
        //Assert.notNull(flowDefinition, ErrorCodes.NOT_FOUND, "未查询到模板信息");
        LambdaQueryWrapper<FlowDefinitionNode> definitionNodeWrapper = Wrappers.lambdaQuery(FlowDefinitionNode.class).eq(FlowDefinitionNode::getDefinitionId, flowDefinition.getId());
        List<FlowDefinitionNode> definitionNodeList = flowDefinitionNodeService.list(definitionNodeWrapper);
        if (CollectionUtils.isNotEmpty(definitionNodeList)){
            List<Integer> nodeIdList = definitionNodeList.stream().map(FlowDefinitionNode::getId).collect(Collectors.toList());
            LambdaQueryWrapper<FlowInstanceNode> instanceNodeWrapper = Wrappers.lambdaQuery(FlowInstanceNode.class)
                    .in(FlowInstanceNode::getNodeId, nodeIdList).eq(FlowInstanceNode::getActionStatus, 0);
            List<FlowInstanceNode> flowInstanceNodeList = flowInstanceNodeService.list(instanceNodeWrapper);
            //Assert.isEmpty(flowInstanceNodeList, ErrorCodes.BUSINESS,"模板内有未完成的审批节点不能删除");

            flowDefinitionNodeService.removeByIds(nodeIdList);
            flowNodeConditionService.remove(Wrappers.lambdaQuery(FlowNodeCondition.class).in(FlowNodeCondition::getNodeId, nodeIdList));
            flowNodeUserService.remove(Wrappers.lambdaQuery(FlowNodeUser.class).in(FlowNodeUser::getNodeId, nodeIdList));
        }
        fowMyModuleService.remove(Wrappers.lambdaQuery(FlowMyModule.class).eq(FlowMyModule::getDefinitionId, flowDefinition.getId()));
        flowDefinitionService.removeById(flowDefinition.getId());
    }

    @Override
    public FlowDefinitionDetailVo templateDetail(String code) {
        FlowDefinition flowDefinition = flowDefinitionService.getOne(Wrappers.lambdaQuery(FlowDefinition.class).eq(FlowDefinition::getCode, code));
        //Assert.notNull(flowDefinition, ErrorCodes.NOT_FOUND, "未查询到模板信息");
        FlowModule flowModule = flowModuleService.getById(flowDefinition.getModuleId());
        //Assert.notNull(flowModule,ErrorCodes.NOT_FOUND,"未查询到模块信息");

        FlowDefinitionDetailVo definitionDetail = MapperUtils.map(flowDefinition, FlowDefinitionDetailVo.class);
        definitionDetail.setModuleCode(flowModule.getCode());
        if (StringUtils.isNotBlank(flowDefinition.getDefinitionNode())){
            definitionDetail.setNodeConfig(JSONObject.parseObject(flowDefinition.getDefinitionNode(), FlowDefinitionConfigVo.class));
        }
        return definitionDetail;
    }

    @Override
    public void openFlowDefinition(String code) {
        FlowDefinition flowDefinition = flowDefinitionService.getOne(Wrappers.lambdaQuery(FlowDefinition.class).eq(FlowDefinition::getCode, code));
        //Assert.notNull(flowDefinition, ErrorCodes.NOT_FOUND, "未查询到模板信息");
        FlowModule flowModule = flowModuleService.getById(flowDefinition.getModuleId());
        //Assert.notNull(flowModule,ErrorCodes.NOT_FOUND,"未查询到模块信息");
        LambdaQueryWrapper<FlowMyModule> wrapper = Wrappers.lambdaQuery(FlowMyModule.class)
                .eq(FlowMyModule::getModuleId, flowModule.getId())
                .eq(FlowMyModule::getCompanyId, flowDefinition.getCompanyId())
                .eq(FlowMyModule::getOpen, Boolean.TRUE);
        FlowMyModule myModule = fowMyModuleService.getOne(wrapper);
        if (Objects.nonNull(myModule) && !Objects.equals(myModule.getDefinitionId(), flowDefinition.getId())){
            throw new ApplicationException(ErrorCodes.BUSINESS, "已存在生效模板");
        }
        FlowMyModule flowMyModule = fowMyModuleService.getOne(Wrappers.lambdaQuery(FlowMyModule.class).eq(FlowMyModule::getDefinitionId, flowDefinition.getId()));
        //Assert.notNull(flowMyModule, ErrorCodes.BUSINESS,"未查询到模块关联信息");

        if (!flowMyModule.getOpen() && !flowDefinition.getStatus()){
            throw new ApplicationException(ErrorCodes.BUSINESS, "未生效的模板不能启用");
        }
        flowMyModule.setOpen(!flowMyModule.getOpen());
        fowMyModuleService.updateById(flowMyModule);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveFlowDefinitionNode(FlowDefinitionConfigDto nodeConfig, FlowNodeConditionDto condition, FlowDefinition flowDefinition, FlowDefinitionNode definitionNode) {
        if (Objects.nonNull(nodeConfig) && Objects.nonNull(nodeConfig.getType())){
            if (Objects.equals(nodeConfig.getType(), 0) || Objects.equals(nodeConfig.getType(), 1) || Objects.equals(nodeConfig.getType(), 2)){
                definitionNode = flowDefinitionServiceImpl.addFlowDefinitionNode(nodeConfig, null, flowDefinition, definitionNode);
            }
            List<FlowNodeConditionDto> conditionNodeList = nodeConfig.getConditionNodeList();
            if (Objects.equals(nodeConfig.getType(), 4)){
                if (CollectionUtils.isNotEmpty(conditionNodeList)) {
                    flowDefinitionServiceImpl.addDefinitionNodeList(conditionNodeList, flowDefinition, definitionNode);
                }
            }
            if (Objects.nonNull(nodeConfig.getChildNode())){
                if (Objects.equals(nodeConfig.getType(), 4)){
                    if (CollectionUtils.isNotEmpty(conditionNodeList)){
                        List<FlowDefinitionNode> definitionNodeList = Lists.newArrayList();
                        getLastFlowDefinitionNodeList(definitionNode, definitionNodeList);
                        for (FlowDefinitionNode node : definitionNodeList) {
                            flowDefinitionServiceImpl.saveFlowDefinitionNode(nodeConfig.getChildNode(), null, flowDefinition, node);
                        }
                    }
                } else {
                    flowDefinitionServiceImpl.saveFlowDefinitionNode(nodeConfig.getChildNode(), null, flowDefinition, definitionNode);
                }
            }
        }
        if (Objects.nonNull(condition) && Objects.nonNull(condition.getType())){
            if (Objects.equals(condition.getType(), 3)){
                definitionNode = flowDefinitionServiceImpl.addFlowDefinitionNode(null, condition, flowDefinition, definitionNode);
            }
            if (Objects.nonNull(condition.getChildNode())){
                flowDefinitionServiceImpl.saveFlowDefinitionNode(condition.getChildNode(), null, flowDefinition, definitionNode);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void getLastFlowDefinitionNodeList(FlowDefinitionNode flowDefinitionNode, List<FlowDefinitionNode> definitionNodeList) {
        List<FlowDefinitionNode> definitionNodes = flowDefinitionNodeService.list(Wrappers.lambdaQuery(FlowDefinitionNode.class).eq(FlowDefinitionNode::getParentId, flowDefinitionNode.getId()));
        if (CollectionUtils.isNotEmpty(definitionNodes)){
            for (FlowDefinitionNode definitionNode : definitionNodes) {
                flowDefinitionServiceImpl.getLastFlowDefinitionNodeList(definitionNode, definitionNodeList);
            }
        } else {
            definitionNodeList.add(flowDefinitionNode);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public FlowDefinitionNode addFlowDefinitionNode(FlowDefinitionConfigDto nodeConfig, FlowNodeConditionDto condition, FlowDefinition flowDefinition, FlowDefinitionNode parentDefinitionNode) {
        FlowDefinitionNode definitionNode = new FlowDefinitionNode();
        definitionNode.setDefinitionSort(1);
        definitionNode.setNoHanderAction(3);
        definitionNode.setDefinitionId(flowDefinition.getId());
        definitionNode.setParentId(0);
        if (Objects.nonNull(parentDefinitionNode)){
            definitionNode.setParentId(parentDefinitionNode.getId());
            definitionNode.setParentCode(parentDefinitionNode.getNodeCode());
            definitionNode.setDefinitionSort(parentDefinitionNode.getDefinitionSort() + 1);
        }
        definitionNode.setNodeCode(org.apache.alan.wrokflow.utils.StringUtils.getUUId(32));
        if (Objects.nonNull(nodeConfig)){
            flowDefinitionServiceImpl.saveDefinitionNode(definitionNode, nodeConfig, flowDefinition.getCompanyId());
        }
        if (Objects.nonNull(condition)){
            flowDefinitionServiceImpl.saveConditionNode(definitionNode, condition);
        }
        return definitionNode;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveDefinitionNode(FlowDefinitionNode definitionNode, FlowDefinitionConfigDto nodeConfig, Integer companyId) {
        definitionNode.setNodeName(nodeConfig.getNodeName());
        definitionNode.setSettype(nodeConfig.getSettype());
        definitionNode.setType(nodeConfig.getType());
        definitionNode.setExamineMode(nodeConfig.getExamineMode());
        definitionNode.setExamineEndDirectorLevel(null);
        flowDefinitionNodeService.save(definitionNode);
        if (CollectionUtils.isNotEmpty(nodeConfig.getNodeUserList())){
            definitionNode.setSelectRange(2);
            definitionNode.setSelectMode((nodeConfig.getNodeUserList().size() > 1) ? 2 : 1);
            List<FlowNodeUser> flowNodeUserList = nodeConfig.getNodeUserList().stream().map(item -> {
                FlowNodeUser flowNodeUser = new FlowNodeUser();
                flowNodeUser.setTargetId(item.getTargetId());
                flowNodeUser.setCompanyId(companyId);
                flowNodeUser.setName(item.getName());
                flowNodeUser.setType(item.getType());
                flowNodeUser.setNodeId(definitionNode.getId());
                flowNodeUser.setUserType(definitionNode.getType());
                return flowNodeUser;
            }).collect(Collectors.toList());
            flowNodeUserService.saveBatch(flowNodeUserList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveConditionNode(FlowDefinitionNode definitionNode, FlowNodeConditionDto condition) {
        definitionNode.setNodeName(condition.getNodeName());
        definitionNode.setType(condition.getType());
        definitionNode.setPriorityLevel(condition.getPriorityLevel());
        flowDefinitionNodeService.save(definitionNode);
        if (CollectionUtils.isNotEmpty(condition.getConditionList())){
            List<FlowNodeCondition> nodeConditionList = condition.getConditionList().stream().map(item -> {
                FlowNodeCondition nodeCondition = new FlowNodeCondition();
                nodeCondition.setNodeId(definitionNode.getId());
                nodeCondition.setType(item.getType());
                nodeCondition.setOptType(item.getOptType());
                if (Objects.equals(item.getType(), 1)){
                    String userIds = item.getNodeUserList().stream()
                            .map(user -> String.valueOf(user.getTargetId())).collect(Collectors.joining(","));
                    nodeCondition.setUserIds(userIds);
                } else {
                    nodeCondition.setZdy1(item.getZdy1());
                    nodeCondition.setColumnType("0");
                }
                return nodeCondition;
            }).collect(Collectors.toList());
            flowNodeConditionService.saveBatch(nodeConditionList);
        }
    }

    public void addDefinitionNodeList(List<FlowNodeConditionDto> conditionNodeList, FlowDefinition flowDefinition, FlowDefinitionNode parentDefinitionNode) {
        for (FlowNodeConditionDto condition : conditionNodeList) {
            saveFlowDefinitionNode(null, condition, flowDefinition, parentDefinitionNode);
        }
    }
}
