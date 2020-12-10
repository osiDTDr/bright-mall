package cn.iocoder.mall.userweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"cn.iocoder.mall.systemservice.rpc","cn.iocoder.mall.userservice.rpc"})
public class UserWebApplication {

    public static void main(String[] args) {
           SpringApplication.run(UserWebApplication.class, args);
    }

}
