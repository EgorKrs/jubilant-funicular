package com.loneliness.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

public class SQLConnection {
    private static  SQLConnection instance;
    private static final ReentrantLock locker = new ReentrantLock();
    private final static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
    public static SQLConnection getInstance() throws PropertyVetoException {
        if (instance==null){
            locker.lock();
            if (instance==null){
                instance=new SQLConnection();
            }
            locker.unlock();
        }
        return instance;
    }
    private SQLConnection() throws PropertyVetoException {
        comboPooledDataSource.setDriverClass( "com.mysql.cj.jdbc.Driver" ); //loads the jdbc driver
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/queen_of_spades?serverTimezone=Europe/Moscow&useSSL=false&useUnicode=true&characterEncoding=UTF-8");
        comboPooledDataSource.setUser("root");
        comboPooledDataSource.setPassword("con2Root");
// the settings below are optional -- c3p0 can work with defaults
        comboPooledDataSource.setMinPoolSize(5);
        comboPooledDataSource.setAcquireIncrement(5);
        comboPooledDataSource.setMaxPoolSize(200);
    }
    public Connection getConnection() throws SQLException {
        locker.lock();
        Connection connection= comboPooledDataSource.getConnection();
        locker.unlock();
        return connection;
    }
}
