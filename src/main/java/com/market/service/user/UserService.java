package com.market.service.user;

import com.market.entity.User;

/**
 * @author kinoz
 * @Date 2022/7/8 - 15:33
 * @apiNote
 */
public interface UserService {
    //用户登录
    User Login(String userCode,String password);
}
