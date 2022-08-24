package com.qiumu.config;

import com.qiumu.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
        log.info("静态资源映射....");

    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(new JacksonObjectMapper());

        log.info("Mvc消息转换扩展....");
        converters.add(0,messageConverter);
    }
    //    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//        HandlerInterceptor handlerInterceptor = new HandlerInterceptor() {
//            @Override
//            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//                log.info("{} run..",request.getRequestURI());
//
//                if (request.getSession().getAttribute("empId") != null) {
//                    return true;
//                }
//
//                response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
//                return false;
//            }
//        };
//        registry.addInterceptor(handlerInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/backend/**","/employee/login","/employee/logout");
//    }
}
