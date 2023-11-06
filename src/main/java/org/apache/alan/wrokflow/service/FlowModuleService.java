package org.apache.alan.wrokflow.service;



import org.apache.alan.wrokflow.dao.po.FlowModule;
import org.apache.alan.wrokflow.dto.FlowModuleSaveDto;
import org.apache.alan.wrokflow.dto.FlowModuleUpdateDto;
import org.apache.alan.wrokflow.vo.FlowModuleGetCompanyVo;
import org.apache.alan.wrokflow.vo.GetFlowModuleVo;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @create: 2022-12-07 16:03
 **/
public interface FlowModuleService extends BaseService<FlowModule>{

    List<FlowModule> get(int parentId);

    FlowModule getByCode(String code);

    FlowModule getParentModuleByChildId(int id);

    FlowModule getByModuleId(int id);

    /**
     * 查询业务审批功能列表
     * @return
     */
    List<GetFlowModuleVo> getFlowModule();

    /**
     * 获取业务审批功能详情
     * @param code
     * @return
     */
    GetFlowModuleVo getFlowModuleDetail(String code);

    /**
     * 新增业务审批功能
     * @param dto
     */
    void addFlowModule(FlowModuleSaveDto dto);

    /**
     * 修改业务审批功能
     * @param dto
     */
    void updateFlowModule(FlowModuleUpdateDto dto);

    /**
     * 获取一级业务菜单
     * @return
     */
    List<GetFlowModuleVo> getFirstFlowModule();

    /**
     * 获取企业业务功能菜单列表
     * @param companyId
     * @return
     */
    List<FlowModuleGetCompanyVo> getCompanyFlowModule(Integer companyId);
}
