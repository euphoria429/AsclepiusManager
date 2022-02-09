package com.hcx.asclepiusmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
@MapperScan({"com.hcx.asclepiusmanager.**.mapper","com.hcx.asclepiusmanager.**.repositories","com.hcx.asclepiusmanager.**.repository"})
public class AsclepiusManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsclepiusManagerApplication.class, args);
    }

}
