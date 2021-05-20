package com.server.service;

import com.server.entity.User;

import java.util.List;

/**
 * 功能描述：
 *
 * @Author: hyq
 * @Date: 2021/5/9 10:17
 */
public interface UserService {

    List<User> findAll();

    String save(User user);

    User getUserById(String id);

    String updateUserById(User user);

}
