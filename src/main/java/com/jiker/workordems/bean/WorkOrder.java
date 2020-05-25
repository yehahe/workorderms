package com.jiker.workordems.bean;

import java.io.Serializable;
import java.util.Date;

public class WorkOrder implements Serializable {
    /**
    * id
    */
    private Integer id;

    /**
    * 工单编号
    */
    private String number;

    /**
    * 工单名称
    */
    private String name;

    /**
    * 工单内容
    */
    private String content;

    /**
    * 开始时间
    */
    private Date startTime;

    /**
    * 完成时间
    */
    private Date endTime;

    /**
    * 角色
    */
    private String role;

    /**
    * 执行人
    */
    private String executor;

    /**
    * 工单执行状态：1-已执行，0-未执行
    */
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}