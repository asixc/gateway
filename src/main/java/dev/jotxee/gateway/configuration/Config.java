package dev.jotxee.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class Config {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("cors-preflight", r -> r
                        .method("OPTIONS")
                        .filters(f -> f
                                .setResponseHeader("Access-Control-Allow-Origin", "*")
                                .setResponseHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                                .setResponseHeader("Access-Control-Allow-Headers", "*")
                                .setResponseHeader("Access-Control-Max-Age", "3600")
                                .setResponseHeader("X-Powered-By", "JotxeeDEV Gateway Service")
                        )
                        .uri("no://op")) // Responde directamente sin ir al backend
                .route(r -> r.path("/api/v1/todo/**")
                        .filters(f -> f
                                .addResponseHeader("Access-Control-Allow-Origin", "*")
                                .addResponseHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                                .addResponseHeader("Access-Control-Allow-Headers", "*")
                        )
                        .uri("http://todo-api-native-app:8080"))
                .route(r -> r.path("/api/v1/meal-logs/**")
                        .filters(f -> f
                                .addResponseHeader("Access-Control-Allow-Origin", "*")
                                .addResponseHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                                .addResponseHeader("Access-Control-Allow-Headers", "*")
                        )
                        .uri("http://food-log-api-native:8081"))
                .build();
    }

    /*
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
                //config.setAllowedOriginPatterns(List.of("http://172.24.*", "https://172.24.*"));
                config.addAllowedOriginPattern("*");
                // Métodos HTTP permitidos
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                // ❌ NO es necesario allowCredentials(true) para JWT
                // config.setAllowCredentials(true); // 🔴 NO lo incluyas
                // Permitir encabezados, incluyendo Authorization para JWT
                config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                return config;
            }
*/
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Permitir cualquier origen (ajústalo si es necesario)
        config.addAllowedOrigin("*");

        // Métodos permitidos, incluyendo OPTIONS para Preflight
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Permitir todos los headers
        config.setAllowedHeaders(List.of("*"));

        // Permitir credenciales si es necesario (JWT, cookies, etc.)
        //config.setAllowCredentials(true);

        // Registrar la configuración para todas las rutas
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

}
