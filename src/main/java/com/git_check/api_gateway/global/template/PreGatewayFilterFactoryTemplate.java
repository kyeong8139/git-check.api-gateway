package com.git_check.api_gateway.global.template;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

import lombok.Data;

public abstract class PreGatewayFilterFactoryTemplate extends AbstractGatewayFilterFactory<PreGatewayFilterFactoryTemplate.Config> {

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

    public PreGatewayFilterFactoryTemplate() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            customizeRequest(builder);
            return chain.filter(exchange.mutate().request(builder.build()).build());
        };
    }

    protected abstract void customizeRequest(ServerHttpRequest.Builder builder);
}


