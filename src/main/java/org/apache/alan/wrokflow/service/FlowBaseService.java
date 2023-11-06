package org.apache.alan.wrokflow.service;

import org.apache.alan.wrokflow.enums.FinishFlowEnum;

/**
 * 审批流基础接口
 * @description:
 * @author: Administrator
 * @create: 2022-12-15 11:14
 **/
public interface FlowBaseService {

    /**
     * 所有流程结束后调用
     * @param bid 业务id
     * @param instanceCode 业务类型
     * @param finishFlowEnum 完成流程的类型：未开启、流程未定义、正常完成
     * @return
     */
    void flowFinished(int bid, String instanceCode, FinishFlowEnum finishFlowEnum);

    /**
     * 流程终止后调用，回退某些业务操作
     * @param bid 业务id
     * @param instanceCode 业务类型
     */
    void flowTerminated(int bid, String instanceCode);

//    /**
//     * 审批业务详情
//     * @param bid 业务id
//     * @return
//     */
//    FlowBusinessDetailVo flowTaskDetail(int bid);
}
