package com.loneliness.dao.sql_dao_impl;

import com.loneliness.dao.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
/**
 * класс для получения создания и управления подключениями к базе данных
 * @author Egor Krasouski
 *
 */
class ConnectionPool {
    private static final ReentrantLock locker=new ReentrantLock();
    private static final String url="jdbc:mysql://localhost/queen_of_spades?allowPublicKeyRetrieval=TRUE&serverTimezone=Europe/Moscow&useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private static final String driver="com.mysql.cj.jdbc.Driver";
    private static final int step=5;
    private final int maxCapacity=100;
    private LinkedBlockingQueue<Connection> availableConnection = new LinkedBlockingQueue<>(maxCapacity);
    private LinkedBlockingQueue<Connection> usedConnection = new LinkedBlockingQueue<>(maxCapacity);
    private Properties properties;
    private String username="root";
    private String password="con2Root";
    private final Logger logger = LogManager.getLogger();
    private static  ConnectionPool instance;


    static ConnectionPool getInstance() throws DAOException {
        if(instance==null){
            try {
                locker.lock();
                if (instance == null) {
                    instance = new ConnectionPool(url, driver, step);
                }
            }
            finally {
                locker.unlock();
            }
        }
        return instance;
    }



    private ConnectionPool(String url, String driver, int initConnCnt) throws DAOException {
        try {
            Class.forName(driver);
            properties=new Properties();
            properties.setProperty("user",username);
            properties.setProperty("password", password);

        } catch (Exception e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_USING_DRIVER "+driver,e);
        }
        if(initConnCnt<0){

            logger.info("Wrong initial size "+initConnCnt+ " . Use 5 at start");
            initConnCnt=5;
        }
        for (int i = 0; i < initConnCnt; i++) {
            availableConnection.add(createConnection());
        }
    }

    private Connection createConnection() throws DAOException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,properties);
        } catch (Exception e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_CREATING_CONNECTION ",e);
        }
        return conn;
    }

    private void addConnection(int quantity) throws DAOException {
        for (int i=0;i<quantity;i++){
           if(!availableConnection.offer(createConnection())){
               logger.info("Queue is full");
               break;
           }
        }
    }

    public Connection getConnection() throws  DAOException {
        Connection newConn = null;
        if(availableConnection.size()==0){
            if(maxCapacity<=usedConnection.size()){
                throw new DAOException("can not add more connection");
            }
            addConnection(step);
        }


        newConn = availableConnection.poll();
        try {
            assert newConn != null;
            usedConnection.put(newConn);
        } catch (InterruptedException e) {
            logger.catching(e);
            throw new DAOException(e.getMessage(),e.getCause());
        }
        return newConn;
    }



    void closeConnection(Connection connection) throws  DAOException {
        try {

        if (connection != null) {
            if (usedConnection.remove(connection)) {
                    availableConnection.put(connection);
            } else {
                throw new DAOException("Connection not in the usedConns array");
            }
        }
        }catch (InterruptedException e) {
                logger.catching(e);
                throw new DAOException(e.getMessage(),e.getCause());
            }

    }

    public void closeAllConnection(){
            while (usedConnection.size() != 0){
                usedConnection.clear();
            }

    }

    public int getAvailableConnectionCount() {
        return availableConnection.size();
    }
}


