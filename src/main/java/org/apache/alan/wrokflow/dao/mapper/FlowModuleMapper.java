package org.apache.alan.wrokflow.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.alan.wrokflow.dao.po.FlowModule;
import org.apache.alan.wrokflow.vo.GetFlowModuleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FlowModuleMapper extends BaseMapper<FlowModule> {

    List<GetFlowModuleVo> getFlowModuleByParentId(@Param("parentId") Integer parentId);

    GetFlowModuleVo getFlowModuleDetail(@Param("code")String code);

}
