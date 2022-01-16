package com.hcsp.wxshop.service;

import com.hcsp.wxshop.dao.UserDao;
import com.hcsp.wxshop.generate.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUserIfNotExist(String tel) {
        User user = new User();
        user.setTel(tel);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        try {
            userDao.insertUser(user);
        } catch (DuplicateKeyException e) {
            return userDao.getUserByTel(tel);
        }
        return user;
    }

    /**
     * 根据电话返回用户，如果用户不存在，返回null
     * @param tel
     * @return 返回用户
     */
    public User getUserByTel(String tel) {
        return userDao.getUserByTel(tel);
    }
}
