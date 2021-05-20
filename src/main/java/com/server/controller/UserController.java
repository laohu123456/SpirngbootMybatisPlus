package com.server.controller;

import com.server.entity.User;
import com.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能描述：
 *
 * @Author: hyq
 * @Date: 2021/5/9 10:20
 */
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "findAll")
    public List<User> findAll(){
        return userService.findAll();
    }

    @RequestMapping(value = "save")
    public String save(@RequestBody User user){
        return userService.save(user);
    }

    @RequestMapping(value = "getUserById")
    public User getUserById(@RequestParam("id") String id){
        return userService.getUserById(id);
    }

    @RequestMapping(value = "updateUserById")
    public String updateUserById(@RequestBody User user){
        return userService.updateUserById(user);
    }

}
