package com.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述：
 *
 * @Author: hyq
 * @Date: 2021/5/9 10:12
 */
@Data
@TableName(value = "a_user")
public class User implements Serializable {


    private static final long serialVersionUID = -5577897625529829107L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String userid;
    private String username;
    private String password;
    private String email;

}
