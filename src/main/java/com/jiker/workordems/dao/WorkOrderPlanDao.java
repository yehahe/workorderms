package com.jiker.workordems.dao;

import com.jiker.workordems.bean.WorkOrderPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WorkOrderPlanDao {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkOrderPlan record);

    int insertSelective(WorkOrderPlan record);

    WorkOrderPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorkOrderPlan record);

    int updateByPrimaryKey(WorkOrderPlan record);

    @Select("select max(number) as number from workorder_plan")
    String queryField();

    //@Select({"<script>select * from workorder_plan where 1=1 <if test= 'status!=null'> status=#{status} </if> and <![CDATA[ plan_start_time>=#{planStartTime} and plan_start_time <= #{planEndTime}]]></script>"})
    List<WorkOrderPlan> queryWorkOrderPlanForTimeRegin(WorkOrderPlan workOrderPlan);

    //@Select("select * from workorder_plan where status=0 and plan_start_time>=date_format(#{date},'%Y-%m-%d %H:%i:%s') and plan_start_time < date_format(#{newDateString},'%Y-%m-%d %H:%i:%s')")
    List<WorkOrderPlan> queryWorkOrder(@Param("date") String date, @Param("newDateString") String newDateString);
}