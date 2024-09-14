package com.program.projectquotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.program.projectquotation.mapper")
public class ProjectQuotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectQuotationApplication.class, args);
    }

}
