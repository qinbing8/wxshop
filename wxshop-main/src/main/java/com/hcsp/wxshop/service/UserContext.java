package com.hcsp.wxshop.service;

import com.hcsp.wxshop.generate.User;

public class UserContext {
    private static ThreadLocal<User> currentUser = new ThreadLocal<User>();

    //如果是static修饰的，无论什么时候调用返回的结果都是一样的
    //但是ThreadLocal是特殊的，不同的线程调用currentUser.get()得到的结果是不一样的。
    public static User getCurrentUser() {
        return currentUser.get();
    }

    // 用户来了之后把它放到当前线程的上下文，使得今后的所有调用都可以直接调用
    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }
}
