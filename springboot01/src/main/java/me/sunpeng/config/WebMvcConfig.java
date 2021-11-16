package me.sunpeng.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ServletContextRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Collections;

/**
 * @author sp
 * @date 2021-11-16 20:42
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Bean
    public FilterRegistrationBean loggingFilterRegistration() {
        FilterRegistrationBean<ServletContextRequestLoggingFilter> registration = new FilterRegistrationBean<>();
        ServletContextRequestLoggingFilter filter = new ServletContextRequestLoggingFilter();
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(9999);
        registration.setFilter(filter);
        registration.setUrlPatterns(Collections.singleton("/notifications/*"));
        return registration;
    }

    /**
     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。 需要重新指定静态资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

}
