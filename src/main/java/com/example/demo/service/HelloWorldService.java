package com.example.demo.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HelloWorldService {

    public String test(String name) {
        return "hello!" + name;
    }


    public String test(String name, int age) {
        return "hello!" + name + "age:" + age;
    }

    /**
     *
     * @param name 名称
     * @param age 年龄
     * @return
     */
    public String test(String name, @Nullable Integer age) throws IOException {
        if (name == null) {
            throw new IOException("name不允许为null");
        }
        return "hello!" + name + "age:" + age;
    }

    public String test2(String name) {
        return "hello!" + name;
    }

    public String getTopic(Integer topicId) {
        if(topicId == null) {
            throw new RuntimeException("topicId不能为空");
        }
        return "topic hahah";
    }


}
