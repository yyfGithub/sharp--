package com.sharp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.sharp.*.dao")
@SpringBootApplication
@EnableCaching
public class SharpApplication {
    public static void main(String[] args) {
        SpringApplication.run(SharpApplication.class, args);
    }
}
