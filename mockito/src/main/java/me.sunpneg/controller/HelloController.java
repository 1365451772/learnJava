package me.sunpneg.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sp
 * @date 2021-10-25 10:38
 */
@RestController
public class HelloController {

    @GetMapping("/")
    public String hello(){
        return "hello";
    }
}
