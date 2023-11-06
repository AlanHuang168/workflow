package org.apache.alan.wrokflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.alan.wrokflow.dao.mapper.FlowMyModuleMapper;
import org.apache.alan.wrokflow.dao.po.FlowDefinition;
import org.apache.alan.wrokflow.dao.po.FlowModule;
import org.apache.alan.wrokflow.dao.po.FlowMyModule;
import org.apache.alan.wrokflow.enums.FlowSetEnum;
import org.apache.alan.wrokflow.service.FlowDefinitionService;
import org.apache.alan.wrokflow.service.FlowModuleService;
import org.apache.alan.wrokflow.service.FlowMyModuleService;
import org.apache.alan.wrokflow.utils.MapperUtils;
import org.apache.alan.wrokflow.vo.FlowModuleGetCompanyVo;
import org.apache.alan.wrokflow.vo.FlowModuleListVo;
import org.apache.alan.wrokflow.vo.GetFlowDefinitionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Alan
 * @date: 2023-04-27 16:11
 */
@Service
@Slf4j
public class FlowMyModuleServiceImpl extends BaseServiceImpl<FlowMyModuleMapper, FlowMyModule> implements FlowMyModuleService {

    @Autowired
    private FlowModuleService flowModuleService;
    @Autowired
    private FlowDefinitionService flowDefinitionService;
    @Resource
    private FlowMyModuleMapper flowMyModuleMapper;

    @Override
    public List<FlowModuleListVo> get(int companyId) {
        List<FlowMyModule> flowMyModules = getByCompanyId(companyId);
        return MapperUtils.mapList(flowMyModules,FlowModuleListVo.class);
    }

    public List<FlowMyModule> getByCompanyId(int companyId){
        return list(new QueryWrapper<FlowMyModule>().lambda()
                .eq(FlowMyModule::getCompanyId,companyId)
                .eq(FlowMyModule::getOpen,true));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOpen(int moduleId,int companyId,boolean open){
        FlowModule flowModule = flowModuleService.getById(moduleId);
        //Assert.notNull(flowModule);

        QueryWrapper<FlowMyModule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowMyModule.COL_MODULE_ID,moduleId);
        queryWrapper.eq(FlowMyModule.COL_COMPANY_ID,companyId);
        FlowMyModule flowMyModule = getOne(queryWrapper);
        if(flowMyModule != null){
            flowMyModule.setOpen(open);
            flowMyModule.setUpdatedTime(new Date());
            FlowDefinition flowDefinition = flowDefinitionService.getById(flowMyModule.getDefinitionId());
            flowDefinition.setStatus(open);
            flowDefinitionService.updateById(flowDefinition);
            updateById(flowMyModule);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createFlowSet(int definitionId,String moduleName,String defName,int moduleId,int companyId){
        QueryWrapper<FlowMyModule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowMyModule.COL_MODULE_ID,moduleId);
        queryWrapper.eq(FlowMyModule.COL_COMPANY_ID,companyId);
        FlowMyModule flowMyModule = getOne(queryWrapper);
        if(flowMyModule == null){
            flowMyModule = new FlowMyModule();
            flowMyModule.setCompanyId(companyId);
            flowMyModule.setModuleId(moduleId);
            flowMyModule.setModuleName(moduleName);
            flowMyModule.setDefName(defName);
            flowMyModule.setFlowSet(FlowSetEnum.NO.getStatus());
            flowMyModule.setCreatedTime(new Date());
            flowMyModule.setOpen(false);
            flowMyModule.setDefinitionId(definitionId);
            save(flowMyModule);
        }else{
            flowMyModule.setUpdatedTime(new Date());
            flowMyModule.setFlowSet(FlowSetEnum.FINISHED.getStatus());
            updateById(flowMyModule);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFlowSet(int moduleId,int companyId,int flowSet){
        QueryWrapper<FlowMyModule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowMyModule.COL_MODULE_ID,moduleId);
        queryWrapper.eq(FlowMyModule.COL_COMPANY_ID,companyId);
        FlowMyModule flowMyModule = getOne(queryWrapper);
        flowMyModule.setUpdatedTime(new Date());
        flowMyModule.setFlowSet(flowSet);
        flowMyModule.setOpen(flowSet == FlowSetEnum.FINISHED.getStatus());
        updateById(flowMyModule);
    }

    @Override
    public List<FlowModuleGetCompanyVo> getFlowDefinitionByModuleAndCompany(Integer moduleId, Integer companyId) {
        return flowMyModuleMapper.getFlowDefinitionByModuleAndCompany(moduleId,companyId);
    }
}
