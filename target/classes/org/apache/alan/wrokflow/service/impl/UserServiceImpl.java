package org.apache.alan.wrokflow.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.alan.wrokflow.dao.mapper.UserMapper;
import org.apache.alan.wrokflow.dao.po.User;
import org.apache.alan.wrokflow.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Alan
 * @date: 2023-11-06 17:43
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {


}
