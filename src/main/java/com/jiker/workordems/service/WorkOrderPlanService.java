package com.jiker.workordems.service;

import com.jiker.workordems.bean.WorkOrderPlan;
import com.jiker.workordems.dao.WorkOrderPlanDao;
import com.jiker.workordems.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkOrderPlanService {

    @Autowired
    private WorkOrderPlanDao workOrderPlanDao;
    @Autowired
    private Utils utils;

    /**
     * 创建工单业务逻辑处理
     * @param workOrderPlan
     * @return
     */
    public Integer CreateWorkOrderPlan(WorkOrderPlan workOrderPlan){
        //WorkOrderPlanDao workOrderPlanDao=new WorkOrderPlanDao();
        //Utils utils=new Utils();
        //1 根据科室代码生成工单号
        String number =  utils.createWorkOrderNumber(workOrderPlan.getNumber());
        //2 根据周期生成对应的cron
        String cycle= utils.analysisCycle(workOrderPlan.getCycle());
        //3 调用Dao层入库
        workOrderPlan.setNumber(number);
        workOrderPlan.setCycle(cycle);
        Integer result=0;
        result=workOrderPlanDao.insert(workOrderPlan);
        return result;
    }

    /**
     * 修改工单业务逻辑
     * @param workOrderPlan
     * @return
     */
    public Integer ModifyWorkOrderPlan(WorkOrderPlan workOrderPlan){
        //判断工单是否存在
        Integer result=0;
        WorkOrderPlan workOrderPlanModify = workOrderPlanDao.selectByPrimaryKey(workOrderPlan.getId());
        if (workOrderPlanModify == null){
            return result;
        }
        //只改变工单内容，开始和结束时间
        workOrderPlanModify.setContent(workOrderPlan.getContent());
        workOrderPlanModify.setPlanStartTime(workOrderPlan.getPlanStartTime());
        workOrderPlanModify.setPlanEndTime(workOrderPlan.getPlanEndTime());
        workOrderPlanModify.setExecutor(workOrderPlan.getExecutor());
        int updateFlag = workOrderPlanDao.updateByPrimaryKeySelective(workOrderPlanModify);
        if (updateFlag == 1){
            result = 1;
            return result;
        }else {
            return result;
        }
    }

    public Integer deleteWorkOrderPlan(int id){
        Integer result=0;
        result = workOrderPlanDao.deleteByPrimaryKey(id);
        return result;
    }

}
