package com.college.supermarket.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;

import com.college.supermarket.domain.DbInit;
import com.college.supermarket.domain.User;
import com.college.supermarket.domain.UserDao;

import java.sql.SQLException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class UserService {
    private static final String TABLE_NAME = "User";
    private SQLiteDatabase database;
    private DbInit dbInit;
    private UserDao userDao;
    private static User authUser;


    private static final String key = "q8ikOzHGPVXmWj6q+h4g7g==";

    public UserService(Context context) throws SQLException {
        dbInit = new DbInit(context);
        database = dbInit.getReadableDatabase();
        userDao = dbInit.getUserDao();
    }

    public void setAuthUser(User user){
        authUser = user;
    }

    public User getAuthUser(){
        return authUser;
    }

    public void create(User user) throws Exception {
        user.setPassword(encodePassword(user.getPassword()));
        userDao.create(user);
    }

    public boolean getUser(User user) throws Exception {
        User userFromDb = userDao.findUserByLogin(user.getNumberPhone());
        if (decodePassword(userFromDb.getPassword()).equals(user.getPassword())) {
            setAuthUser(userFromDb);
            return true;
        } else return false;

    }

    private String encodePassword(String password) throws Exception {
        byte[] encodedBytes = Base64.decode(password, Base64.DEFAULT);
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, stringToKey(key));
        return Base64.encodeToString(c.doFinal(password.getBytes("UTF-8")), Base64.DEFAULT);
    }

    private String decodePassword(String encodePassword) throws Exception {
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, stringToKey(key));
        byte[] encodedBytes = Base64.decode(encodePassword, Base64.DEFAULT);
        return new String(c.doFinal(encodedBytes), "UTF-8");
    }

    public SecretKey stringToKey(String stringKey) {
        byte[] encodedKey = Base64.decode(stringKey.trim(), Base64.DEFAULT);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }
}
