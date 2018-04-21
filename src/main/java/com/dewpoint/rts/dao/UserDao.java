package com.dewpoint.rts.dao;

import org.springframework.stereotype.Component;

@Component
public class UserDao<User, ID> extends BaseDao<User, Long> {
}