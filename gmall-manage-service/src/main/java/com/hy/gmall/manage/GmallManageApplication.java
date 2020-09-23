package com.hy.gmall.manage;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.hy.gmall.manage.mapper")
@EnableDubboConfiguration
@EnableRedisHttpSession
public class GmallManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallManageApplication.class, args);
    }

}
