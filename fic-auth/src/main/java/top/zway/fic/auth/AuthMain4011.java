package top.zway.fic.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ZZJ
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AuthMain4011 {
    public static void main(String[] args) {
        SpringApplication.run(AuthMain4011.class, args);
    }
}
