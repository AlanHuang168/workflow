package org.apache.alan.wrokflow.service;

import org.apache.alan.wrokflow.dao.po.FlowInstance;
import org.apache.alan.wrokflow.dto.Authentication;
import org.apache.alan.wrokflow.dto.FlowEngineDto;
import org.apache.alan.wrokflow.dto.FlowInstancePageDto;
import org.apache.alan.wrokflow.dto.FlowInstanceUpdateDto;
import org.apache.alan.wrokflow.vo.FlowBusinessDetailVo;
import org.apache.alan.wrokflow.vo.FlowInstancePageVo;
import org.apache.alan.wrokflow.vo.FlowTaskDetailVo;
import org.apache.alan.wrokflow.vo.PageVo;

import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @create: 2022-12-07 18:45
 **/
public interface FlowInstanceService extends BaseService<FlowInstance> {

    void startEngine(String moduleCode, Integer bId, Authentication user);

    /**
     * 业务审批初始化
     * @param dto 审批参数
     * @return
     */
    boolean startEngine(FlowEngineDto dto);

    PageVo<FlowInstancePageVo> listPage(FlowInstancePageDto dto);

    void updateDefinitionId(String definitCode, int definitionId, int id);

    void deleteByDefinitionId(int definitionId);

    void deleteById(int id);

    void update(FlowInstanceUpdateDto dto, int userId);

    void agree(String code, String note, Authentication auth);

    void disagree(String code, String note, Authentication auth);

    void changeNode(String taskCode, String username, int userId);

    List<FlowTaskDetailVo> details(String taskCode, Authentication auth);

    //业务系统查询审批流程详情
    List<FlowTaskDetailVo> getDetailsByBid(Integer bid, String code);

    FlowBusinessDetailVo businessDetail(String instanceCode, Authentication auth);

    void batchApprove(int action, List<String> codes, String note, Authentication auth);
}
