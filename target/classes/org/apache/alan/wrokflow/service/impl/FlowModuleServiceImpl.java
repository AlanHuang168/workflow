package org.apache.alan.wrokflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.apache.alan.wrokflow.constant.ErrorCodes;
import org.apache.alan.wrokflow.dao.mapper.FlowModuleMapper;
import org.apache.alan.wrokflow.dao.po.FlowModule;
import org.apache.alan.wrokflow.dto.FlowModuleSaveDto;
import org.apache.alan.wrokflow.dto.FlowModuleUpdateDto;
import org.apache.alan.wrokflow.exception.ApplicationException;
import org.apache.alan.wrokflow.service.FlowDefinitionService;
import org.apache.alan.wrokflow.service.FlowModuleService;
import org.apache.alan.wrokflow.service.FlowMyModuleService;
import org.apache.alan.wrokflow.utils.MapperUtils;
import org.apache.alan.wrokflow.utils.StringUtils;
import org.apache.alan.wrokflow.vo.FlowModuleGetCompanyVo;
import org.apache.alan.wrokflow.vo.GetFlowModuleVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-07 16:04
 */
@Service
public class FlowModuleServiceImpl extends BaseServiceImpl<FlowModuleMapper, FlowModule> implements FlowModuleService {

    @Resource
    private FlowModuleMapper flowModuleMapper;

    @Autowired
    private FlowDefinitionService flowDefinitionService;

    @Autowired
    private FlowMyModuleService flowMyModuleService;

    @Override
    public List<FlowModule> get(int parentId) {
        QueryWrapper<FlowModule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowModule.COL_PARENT_ID,parentId);
        return list(queryWrapper);
    }

    @Override
    public FlowModule getByCode(String code) {
        QueryWrapper<FlowModule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowModule.COL_MODULE_CODE,code);
        return getOne(queryWrapper);
    }

    @Override
    public FlowModule getParentModuleByChildId(int id) {
        return getById(id);
//        FlowModule flowModule = getById(id);
//        FlowModule flowModule1 = getById(flowModule.getParentId());
//        if(Objects.nonNull(flowModule1)){
//            return flowModule1;
//        }else {
//            return flowModule;
//        }
    }

    @Override
    public FlowModule getByModuleId(int id) {
        return getById(id);
    }

    @Override
    public List<GetFlowModuleVo> getFlowModule() {
        //获取父级审批业务菜单列表
        List<GetFlowModuleVo> flowModuleList = getFirstFlowModule();
        if (CollectionUtils.isEmpty(flowModuleList)){
            return flowModuleList;
        }

        for (GetFlowModuleVo flowModule : flowModuleList){
            //获取下级审批业务功能列表
            List<GetFlowModuleVo> childrenFlowModuleList = flowModuleMapper.getFlowModuleByParentId(flowModule.getId());
            if (CollectionUtils.isNotEmpty(childrenFlowModuleList)){
                flowModule.setChildren(childrenFlowModuleList);
            }
        }
        return flowModuleList;
    }

    @Override
    public GetFlowModuleVo getFlowModuleDetail(String code) {
        GetFlowModuleVo vo = flowModuleMapper.getFlowModuleDetail(code);
        return vo;
    }

    @Override
    public void addFlowModule(FlowModuleSaveDto dto) {

        FlowModule flowModule = MapperUtils.map(dto,FlowModule.class);
        if (StringUtils.isNotBlank(dto.getParentCode())){
            if (StringUtils.isBlank(dto.getModuleCode())){
                throw new ApplicationException(ErrorCodes.BUSINESS,"业务菜单code不能为空");
            }
            FlowModule parentModule = getOne(new QueryWrapper<FlowModule>().lambda().
                    eq(FlowModule::getCode,dto.getParentCode()).eq(FlowModule::getDeleted,false));
            //Assert.notNull(parentModule, ErrorCodes.NOT_FOUND,"父级业务菜单不存在");
            flowModule.setParentId(parentModule.getId());
        }else{
            flowModule.setParentId(0);
        }

        save(flowModule);
    }

    @Override
    public void updateFlowModule(FlowModuleUpdateDto dto) {
        FlowModule flowModule = getOne(new QueryWrapper<FlowModule>().lambda().
                eq(FlowModule::getCode,dto.getCode()).eq(FlowModule::getDeleted,false));
        //Assert.notNull(flowModule, ErrorCodes.NOT_FOUND,"业务菜单不存在");
        if (StringUtils.isNotBlank(dto.getParentCode())){
            if (StringUtils.isBlank(dto.getModuleCode())){
                throw new ApplicationException(ErrorCodes.BUSINESS,"业务菜单code不能为空");
            }
            FlowModule parentModule = getOne(new QueryWrapper<FlowModule>().lambda().
                    eq(FlowModule::getCode,dto.getParentCode()).eq(FlowModule::getDeleted,false));
           // Assert.notNull(parentModule, ErrorCodes.NOT_FOUND,"父级业务菜单不存在");
            flowModule.setParentId(parentModule.getId());
        }else{
            flowModule.setParentId(0);
        }

        flowModule.setModuleCode(dto.getModuleCode());
        flowModule.setName(dto.getName());
        flowModule.setSort(dto.getSort());
        flowModule.setRemark(dto.getRemark());

        updateById(flowModule);
    }

    @Override
    public List<GetFlowModuleVo> getFirstFlowModule() {
        List<GetFlowModuleVo> flowModuleList = flowModuleMapper.getFlowModuleByParentId(0);
        return flowModuleList;
    }

    @Override
    public List<FlowModuleGetCompanyVo> getCompanyFlowModule(Integer companyId) {
        List<FlowModuleGetCompanyVo> companyFlowModuleList = new ArrayList<>();
        //获取父级审批业务菜单列表
        List<GetFlowModuleVo> flowModuleList = getFirstFlowModule();
        if (CollectionUtils.isEmpty(flowModuleList)){
            return companyFlowModuleList;
        }
        companyFlowModuleList = MapperUtils.mapList(flowModuleList,FlowModuleGetCompanyVo.class);
        for (FlowModuleGetCompanyVo companyFlowModule : companyFlowModuleList){
            //获取下级审批业务功能列表
            List<GetFlowModuleVo> childrenFlowModuleList = flowModuleMapper.getFlowModuleByParentId(companyFlowModule.getId());
            if (CollectionUtils.isNotEmpty(childrenFlowModuleList)){
                List<FlowModuleGetCompanyVo> childrenCompanyFlowModuleList = MapperUtils.mapList(childrenFlowModuleList,FlowModuleGetCompanyVo.class);
                companyFlowModule.setChildren(childrenCompanyFlowModuleList);
                for (FlowModuleGetCompanyVo childrenCompanyFlowModule : childrenCompanyFlowModuleList){
                    //查询是否存在企业模板
                    List<FlowModuleGetCompanyVo> flowDefinitionVoList = flowMyModuleService.
                            getFlowDefinitionByModuleAndCompany(childrenCompanyFlowModule.getId(),companyId);
                    childrenCompanyFlowModule.setFlowDefinitionList(flowDefinitionVoList);
                }
            }
        }

        return companyFlowModuleList;
    }

}
