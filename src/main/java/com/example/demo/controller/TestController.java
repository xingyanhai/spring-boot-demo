package com.example.demo.controller;


import com.example.demo.service.TestService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("")
    public String helloWorld() {
        return "test";
    }


    @GetMapping("test")
    public String test() {
        return "test";
    }

    @GetMapping("topic")
    public String topic(Integer topicId) throws JSONException {
        if(topicId == null) {
            return "topicId不能为空";
        }
        try {
            return testService.getTopic(topicId);
        }catch (IOException e) {
            return e.toString();
        }
    }
    @GetMapping("question")
    public String topic(String search) {
        if(search == null) {
            return "search不能为空";
        }
        try {
            return testService.searchQuestion(search);
        }catch (Exception e) {
            return e.toString();
        }
    }

    @GetMapping("q")
    public String test1() {
        return "xxx";
    }

    @GetMapping("sql")
    public String sqlTest() {
        return testService.sqlTest();
    }
}
