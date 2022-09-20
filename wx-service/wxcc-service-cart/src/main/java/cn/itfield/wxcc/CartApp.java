package cn.itfield.wxcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CartApp {
    public static void main(String[] args) {
        SpringApplication.run(CartApp.class,args);
    }
}
