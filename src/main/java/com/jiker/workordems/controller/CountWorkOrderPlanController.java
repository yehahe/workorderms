package com.jiker.workordems.controller;

import com.jiker.workordems.bean.WorkOrderPlan;
import com.jiker.workordems.service.CountWorkOrderPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountWorkOrderPlanController {
    @Autowired
   private CountWorkOrderPlanService countWorkOrderPlanService;

    /**
     * 统计某个时间段内的工单计划数
     * @param workOrderPlan
     * @return
     * 根据status 来判断
     * status 空 查询所有
     *        1  已提交
     *        0 未提交
     */
    @PostMapping("/queryWorkOrderPlanForTimeRegin")
    public List<WorkOrderPlan> queryWorkOrderPlanForTimeRegin(@RequestBody WorkOrderPlan workOrderPlan){
        List<WorkOrderPlan> workOrderPlanList=new ArrayList<WorkOrderPlan>();
        //1 校验时间参数是否为空，且开始时间不能大于结束时间
        if((workOrderPlan.getPlanStartTime()==null)&&(workOrderPlan.getPlanEndTime()==null)&&(workOrderPlan.getPlanStartTime().getTime()>workOrderPlan.getPlanEndTime().getTime())){
            return null;
        }else {
            //2 调用业务逻辑层queryWorkOrderPlanForTimeRegin方法查询数据
            workOrderPlanList=countWorkOrderPlanService.queryWorkOrderPlanForTimeRegin(workOrderPlan);
        }
        return workOrderPlanList;
    }
}
