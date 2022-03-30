package top.zway.fic.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author ZZJ
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(true)
                .groupName("longjuan")
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.zway.fic"))
                .paths(PathSelectors.any())
                .build();
    }


    @SuppressWarnings("all")
    public ApiInfo apiInfo(){
        return new ApiInfo(
                "longjuan's api",
                "flash_idea_card",
                "v1.0",
                "769022681@qq.com", //开发者团队的邮箱
                "longjuan",
                "Apache 2.0",  //许可证
                "http://www.apache.org/licenses/LICENSE-2.0" //许可证链接
        );
    }
}