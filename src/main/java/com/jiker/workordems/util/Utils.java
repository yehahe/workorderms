package com.jiker.workordems.util;

import com.jiker.workordems.bean.WorkOrder;
import com.jiker.workordems.bean.WorkOrderPlan;
import com.jiker.workordems.dao.WorkOrderPlanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class Utils {

    @Autowired
    private WorkOrderPlanDao workOrderPlanDao;

    /**
     * 生成工单编号
     * @param department
     * @return newNumber
     */
    public String createWorkOrderNumber(String department){
        //WorkOrderPlanDao workOrderPlanDao=new WorkOrderPlanDao();
        String newNumber="";
        //获取当前的8位日期
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String time=sdf.format(date);
        //获取6位序列号
        //String sql="select max(number) as number from workorderms.workorder_plan";
        //String number=workOrderPlanDao.queryField(sql,"number");//获取当前工单表中最大的工单号
        String number=workOrderPlanDao.queryField();//获取当前工单表中最大的工单号
        if(StringUtils.isEmpty(number)){
            number="000001";
        }else {
            number=number.substring(10);
            number = String.format("%0" + 6 + "d", Long.parseLong(number) + 1);
        }
        newNumber=department+time+number;
        return newNumber;
    }

    /**
     * 解析执行周期为crontab格式
     * @param cycle
     * @return cycle
     */
    public String analysisCycle(String cycle){
        if (cycle.equals("时")){
            cycle="0 0 0 * * *";
        }else if(cycle.equals("日")){
            cycle="0 0 2 * * *";
        }else {
            cycle="";
        }
        return cycle;
    }

    /**
     * 根据工单计划表中的周期字段生成工单
     * @param workOrderPlanList 满足条件的工单计划数据
     * @return workOrderList 已生成的工单数据
     */
    public  Map<String, Object> produceWorkOrder(List<WorkOrderPlan> workOrderPlanList) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date planStartTime = null;
        Date planEndTime = null;
        List<WorkOrder> workOrderList = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        //1 遍历取出workOrderPlanList中的每一条数据
        for (int i = 0; i < workOrderPlanList.size(); i++) {
            //1.1 计划出当前工单计划需要生成的工单数量（计划结束时间-计划开始时间）/天
            planStartTime = workOrderPlanList.get(i).getPlanStartTime();
            planEndTime = workOrderPlanList.get(i).getPlanEndTime();
            Integer dateNumber = 1000 * 60 * 60;//默认为1小时的毫秒数
            Integer dateType = 11;//默认为小时的编号
            if (workOrderPlanList.get(i).getCycle().equals("0 0 2 * * *")) {
                dateNumber = 1000 * 60 * 60 * 24;//1天的毫秒数
                dateType = 5;//天的编号
            }
            Long num = (planEndTime.getTime() - planStartTime.getTime()) / dateNumber;

            //1.2 循环生成工单，并写入WorkOrder
            for (int n = 0; n < num; n++) {
                WorkOrder workOrder = new WorkOrder();
                workOrder.setNumber(workOrderPlanList.get(i).getNumber());
                workOrder.setName(workOrderPlanList.get(i).getName());
                workOrder.setContent(workOrderPlanList.get(i).getContent());
                //调用时间相加方法，计算每个工单的开始时间和结束时间
                workOrder.setStartTime(dateAdd(workOrderPlanList.get(i).getPlanStartTime(), dateType, n));
                workOrder.setEndTime(dateAdd(workOrderPlanList.get(i).getPlanEndTime(), dateType, n + 1));
                workOrder.setRole(workOrderPlanList.get(i).getRole());
                workOrder.setExecutor(workOrderPlanList.get(i).getExecutor());
                workOrder.setStatus(workOrderPlanList.get(i).getStatus());
                workOrderList.add(workOrder);
            }
            //1.3 更新工单计划表中的工单生成状态（status）字段
            workOrderPlanList.get(i).setStatus("1");
        }
        //2 生成的工单数据写入map中
        map.put("workOrder", workOrderList);
        //3 更新后的工单计划数据写入map中
        map.put("workOrderPlan", workOrderPlanList);
        System.out.println(sdf.format(new Date()) + ":工单生成成功");
        return map;
    }

    /**
     * 时间相加
     * @param date 传入初始时间值
     * @param type 年:1 月:2 天:5 小时:11
     * @param number 时间需要加的数量
     * @return date 返回相加后的值
     */
    public Date dateAdd(Date date,Integer type,Integer number){
        Calendar calendar=new GregorianCalendar();
        calendar.setTime(date);
        if(type==5){
            calendar.add(Calendar.DAY_OF_MONTH,number);
        }else if (type==11){
            calendar.add(Calendar.HOUR_OF_DAY,number);
        }
        date=calendar.getTime();
        return date;
    }
}