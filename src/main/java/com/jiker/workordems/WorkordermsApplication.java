package com.jiker.workordems;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author skryy9
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.jiker.workordems.dao")
public class WorkordermsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkordermsApplication.class, args);
    }

}
