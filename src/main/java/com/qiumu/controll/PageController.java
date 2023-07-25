package com.qiumu.controll;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PageController {

    @GetMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/front/index.html");
    }
    @GetMapping("/bk")
    public void bk(HttpServletResponse response) throws IOException {
        response.sendRedirect("/backend/page/login/login.html");
    }
}
