package com.jiker.workordems.dao;

import com.jiker.workordems.bean.WorkOrder;
import com.jiker.workordems.bean.WorkOrderPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface WorkOrderDao {
    int deleteByPrimaryKey(Integer id);

    int insert(WorkOrder record);

    int insertSelective(WorkOrder record);

    WorkOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WorkOrder record);

    int updateByPrimaryKey(WorkOrder record);

    //@Select("select * from workorder_plan where status=0 and plan_start_time>=date_format(#{date},'%Y-%m-%d %H:%i:%s') and plan_start_time < date_format(#{newDateString},'%Y-%m-%d %H:%i:%s')")
    //List<WorkOrderPlan> queryWorkOrder( @Param("date") String date, @Param("newDateString") String newDateString);
}