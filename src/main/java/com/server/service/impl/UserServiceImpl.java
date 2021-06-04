package com.server.service.impl;

import com.server.dao.UserMapper;
import com.server.entity.CommonException;
import com.server.entity.User;
import com.server.service.UserService;
import com.server.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 功能描述：
 *
 * @Author: hyq
 * @Date: 2021/5/9 10:18
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.selectList(null);
    }


    @Transactional
    @Override
    public String save(User user) {
        String result = null;
        try{
            result = (userMapper.insert(user) > 0 ? "插入数据成功" : "插入数据失败");
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
        return result;
    }

    @Override
    public User getUserById(String id) {
        return userMapper.selectById(id);
    }

    @Transactional
    @Override
    public String updateUserById(User user) {
        String result = null;
        try {
            result = userMapper.updateById(user) > 0 ? "修改成功" : "修改失败";
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public void testMybatisInterceptor() {
        String uuid = Utils.getUUID();
        User user = new User();
        user.setUserid(uuid);
        user.setUsername("aaaaaaasasasasasa");
        user.setPassword("sdfjldjfs;ld");
        user.setEmail("sadkljaklsjdksaljdkl");
        userMapper.insert(user);

        User user1 = userMapper.selectById(uuid);

        user1.setEmail("dshfkdhljgfdl");

        userMapper.updateById(user1);
        userMapper.deleteById(uuid);
    }
}
