package cn.itfield.wxcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KillApp {
    public static void main(String[] args) {
        SpringApplication.run(KillApp.class);
    }
}
