package top.zway.fic.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import top.zway.fic.base.constant.AuthConstant;
import top.zway.fic.gateway.config.IgnoreUrlsConfig;

import java.net.URI;
import java.util.List;

/**
 * 白名单路径访问时需要移除JWT请求头
 *
 * @author ZZJ
 */
@Component
@RequiredArgsConstructor
public class IgnoreUrlsRemoveJwtFilter implements WebFilter, Ordered {
    private final IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径移除JWT请求头
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                request = exchange.getRequest().mutate().header(AuthConstant.AUTHORIZATION_HEADER_KEY, "").build();
                exchange = exchange.mutate().request(request).build();
                return chain.filter(exchange);
            }
        }
        List<String> tokens = exchange.getRequest().getHeaders().get(AuthConstant.AUTHORIZATION_HEADER_KEY);
        if (tokens != null && !tokens.isEmpty()) {
            String token = tokens.get(0);
            if (!StrUtil.isEmpty(token)) {
                if (!token.startsWith(AuthConstant.TOKEN_REQUEST_HEADER_START_WITH)) {
                    request = exchange.getRequest().mutate().header(AuthConstant.AUTHORIZATION_HEADER_KEY, "").build();
                    exchange = exchange.mutate().request(request).build();
                    return chain.filter(exchange);
                }
                token = token.substring(AuthConstant.TOKEN_REQUEST_HEADER_START_WITH.length());
                Integer exp;
                try {
                    exp = (Integer) JWT.of(token).getPayload("exp");
                } catch (Exception e) {
                    request = exchange.getRequest().mutate().header(AuthConstant.AUTHORIZATION_HEADER_KEY, "").build();
                    exchange = exchange.mutate().request(request).build();
                    return chain.filter(exchange);
                }
                if (exp < System.currentTimeMillis() / 1000) {
                    request = exchange.getRequest().mutate().header(AuthConstant.AUTHORIZATION_HEADER_KEY, "").build();
                    exchange = exchange.mutate().request(request).build();
                    return chain.filter(exchange);
                }
            }
        }
        return chain.filter(exchange);
    }

    /**
     * @return 最前面的过滤器
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}