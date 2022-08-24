package com.qiumu.filter;

import com.alibaba.fastjson.JSON;
import com.qiumu.common.BaseContext;
import com.qiumu.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    private static final AntPathMatcher ANT_PATH_MATCHER =new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();

        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/login",
                "/user/loginout",
                "/"
        };

        if (check(urls,uri)){
            filterChain.doFilter(request,response);
            return;
        }

        if (request.getSession().getAttribute("empId") != null) {
            Long empId = (Long) request.getSession().getAttribute("empId");

            BaseContext.setEmpId(empId);

            filterChain.doFilter(request,response);
            return;
        }
        if (request.getSession().getAttribute("user") != null) {
            Long user = (Long) request.getSession().getAttribute("user");

            BaseContext.setEmpId(user);

            filterChain.doFilter(request,response);
            return;
        }

        log.info("拦截了{}",uri);
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public boolean check(String[] urls,String uri){

        for (String url :
                urls) {
            boolean match = ANT_PATH_MATCHER.match(url, uri);
            if (match) {
                return true;
            }
        }

        return false;
    }
}
