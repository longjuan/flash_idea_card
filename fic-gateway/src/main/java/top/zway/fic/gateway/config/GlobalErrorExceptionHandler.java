package top.zway.fic.gateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.zway.fic.base.result.R;
import top.zway.fic.base.result.ResultCode;

/**
 * @author ZZJ
 */
@Order(-1)
@Configuration
public class GlobalErrorExceptionHandler implements ErrorWebExceptionHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        // 将返回格式设为JSON
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(HttpStatus.OK);
        }
        String message = ex.getMessage();
        if (ex instanceof NotFoundException){
            message = "部分服务不可用";
        }
        // 改变请求响应返回行为
        String finalMessage = message;
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            R<Object> result = R.result(ResultCode.SYSTEM_EXECUTION_ERROR, finalMessage, null, null);
            try {
                // 返回ErrorResult
                return bufferFactory.wrap(objectMapper.writeValueAsBytes(result));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }

}