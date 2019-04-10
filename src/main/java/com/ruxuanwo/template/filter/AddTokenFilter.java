package com.ruxuanwo.template.filter;

import com.ruxuanwo.template.constant.Constant;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * 测试环境下，为每个接口默认添加一个超级管理员的Filter
 * 部署到正式环境时，关闭这个过滤器
 *
 * @author ruxuanwo
 */
public class AddTokenFilter implements Filter {

    /**
     * superAdmin
     */
    private static final String DEFAULT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJlZGUyZjhiZjM3Y2UzMTkwMjgzN2M0YTBhMjZjZjNmYSJ9.hcIneFTEbuVXk6i2FwFjnMQXG4fvsvfOe5RFK0WqMxs";

    private String profilesActive;

    public AddTokenFilter(String profilesActive) {
        super();
        this.profilesActive = profilesActive;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //判断是开发模式（dev）还是生产坏境（pro）
        //如果不是开发坏境，不做任何操作，是开发坏境，往本地测试的request加请求头
        if (profilesActive == null || !"dev".equalsIgnoreCase(profilesActive)) {

            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        filterChain.doFilter(new CustomeizedRequest((HttpServletRequest) servletRequest), servletResponse);
    }

    @Override
    public void destroy() {

    }

    //继承HttpServletRequestWrapper ，重写getHeader获取请求头的值
    private class CustomeizedRequest extends HttpServletRequestWrapper {

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request
         * @throws IllegalArgumentException if the request is null
         */
        public CustomeizedRequest(HttpServletRequest request) {
            super(request);
        }

        //请求中没有带token头部key值的话，默认添加
        @Override
        public String getHeader(String name) {
            if (!Constant.HTTP_HEADER_ACCESS_TOKEN.equalsIgnoreCase(name)) {
                return super.getHeader(name);
            }
            String token = super.getHeader(name);
            return StringUtils.isNotBlank(token) ? token : DEFAULT_TOKEN;
        }

        //请求中没有带Cookie的话，默认创建一个Cookie放入token
        @Override
        public Cookie[] getCookies() {
            Cookie[] cookies = super.getCookies();
            if (cookies == null) {
                Cookie cookie = new Cookie(Constant.HTTP_HEADER_ACCESS_TOKEN, DEFAULT_TOKEN);
                cookie.setPath("/");
                cookie.setMaxAge(60);
                cookies = new Cookie[]{cookie};
            }
            return cookies;
        }
    }
}
