package org.apache.alan.wrokflow.service;

import org.apache.alan.wrokflow.dao.po.FlowMyModule;
import org.apache.alan.wrokflow.vo.FlowModuleGetCompanyVo;
import org.apache.alan.wrokflow.vo.FlowModuleListVo;
import org.apache.alan.wrokflow.vo.GetFlowDefinitionVo;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @create: 2023-04-27 16:11
 **/
public interface FlowMyModuleService extends BaseService<FlowMyModule>{

    List<FlowModuleListVo> get(int companyId) ;

    void updateOpen(int moduleId, int companyId, boolean open);

    public void createFlowSet(int definitionId, String moduleName, String defName, int moduleId, int companyId);

    public void updateFlowSet(int moduleId, int companyId, int flowSet);

    List<FlowModuleGetCompanyVo> getFlowDefinitionByModuleAndCompany(Integer moduleId, Integer companyId);

}
