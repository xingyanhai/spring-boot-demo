package com.example.demo.controller;


import com.example.demo.service.HelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {
    private final static Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class);

    @Autowired
    private HelloWorldService helloWorldService;

    @RequestMapping(value = "abc",  method = RequestMethod.GET)
    public String helloWorld2(@RequestParam("name") String name, int age) throws IOException {

        System.out.println(name + " - " + age);
        // LOGGER.info("人在他在 {}", 123);
        Integer a = 123;
        helloWorldService.test("aaa", 123);
        helloWorldService.test(null, a);
        return helloWorldService.test(name);
    }

    @GetMapping("")
    public String helloWorld(@RequestParam("name") String name, String age) {

        System.out.println(name + " - " + age);
        // LOGGER.info("人在他在 {}", 123);
        return helloWorldService.test2(name);
    }

    @GetMapping("{abcd}")
    public String helloWorld2(@PathVariable("abcd") String name) {
        // LOGGER.info("人在他在 {}", 123);
        return helloWorldService.test(name);
    }
}

