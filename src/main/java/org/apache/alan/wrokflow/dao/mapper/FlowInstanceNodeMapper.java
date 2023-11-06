package org.apache.alan.wrokflow.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.alan.wrokflow.dao.po.FlowInstanceNode;
import org.apache.alan.wrokflow.dto.FlowInstancePageDto;
import org.apache.alan.wrokflow.vo.FlowInstancePageVo;
import org.apache.ibatis.annotations.Param;

public interface FlowInstanceNodeMapper extends BaseMapper<FlowInstanceNode> {

    IPage<FlowInstancePageVo> getMyTaskPage(@Param("pageVo") IPage<FlowInstancePageVo> pageVo, @Param("dto") FlowInstancePageDto dto);

    IPage<FlowInstancePageVo> getMyTaskPageNew(@Param("pageVo") IPage<FlowInstancePageVo> pageVo, @Param("dto") FlowInstancePageDto dto);

}
