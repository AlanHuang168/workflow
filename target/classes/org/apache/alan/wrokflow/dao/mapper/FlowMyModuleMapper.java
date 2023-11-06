package org.apache.alan.wrokflow.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.alan.wrokflow.dao.po.FlowMyModule;
import org.apache.alan.wrokflow.vo.FlowModuleGetCompanyVo;
import org.apache.alan.wrokflow.vo.GetFlowDefinitionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FlowMyModuleMapper extends BaseMapper<FlowMyModule> {

    List<FlowModuleGetCompanyVo> getFlowDefinitionByModuleAndCompany(@Param("moduleId") Integer moduleId, @Param("companyId") Integer companyId);
}
