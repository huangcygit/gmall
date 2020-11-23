package com.hy.gmall.manage;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.hy.gmall.manage.mapper")
@EnableDubbo
public class GmallManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallManageApplication.class, args);
    }

}
