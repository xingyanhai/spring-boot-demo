package com.example.demo.controller;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/queryUserList")
    public  List<User> queryUserList () {
        List<User> userList = userMapper.queryUserList();
        return userList;
    }
    @GetMapping("/queryUserById")
    public  User queryUserById (int id) {
        User user = userMapper.queryUserById(id);
        return user;
    }
    @GetMapping("/addUser")
    public  int addUser (String name, String pwd) {
        int res = userMapper.addUser(new User(null,name,pwd));
        return res;
    }
    @GetMapping("/updateUser")
    public  int updateUser (String name, String pwd, int id) {
        int res = userMapper.updateUser(new User(id,name,pwd));
        return res;
    }
    @GetMapping("/deleteUser")
    public  int deleteUser (int id) {
        int res = userMapper.deleteUser(id);
        return res;
    }
}
