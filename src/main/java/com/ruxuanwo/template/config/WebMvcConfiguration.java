package com.ruxuanwo.template.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ruxuanwo.template.filter.AddTokenFilter;
import com.ruxuanwo.template.interceptor.ApiInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.nio.charset.Charset;

/**
 * 全局配置
 *
 * @author ruxuanwo
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Value("${hm.web.mvc.schema}")
    private String schema;

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        /*是否允许请求带有验证信息*/
        corsConfiguration.setAllowCredentials(true);
        /*允许访问的客户端域名*/
        corsConfiguration.addAllowedOrigin("*");
        /*允许服务端访问的客户端请求头*/
        corsConfiguration.addAllowedHeader("*");
        /*允许访问的方法名,GET POST等*/
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    /**
     * 使用fastJson代替Jackjson解析JSON数据
     *
     * @return
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        /*
         * 转换为JSON字符串，默认:
         * WriteNullListAsEmpty    List字段如果为null,输出为[],而非null
         * WriteNullStringAsEmpty  字符类型字段如果为null,输出为”“,而非null
         * WriteMapNullValue       是否输出值为null的字段,默认为false
         */
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        fastConverter.setDefaultCharset(Charset.forName("UTF-8"));
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }

    /**
     * 资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
    }

    /**
     * 文件上传临时路径，解决spring boot上传2m或者以上大小的文件时报错
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(102400000);
        factory.setMaxRequestSize(102400000);
        String location = System.getProperty("user.dir") + "/data/tmp";
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(apiInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/error");
    }

    /**
     * 注入拦截器
     * @return
     */
    @Bean
    public ApiInterceptor apiInterceptor(){
        return new ApiInterceptor();
    }



    /**
     * 这个Filter 添加token
     */
    @Bean
    public FilterRegistrationBean addTokenFilter(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //开发环境AddTokenFilter里的参数填写dev，生产环境填写pro，dev则所有请求自带token，pro则拦截所有请求
        registration.setFilter(new AddTokenFilter(schema));
        registration.addUrlPatterns("/*");
        registration.setName("addTokenFilter");
        registration.setAsyncSupported(true);
        return registration;
    }


}
