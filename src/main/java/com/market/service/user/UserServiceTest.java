package com.market.service.user;

import com.market.entity.User;
import org.junit.Test;

/**
 * @author kinoz
 * @Date 2022/7/13 - 10:13
 * @apiNote service层面功能测试
 */
public class UserServiceTest {
    @Test
    public void Test1(){
        //测试是否可以成功修改密码
        UserServiceImpl use = new UserServiceImpl();
        boolean flag = use.updatePwd(3, "aa12345");
        System.out.println("用户密码修改:"+flag);
    }
    @Test
    public void Test2(){
        UserServiceImpl us = new UserServiceImpl();
        User admin = us.Login("admin", "123456");
        System.out.println("根据用户密码查用户的其他信息");
        System.out.println(admin.getAddress());
        System.out.println(admin.getBirthday());
    }
    @Test
    public void Test3(){
        UserServiceImpl use = new UserServiceImpl();
        System.out.println("对应员工等级的总人数");
        System.out.println(use.getUserCount(null, 3));
    }

    @Test
    public void Test4(){
        UserServiceImpl us = new UserServiceImpl();
        System.out.println("用户分页测试!");
        System.out.println(us.getUserList(null, 3, 2, 3));
    }
    @Test
    public void addTest(){
        UserServiceImpl us = new UserServiceImpl();
        User user = new User();
        user.setUserName("赵六");
        System.out.println(us.addUser(user));
    }
}
