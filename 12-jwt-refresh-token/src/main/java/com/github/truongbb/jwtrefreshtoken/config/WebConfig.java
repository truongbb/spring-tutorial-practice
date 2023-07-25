package com.github.truongbb.jwtrefreshtoken.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

//  private Logger log = LoggerFactory.getLogger(WebConfig.class);

//  @Bean
//  public CorsFilter corsFilter() {
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//    CorsConfiguration config = new CorsConfiguration();
//    config.addAllowedOrigin("*");
//    config.addAllowedHeader("Accept,Accept-Encoding,Accept-Language,Access-Control-Request-Method,Access-Control-Request-Headers," +
//      "Cache-Control,Connection,Content-Length,Content-Type,Host,Origin,Pragma,Referer,User-Agent,Authorization,StoreManagerNo");
//    config.addAllowedMethod("GET,POST,DELETE,PUT,OPTIONS");
//    config.addExposedHeader("Authorization");
//    config.setMaxAge(3600L);
//
//    if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
//      log.debug("Registering CORS filter");
//      source.registerCorsConfiguration("/demo-api/**", config);
//    }
//    return new CorsFilter(source);
//  }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/")
                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
//        registry.addResourceHandler("/vendor/**").addResourceLocations("classpath:/static/vendor/")
//                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/")
                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/")
                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
//        registry.addResourceHandler("/adminlte/**").addResourceLocations("classpath:/static/adminlte/")
//                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
    }

}
