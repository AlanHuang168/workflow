package org.apache.alan.wrokflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.alan.wrokflow.dao.mapper.FlowNodeConditionMapper;
import org.apache.alan.wrokflow.dao.po.FlowInstance;
import org.apache.alan.wrokflow.dao.po.FlowNodeCondition;
import org.apache.alan.wrokflow.service.FlowNodeConditionService;
import org.apache.alan.wrokflow.service.FlowNodeUserService;
import org.apache.alan.wrokflow.service.UserService;
import org.apache.alan.wrokflow.utils.BigDecimalUtil;
import org.apache.alan.wrokflow.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-05 17:31
 */
@Slf4j
@Service
public class FlowNodeConditionServiceImpl extends BaseServiceImpl<FlowNodeConditionMapper, FlowNodeCondition> implements FlowNodeConditionService {

    @Autowired
    private FlowNodeUserService flowNodeUserService;
    @Autowired
    private UserService userService;

    @Override
    public void deleteByNodeIds(List<Integer> nodeIds) {
        if(nodeIds.size() <= 0){
            return;
        }
        QueryWrapper<FlowNodeCondition> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(FlowNodeCondition.COL_NODE_ID,nodeIds);
        remove(queryWrapper);
    }

    @Override
    public boolean checkCondition(Integer nodeId,Integer userId) {
        QueryWrapper<FlowNodeCondition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowNodeCondition.COL_NODE_ID,nodeId);
        List<FlowNodeCondition> list = list(queryWrapper);
        //1指定人、2角色、3部门 和 字段条件
        if(list.size() > 0){
            for(FlowNodeCondition condition : list) {
                //指定人or角色or部门
                if(StringUtils.isNotEmpty(condition.getShowType())){
                    if(FlowNodeCondition.SHOW_TYPE_INPUT.equals(condition.getShowType())){
                        //input--> val=1 zdy1:200 zdy2: opt1: < opt2:<
                        // input value < 1:true:false
                    }
                    if(FlowNodeCondition.SHOW_TYPE_CHECKBOX.equals(condition.getShowType())){
                        //checkbox 字段值 勾选 key=1
                    }
                }else{
                    if(condition.getType() == 1) {
                        return flowNodeUserService.getByTargetIdAndNodeId(userId, nodeId).size() > 0;
                    }
                }

            }

        }
        return true;
    }

    @Override
    public FlowNodeCondition checkCondition(Integer nodeId, FlowInstance flowInstance) {
        List<FlowNodeCondition> list =
                list(new QueryWrapper<FlowNodeCondition>().lambda().eq(FlowNodeCondition::getNodeId,nodeId));
        FlowNodeCondition condition = null;
        if(Objects.nonNull(list)&&list.size()>0){
            for (FlowNodeCondition v:list){
                // 指定发起人，
                if(v.getType().equals(1)){
                    String [] userIds = v.getUserIds().split(",");
                    List<String> ids = Arrays.asList(userIds);
                    if(ids.indexOf(String.valueOf(flowInstance.getUserId()))>-1){
                        condition = v;
                        break;
                    }
                }else{
                    if(Objects.nonNull(v.getColumnType()) && v.getColumnType().equals("0")) {
                        if (v.getOptType().equals(">")) {
                            if(BigDecimalUtil.isGreaterThan(flowInstance.getBizAmount(),BigDecimalUtil.str2BigDecimal(v.getZdy1()))) {
                                condition = v;
                                break;
                            }
                        }
                        if (v.getOptType().equals("<")) {
                            if(BigDecimalUtil.isLessThan(flowInstance.getBizAmount(),BigDecimalUtil.str2BigDecimal(v.getZdy1()))) {
                                condition = v;
                                break;
                            }
                        }
                        if (v.getOptType().equals("≤")) {
                            if(BigDecimalUtil.isLessOrEqual(flowInstance.getBizAmount(),BigDecimalUtil.str2BigDecimal(v.getZdy1()))) {
                                condition = v;
                                break;
                            }
                        }
                        if (v.getOptType().equals("=")) {
                            if(BigDecimalUtil.equals(flowInstance.getBizAmount(),BigDecimalUtil.str2BigDecimal(v.getZdy1()))) {
                                condition = v;
                                break;
                            }
                        }
                        if (v.getOptType().equals("≥")) {
                            if(BigDecimalUtil.isGreaterOrEqual(flowInstance.getBizAmount(),BigDecimalUtil.str2BigDecimal(v.getZdy1()))) {
                                condition = v;
                                break;
                            }
                        }
                    }
                }
            }
        }
        log.info("命中的条件记录："+JSON.toJSONString(condition));
        return condition;
    }
}
