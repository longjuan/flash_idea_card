package top.zway.fic.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ZZJ
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayMain9201 {
    public static void main(String[] args) {
        SpringApplication.run(GatewayMain9201.class, args);
    }
}
