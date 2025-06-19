package com.mapper;

import com.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface UserMapper {
//    登录
    User selectByUserName(@Param("userName") String userName);

    //zhuce
    int insertUser(User user);
    int checkUserNameExists(String userName);
    //忘记密码
    int updatePassword(@Param("userName") String userName, @Param("newPassword") String newPassword);
    //person
    Map<String, Object> getUserByUsername(String username);
    //xiugaimima
    int updatePasswordByUsername(String username, String password);
}
