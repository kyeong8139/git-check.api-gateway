package com.git_check.api_gateway.global.template;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;

import lombok.Data;
import reactor.core.publisher.Mono;

public abstract class PostGatewayFilterFactoryTemplate extends AbstractGatewayFilterFactory<PostGatewayFilterFactoryTemplate.Config> {

    @Data
    public static class Config {
        private String path;
        private String method;
        private String header;
        private String query;
        private String body;
        private String cookie;
        private String param;
    }

    public PostGatewayFilterFactoryTemplate() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpResponse response = exchange.getResponse();
                customizeResponse(response, config);
            }));
        };
    }

    protected abstract void customizeResponse(ServerHttpResponse response, Config config);
}


