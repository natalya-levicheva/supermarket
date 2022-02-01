package com.college.supermarket.domain;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DbInit extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "supermarket.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 2;

    private SQLiteDatabase database;
    private final Context context;
    private boolean needUpdate = false;

    private UserDao userDao;
    private ProductDao productDao;

    public DbInit(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.context = context;
        copyDatabase();
        this.getReadableDatabase();
    }

    public void updateDatabase() throws IOException{
        if(needUpdate){
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();
        }
        copyDatabase();
        needUpdate = false;
    }

    private boolean checkDatabase(){
        File dbFile = new File(DB_PATH+DB_NAME);
        return dbFile.exists();
    }

    private void copyDatabase(){
        if(!checkDatabase()){
            this.getReadableDatabase();
            this.close();
            try{
                copyDbFile();
            }
            catch (IOException e){
                throw new Error("ErrorCopyingDb");
            }
        }
    }

    private void copyDbFile() throws IOException{
        InputStream inputStream = context.getAssets().open(DB_NAME);
        OutputStream outputStream = new FileOutputStream(DB_PATH+DB_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while((length = inputStream.read(buffer)) > 0){
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public DbInit(@Nullable Context context, @Nullable String name,
                  @Nullable SQLiteDatabase.CursorFactory factory, int version,
                  @Nullable DatabaseErrorHandler errorHandler, Context context1) {
        super(context, name, factory, version, errorHandler);
        this.context = context1;
    }

    public boolean openDataBase() throws SQLException {
        database = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null,
                SQLiteDatabase.CREATE_IF_NECESSARY);
        return database != null;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            needUpdate = true;
            try {
                updateDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public UserDao getUserDao() throws java.sql.SQLException {
        if(userDao == null){
            userDao = new UserDao(connectionSource, User.class);
        }
        return userDao;
    }
    public ProductDao getProductDao() throws java.sql.SQLException {
        if(productDao == null){
            productDao = new ProductDao(connectionSource, Product.class);
        }
        return productDao;
    }

    @Override
    public void close(){
        super.close();
        userDao = null;
    }
}
