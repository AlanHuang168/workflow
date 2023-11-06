package org.apache.alan.wrokflow.service;

import org.apache.alan.wrokflow.dao.po.FlowDefinition;
import org.apache.alan.wrokflow.dto.Authentication;
import org.apache.alan.wrokflow.dto.FlowDefinitionCreateDto;
import org.apache.alan.wrokflow.dto.FlowDefinitionGetByUidAndMIdDto;
import org.apache.alan.wrokflow.dto.FlowUserDto;
import org.apache.alan.wrokflow.vo.FlowDefinitionDetailVo;
import org.apache.alan.wrokflow.vo.FlowUserVo;
import org.apache.alan.wrokflow.vo.GetFlowDefinitionVo;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @create: 2022-12-03 14:00
 **/
public interface FlowDefinitionService extends BaseService<FlowDefinition> {

    FlowDefinition getByModuleIdAndUserId(FlowDefinitionGetByUidAndMIdDto dto);

    /**
     * 根据角色或者部门获取符合条件的用户信息
     * @param dto
     * @return
     */
    FlowUserVo findFlowUserList(FlowUserDto dto);

    /**
     * 根据业务id和企业id查询模板
     * @param moduleId
     * @param companyId
     * @return
     */
    List<GetFlowDefinitionVo> getFlowDefinitionByModuleAndCompany(Integer moduleId, Integer companyId);

    /**
     * 新建审批模板
     * @param form
     */
    void createTemplate(FlowDefinitionCreateDto form, Authentication auth);
    /**
     * 编辑审批模板
     * @param form
     */
    void editTemplate(FlowDefinitionCreateDto form);

    /**
     * 删除审批模板
     * @param code
     */
    void deleteTemplate(String code);

    /**
     * 审批模板详情
     * @param code
     * @return
     */
    FlowDefinitionDetailVo templateDetail(String code);

    /**
     * 开启审批模板
     * @param code
     */
    void openFlowDefinition(String code);
}
