package com.jiker.workordems.controller;

import com.jiker.workordems.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableScheduling //1.开启调度器
public class WorkorderPlanSchedulerController {

    @Autowired
   private WorkOrderService workOrderService;
    SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //2.每天凌晨0点启动工单计划调度器
    @Scheduled(cron = "1 * * * * ?")
    private void workOrderScheduler()throws ParseException {
        String date=sdf.format(new Date());
        System.out.println(date+":工单计划调度器启动");
        workOrderService.produceWorkOrder();
        System.out.println(date+":工单计划调度器结束");
    }
}
