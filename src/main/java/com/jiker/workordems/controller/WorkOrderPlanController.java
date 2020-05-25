package com.jiker.workordems.controller;


import com.jiker.workordems.bean.WorkOrderPlan;
import com.jiker.workordems.constants.MessageConstants;
import com.jiker.workordems.service.WorkOrderPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class WorkOrderPlanController {

    @Autowired
    private WorkOrderPlanService workOrderPlanService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    @PostMapping("/createWorkOrderPlan")
    public String CreateWorkOrderPlan(@RequestBody WorkOrderPlan workOrderPlan) {
        String resDate = sdf.format(new Date());
        int result = 0;   //用于接收SQL执行返回码

        //1、判断前端传过来的数据是否满足要求
        if ((workOrderPlan.getRole() == null)||(workOrderPlan.getCycle() == null)||(workOrderPlan.getExecutor() == null)
                ||(workOrderPlan.getContent() == null)||(workOrderPlan.getName() == null)
                ||(workOrderPlan.getNumber() == null)||(workOrderPlan.getPlanStartTime() == null)
                || (workOrderPlan.getPlanEndTime() == null) ||
                (workOrderPlan.getPlanStartTime().getTime() >= workOrderPlan.getPlanEndTime().getTime())) {
            return MessageConstants.WORKORDER_PLAN_PARAM_CHECK_ERROR;
        }

        result = workOrderPlanService.CreateWorkOrderPlan(workOrderPlan);
        //根据返回结果值,完成工单计划成功或失败逻辑处理
        if (result == 0) {
            return resDate + MessageConstants.WORKORDER_PLAN_CREATE_FAIL;
        } else {
            return resDate + MessageConstants.WORKORDER_PLAN_CREATE_SUCCESS;
        }

    }

    @PostMapping("/modifyWorkOrderPlan")
    public String modifyWorkOrderPlan(@RequestBody WorkOrderPlan workOrderPlan) {
        String resDate = sdf.format(new Date());
        int result = 0;   //用于接收SQL执行返回码

        //1、判断前端传过来的数据是否满足要求
        if (workOrderPlan.getId() == null){
            return MessageConstants.WORKORDER_PLAN_ID_CHECK;
        }
        if ((workOrderPlan.getExecutor() == null)||(workOrderPlan.getContent() == null)||(workOrderPlan.getPlanStartTime() == null) || (workOrderPlan.getPlanEndTime() == null) || (workOrderPlan.getPlanStartTime().getTime() >= workOrderPlan.getPlanEndTime().getTime())) {
            return MessageConstants.WORKORDER_PLAN_PARAM_CHECK_ERROR;
        }

        result = workOrderPlanService.ModifyWorkOrderPlan(workOrderPlan);
        //根据返回结果值,完成工单计划成功或失败逻辑处理
        if (result == 1) {
            return resDate + MessageConstants.WORKORDER_PLAN_MODIFY_SUCCESS;
        } else {
            return resDate + MessageConstants.WORKORDER_PLAN_MODIFY_FAIL;
        }
    }

    @GetMapping("/deleteWorkOrderPlan/{id}")
    public String deleteWorkOrderPlan(@PathVariable int id) {
        String resDate = sdf.format(new Date());
        int result = 0;   //用于接收SQL执行返回码

        result = workOrderPlanService.deleteWorkOrderPlan(id);
        //根据返回结果值,完成工单计划成功或失败逻辑处理
        if (result == 1) {
            return resDate + MessageConstants.WORKORDER_PLAN_DELETE_SUCCESS;
        } else {
            return resDate + MessageConstants.WORKORDER_PLAN_DELETE_FAIL;
        }
    }
}
