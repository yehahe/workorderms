package com.jiker.workordems.service;

import com.jiker.workordems.bean.WorkOrder;
import com.jiker.workordems.bean.WorkOrderPlan;
import com.jiker.workordems.dao.WorkOrderDao;
import com.jiker.workordems.dao.WorkOrderPlanDao;
import com.jiker.workordems.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WorkOrderService {
    @Autowired
    private WorkOrderPlanDao workOrderPlanDao;
    @Autowired
    private WorkOrderDao workOrderDao;

    /**
     * 根据工单计划生成工单
     * @throws ParseException
     */
    public void produceWorkOrder() throws ParseException {
        Utils utils=new Utils();
        //WorkOrderDao workOrderDao=new WorkOrderDao();
        //WorkOrderPlanDao workOrderPlanDao=new WorkOrderPlanDao();

        List<WorkOrderPlan> workOrderPlanList=new ArrayList<>();
        List<WorkOrder> workOrderList=new ArrayList<>();
        Map<String ,Object> map =new HashMap<String,Object>();
        //1.获取当前日期
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String date=sdf.format(new Date())+" 00:00:00";
        sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate=utils.dateAdd(sdf.parse(date),5,1);//date参数加1天

        String newDateString = sdf.format(newDate);

        //2.写出满足条件的SQL
        //String sql = getString(date, newDateString);
        //3.调用查询数据的方法
        workOrderPlanList=workOrderPlanDao.queryWorkOrder(date,newDateString);
        //4.更新时间值，作为打印时间戳
        String strDate=sdf.format(new Date());

        //5.调用工单生成方法
        if(workOrderPlanList.size()<0){
            //当日无工单计划
            System.out.println("当日无工单计划");
            return;
        }

        map=utils.produceWorkOrder(workOrderPlanList);
        workOrderList=(List<WorkOrder>) map.get("workOrder");
        workOrderPlanList=(List<WorkOrderPlan>)map.get("workOrderPlan");
        for (WorkOrder workOrder:workOrderList){
            //5.1 工单数据入库
            int flag = workOrderDao.insert(workOrder);
            if(flag == 0){
                //根据工单号生成工单入库失败
                System.out.println("工单" + workOrder.getNumber() + "生成工单入库失败");
                return;
            }
        }



        System.out.println(strDate+":根据工单号生成工单入库成功");
        //5.2 更新工单计划数据：工单生成状态置为1-已生成
        for (WorkOrderPlan workOrderPlan:workOrderPlanList){
            int flag = workOrderPlanDao.updateByPrimaryKeySelective(workOrderPlan);
            if(flag == 0){
                //根据工单号生成工单入库失败
                System.out.println("工单计划" + workOrderPlan.getNumber() + "更新失败");
                return;
            }
        }

    }

}
