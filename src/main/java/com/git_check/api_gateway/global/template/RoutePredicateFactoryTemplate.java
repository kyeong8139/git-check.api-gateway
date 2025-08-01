package com.git_check.api_gateway.global.template;

import java.util.function.Predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import lombok.Data;

public abstract class RoutePredicateFactoryTemplate extends AbstractRoutePredicateFactory<RoutePredicateFactoryTemplate.Config> {
    
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

    public RoutePredicateFactoryTemplate() {
        super(Config.class);
    }   

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return exchange -> {
            ServerHttpRequest request = exchange.getRequest();
            return matches(request, config);
        };
    }

    protected abstract boolean matches(ServerHttpRequest request, Config config);
}
