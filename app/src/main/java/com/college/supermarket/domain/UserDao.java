package com.college.supermarket.domain;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class UserDao extends BaseDaoImpl<User, Integer> {

    public UserDao(ConnectionSource connectionSource, Class<User> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<User> findAll() throws SQLException {
        return this.queryForAll();
    }

    public User findUserByUserId(Integer userId) throws SQLException {
        return this.queryForId(userId);
    }

    public User findUserByLogin(String login) throws SQLException {
        return this.queryForEq("numberPhone", login).get(0);
    }
}
