package com.loneliness.dao.sql_dao_impl;


import com.loneliness.dao.DAO;
import com.loneliness.dao.DAOException;
import com.loneliness.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.beans.PropertyVetoException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class SQLDAO<T extends Entity> implements DAO<T> {

    Logger logger = LogManager.getLogger();
    PreparedStatement statement;
    ResultSet resultSet;

    Collection<T> receiveCollection(ResultSet resultSet) {
        Collection<T> data = new ConcurrentLinkedQueue<>();
        T t;
        try {
            while (resultSet.next()) {
                try {// allow get date, if there was invalid note
                    t = receiveDataFromResultSet(resultSet);
                    data.add(t);
                } catch (SQLException e) {
                    logger.catching(e);
                }
            }
        } catch (SQLException e) {
            logger.catching(e);
        }
        return data;
    }

    abstract protected T receiveDataFromResultSet(ResultSet resultSet) throws SQLException;

}
