package cn.itfield.wxcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MediaApp {
    public static void main(String[] args) {
        SpringApplication.run(MediaApp.class);
    }
}
