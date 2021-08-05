package com.hcsp.wxshop.dao;

import com.hcsp.wxshop.generate.User;
import com.hcsp.wxshop.generate.UserExample;
import com.hcsp.wxshop.generate.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDao {
    private final UserMapper userMapper;

    @Autowired
    public UserDao(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void insertUser(User user) {
        userMapper.insert(user);
    }

    public User getUserByTel(String tel) {
        UserExample example = new UserExample();
        example.createCriteria().andTelEqualTo(tel);
        return userMapper.selectByExample(example).get(0);
    }
}
