package org.apache.alan.wrokflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.alan.wrokflow.dao.mapper.FlowNodeUserMapper;
import org.apache.alan.wrokflow.dao.po.FlowNodeUser;
import org.apache.alan.wrokflow.service.FlowNodeUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Alan
 * @date: 2022-12-05 17:40
 */
@Service
public class FlowNodeUserServiceImpl extends BaseServiceImpl<FlowNodeUserMapper, FlowNodeUser> implements FlowNodeUserService {

    @Override
    public void deleteByNodeIds(List<Integer> nodesIds){
        if(nodesIds.size() <=0) {
            return;
        }
        QueryWrapper<FlowNodeUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(FlowNodeUser.COL_NODE_ID,nodesIds);
        remove(queryWrapper);
    }

    @Override
    public List<FlowNodeUser> getByTargetIdAndNodeId(Integer targetId, Integer nodeId) {
        QueryWrapper<FlowNodeUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowNodeUser.COL_NODE_ID,nodeId);
        queryWrapper.eq(FlowNodeUser.COL_TARGET_ID,targetId);
        return list(queryWrapper);
    }

    @Override
    public boolean checkCondition(Integer nodeId, Integer userId) {
        QueryWrapper<FlowNodeUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowNodeUser.COL_NODE_ID,nodeId);
       // queryWrapper.eq(FlowNodeUser.COL_TARGET_ID,targetId);
        List<FlowNodeUser> list = list(queryWrapper);
        for(FlowNodeUser nodeUser : list){
            if(nodeUser.getType() == 1){
                //指定人
                return nodeUser.getTargetId().intValue()==userId;
            }else if(nodeUser.getType() == 2){
                //TODO 角色 根据userId 查询角色

            }else if(nodeUser.getType() == 3){
                //TODO 部门

            }
        }

        return true;
    }

    @Override
    public List<FlowNodeUser> getByNodeId(Integer nodeId) {
        QueryWrapper<FlowNodeUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowNodeUser.COL_NODE_ID,nodeId);
        queryWrapper.ne(FlowNodeUser.COL_USER_TYPE,1);
        List<FlowNodeUser> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<FlowNodeUser> getByNodeId(Integer nodeId,Integer type) {
        QueryWrapper<FlowNodeUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowNodeUser.COL_NODE_ID,nodeId);
        queryWrapper.eq(FlowNodeUser.COL_USER_TYPE,type);
        List<FlowNodeUser> list = list(queryWrapper);
        return list;
    }

    @Override
    public void updateUserType(Integer targetId, Integer nodeId,int userType) {
        UpdateWrapper<FlowNodeUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(FlowNodeUser.COL_NODE_ID,nodeId);
        updateWrapper.eq(FlowNodeUser.COL_TARGET_ID,targetId);
        updateWrapper.set(FlowNodeUser.COL_USER_TYPE,userType);
        update(updateWrapper);
    }
}
