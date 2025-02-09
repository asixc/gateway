package dev.jotxee.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("meal-logs", r -> r
                        .path("/api/v1/meal-logs/**")
                        .filters(f -> f
                                .setResponseHeader("Access-Control-Allow-Origin", "*")
                                .addResponseHeader("X-Powered-By", "JotxeeDEV MEAL-LOG Gateway Service")
                        )
                        .uri("http://food-log-api-native:8081")) // Verifica que el contenedor tenga el hostname correcto
                .route("todo-service", r -> r
                        .path("/api/v1/todo/**")
                        .filters(f -> f
                                .setResponseHeader("Access-Control-Allow-Origin", "*")
                                .addResponseHeader("X-Powered-By", "JotxeeDEV TODO Gateway Service")
                        )
                        .uri("http://todo-api-native-app:8080"))
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
*/
}
