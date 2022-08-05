package top.zway.fic.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ZZJ
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserMain9066 {
    public static void main(String[] args) {
        SpringApplication.run(UserMain9066.class, args);
    }
}
