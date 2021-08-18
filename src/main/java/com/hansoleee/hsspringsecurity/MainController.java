package com.hansoleee.hsspringsecurity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        log.info(request.getRequestURL().toString() + " " + request.getMethod());
        return "login";
    }

    @GetMapping("/register")
    public String register(HttpServletRequest request) {
        log.info(request.getRequestURL().toString() + " " + request.getMethod());
        return "register";
    }
}
