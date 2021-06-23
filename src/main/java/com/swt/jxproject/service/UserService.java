package com.swt.jxproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swt.jxproject.dto.UserDto;
import com.swt.jxproject.entity.User;

public interface UserService extends IService<User> {
    UserDto findUserByNameEq(String name);

    public UserDto findUserById(Long id);
}
