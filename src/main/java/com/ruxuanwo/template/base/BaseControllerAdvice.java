package com.ruxuanwo.template.base;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 页面路由控制
 *
 * @author ruxuanwo
 */
@Data
@ControllerAdvice
public class BaseControllerAdvice{
    @Value("${system.config.base-url}")
    private String systemBaseUrl;
    @Value("${system.config.static-url}")
    private String systemStaticUrl;
    @ModelAttribute(name="staticUrl")
    public String staticUrl() {
        return systemStaticUrl;
    }
    @ModelAttribute(name="domain")
    public String baseUrl() {
        return systemBaseUrl;
    }

}
