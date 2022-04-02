package top.zway.fic.search.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @author ZZJ
 */
@EqualsAndHashCode(callSuper = true)
@Configuration
@ConfigurationProperties("elasticsearch")
@Data
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
    private String host;
    private Integer port;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port));
        return new RestHighLevelClient(builder);
    }
}