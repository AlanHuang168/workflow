package org.apache.alan.wrokflow.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.alan.wrokflow.dao.po.FlowDefinitionNode;
import org.apache.ibatis.annotations.Param;

public interface FlowDefinitionNodeMapper extends BaseMapper<FlowDefinitionNode> {
    FlowDefinitionNode findById(@Param("id") Integer id);
}
