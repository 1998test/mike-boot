package com.duojiala.mikeboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = {"com.duojiala.mikeboot.dao"})
@SpringBootApplication
public class MikeBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MikeBootApplication.class, args);
    }

}
