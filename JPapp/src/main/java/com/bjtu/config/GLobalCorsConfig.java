//package com.bjtu.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
///*
// * 跨域配置
// */
//@Configuration
//public class GLobalCorsConfig {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOriginPattern("*");
//        config.setAllowCredentials(true);
//        config.addExposedHeader("");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**",config);
//        return new CorsFilter(source);
//    }
//
//}
