package dev.jotxee.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class Config {

    @Bean
    public RouteLocator todoAppRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/todo/**")
                        .filters(f -> f
                                //.addRequestHeader("Hello", "World"))
                                //.prefixPath("/api/v1")
                                .addResponseHeader("X-Powered-By", "JotxeeDEV Gateway Service")
                        )
                        .uri("http://todo-api-native-app:8080"))
                .route(r -> r.path("/api/v1/meal-logs/**")
                        .filters(f -> f
                                //.prefixPath("/api/v1")
                                .addResponseHeader("X-Powered-By", "JotxeeDEV Gateway Service")
                        )
                        .uri("http://food-log-api-native:8081"))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = getCorsConfiguration();

        // Registrar la configuración en todas las rutas
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    private static CorsConfiguration getCorsConfiguration() {
        final CorsConfiguration config = new CorsConfiguration();

        // Orígenes permitidos
        config.setAllowedOrigins(List.of(
                "http://localhost",
                "https://foodlog.jotxee.dev",
                "https://dev.todo.full4media.com"
        ));

        // Métodos HTTP permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // ❌ NO es necesario allowCredentials(true) para JWT
        // config.setAllowCredentials(true); // 🔴 NO lo incluyas

        // Permitir encabezados, incluyendo Authorization para JWT
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        return config;
    }

}
