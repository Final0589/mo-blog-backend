package com.mo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mo.mapper")
public class MoBlogBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoBlogBackendApplication.class, args);
    }

}
