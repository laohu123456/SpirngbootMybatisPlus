package com.server.controller;

import com.server.annotation.LogMethodRecord;
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

    @LogMethodRecord(value = "mybatisplus初试,查询全部", uri = "/user/findAll")
    @RequestMapping(value = "findAll")
    public List<User> findAll(){
        return userService.findAll();
    }

    @LogMethodRecord(value = "mybatisplus初试,保存实体", uri = "/user/save")
    @RequestMapping(value = "save")
    public String save(@RequestBody User user){
        return userService.save(user);
    }

    @LogMethodRecord(value = "mybatisplus初试,根据Id查询", uri = "/user/getUserById")
    @RequestMapping(value = "getUserById")
    public User getUserById(@RequestParam("id") String id){
        return userService.getUserById(id);
    }

    @LogMethodRecord(value = "mybatisplus初试,根据Id更新指定数据", uri = "/user/updateUserById")
    @RequestMapping(value = "updateUserById")
    public String updateUserById(@RequestBody User user){
        return userService.updateUserById(user);
    }


    @RequestMapping(value = "testMybatisInterceptor")
    public void testMybatisInterceptor(){
        userService.testMybatisInterceptor();
    }
}
