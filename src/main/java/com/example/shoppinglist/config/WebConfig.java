package com.example.shoppinglist.config;

import com.example.shoppinglist.resource.interceptor.CustomerInterceptor;
import com.example.shoppinglist.resource.interceptor.TenantInterceptor;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;
    private final CustomerInterceptor customerInterceptor;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter httpMessageConverter) {
//				var objectMapper = httpMessageConverter.getObjectMapper();
//				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//				objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
                httpMessageConverter.setObjectMapper(customMapper());
            }
        }
    }

    public ObjectMapper customMapper() {
        //check all modules registered by default object mapper and diffrence between default and this new jsonmapper
        //javatimemodule required to add to solve Java 8 date/time issue from jsr310
        return JsonMapper.builder().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
                .addModule(new JavaTimeModule())
                .addModule(customStringDeserializer())
                .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true)
                .serializationInclusion(Include.NON_NULL)
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/index.html");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantInterceptor).addPathPatterns("/*api*/*v1*/*shopping-list*/**");
        registry.addInterceptor(customerInterceptor).addPathPatterns("/*api*/*v1*/*shopping-list*/**");


    }

    private SimpleModule customStringDeserializer() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new CustomStringDeserializer());
        return module;
    }

    @Bean
    public PathMatcher pathMatcher() {

        AntPathMatcher pathMatcher = new AntPathMatcher();
        pathMatcher.setTrimTokens(true);
        return pathMatcher;
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setPathMatcher(pathMatcher());
    }
}