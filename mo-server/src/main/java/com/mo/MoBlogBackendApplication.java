package com.mo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.mo.mapper")
@EnableScheduling
public class MoBlogBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoBlogBackendApplication.class, args);
    }

}
