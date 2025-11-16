package Ecom.SecurityConfig;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;


@Configuration 
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final JwtAuthenticationEntryPoint entryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            JwtAuthenticationFilter jwtFilter, 
            JwtAuthenticationEntryPoint entryPoint, 
            JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.jwtFilter = jwtFilter;
        this.entryPoint = entryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    } 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    	.cors(cors -> {
            cors.configurationSource(new CorsConfigurationSource() {
                @Override
                public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                    CorsConfiguration cfg = new CorsConfiguration();

//                    cfg.setAllowedOriginPatterns(Collections.singletonList("*"));
//                    cfg.setAllowedOriginPatterns(Collections.singletonList("https://eccomers96.netlify.app/"));
//                    cfg.setAllowedOriginPatterns(Collections.singletonList("http://localhost:3000"));
                    cfg.setAllowedOriginPatterns(Arrays.asList("*", "https://eccomers96.netlify.app/", "http://localhost:3000"));
                    cfg.setAllowedMethods(Collections.singletonList("*"));

                    cfg.setAllowCredentials(true);
                    cfg.setAllowedHeaders(Collections.singletonList("*"));
                    cfg.setExposedHeaders(Arrays.asList("Authorization"));

                    return cfg;

                }
            });
    	});
    	http.csrf(csrf -> csrf.disable())
    	.exceptionHandling(e -> e.authenticationEntryPoint(entryPoint)
        		.accessDeniedHandler(jwtAccessDeniedHandler))
        .authorizeHttpRequests(auth -> {
            auth
                    .requestMatchers(HttpMethod.POST, "/ecom/admin").permitAll()
                    .requestMatchers(HttpMethod.POST, "/ecom/customers").permitAll()
                    .requestMatchers(HttpMethod.POST, "/ecom/customers/registration").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/ecom/orders/users/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/ecom/product-reviews/**","/ecom/products/**").permitAll()

////                    .requestMatchers(
////                            HttpMethod.POST,
////                            "/ecom/products/**",
////                            "/ecom/order-shippers/**"
////
////                    ).hasRole("ADMIN")
                    .requestMatchers(
                            HttpMethod.POST,
                            "/ecom/products/**",
                            "/ecom/product-reviews/**",
                            "/ecom/customer-addresses/**",
                            "/ecom/cart/**",
                            "/ecom/orders/**",
                            "/ecom/order-shipping/**"
                    ).hasAnyRole("USER", "ADMIN")
                    .requestMatchers(
                            HttpMethod.PUT,
                            "/ecom/admin/**",
                            "/ecom/products/**"

                    ).hasRole("ADMIN")
                    .requestMatchers(
                            HttpMethod.PUT,
                            "/ecom/admin/**",
                            "/ecom/product-reviews/**",
                            "/ecom/customer-addresses/update/**",
                            "/ecom/cart/**", "/ecom/order-shipping/**"

                    ).hasRole("USER")

                    .requestMatchers(
                            HttpMethod.DELETE,
                            "/ecom/products/**",
                            "/ecom/product-reviews/**",
                            "/ecom/customer-addresses/delete/**",
//                            "/ecom/orders/users/**",
                            "/ecom/order-shipping/**",
                            "/ecom/order-shippers/**"

                    ).hasRole("ADMIN")
                    .requestMatchers(
                            HttpMethod.DELETE,
                            "/ecom/cart/remove-product/**"
//                            "/ecom/orders/users/**"
                    ).hasRole("USER")

                    .requestMatchers(
                            HttpMethod.GET,

                            "/ecom/customer-addresses/**",
                            "/ecom/cart/products/**",
                            "/ecom/orders/**",
                            "/ecom/order-shippers",
                            "/ecom/order-payments/**"

                    ).hasAnyRole("ADMIN", "USER")

                    .requestMatchers("/swagger-ui*/**", "/v3/api-docs/**").permitAll()
                    .anyRequest().authenticated();
        })

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}