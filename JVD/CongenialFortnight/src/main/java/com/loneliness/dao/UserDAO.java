package com.loneliness.dao;

import com.loneliness.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;


/*
   return :
   -2-error
   -3-invalid note
   -4-db error

   */


public class UserDAO implements DAO<User>{
    private Logger logger = LogManager.getLogger();
    private SQLConnection sqlConnection=SQLConnection.getInstance();
    private PreparedStatement statement;
    private ResultSet resultSet;
    private enum SqlCommand{
        CREATE,UPDATE,GET_BY_ID,DELETE, GET_ALL, GET_ALL_IN_LIMIT,GET_LAST_INSERTED_ID;
        SqlCommand(String command){
            this.command=command;
        }
        SqlCommand(){}

        private String command;

        public void setCommand(String command) {
            this.command = command;
        }

        public String getCommand(){
            return command;
        }
    }

    public UserDAO() throws PropertyVetoException {
        StringBuffer command=new StringBuffer();
        String tableName = "users";
        String idField = "id_users";


        command.append("INSERT ").append(tableName).append(" (login,password,type,avatar_id) ").
                append("VALUES(?,?,?,?);");
        SqlCommand.CREATE.setCommand(command.toString());
        command=new StringBuffer();
        command.append("UPDATE ").append(tableName).append(" SET login= ?, password =? ,type =?,avatar_id =? ").
                append("WHERE ").append(idField).append("= ? ;");
        SqlCommand.UPDATE.setCommand(command.toString());
        command=new StringBuffer();
        command.append("DELETE FROM ").append(tableName).append(" WHERE ").append(idField).append("= ? ;");
        SqlCommand.DELETE.setCommand(command.toString());
        command=new StringBuffer();
        command.append("SELECT * FROM ").append(tableName).append(" WHERE ").append(idField).append(" = ? ;");
        SqlCommand.GET_BY_ID.setCommand(command.toString());
        command=new StringBuffer();
        command.append("SELECT * FROM ").append(tableName).append(" ;");
        SqlCommand.GET_ALL.setCommand(command.toString());
        command=new StringBuffer();
        command.append("SELECT * FROM ").append(tableName).append(" LIMIT ?, ? ;");
        SqlCommand.GET_ALL_IN_LIMIT.setCommand(command.toString());
        command=new StringBuffer();
        command.append("SELECT * FROM ").append(tableName).append(" WHERE ").append(idField).append("=LAST_INSERT_ID();");
        SqlCommand.GET_LAST_INSERTED_ID.setCommand(command.toString());
    }

    @Override
    public int create(User note) {
        try(Connection connection=sqlConnection.getConnection()) {
            statement=connection.prepareStatement(SqlCommand.CREATE.getCommand());
            statement.setString(1,note.getLogin());
            statement.setString(2,note.getPassword());
            statement.setString(3,note.getType().toString());
            statement.setInt(4,note.getAvatarId());
            if(statement.executeUpdate()==1){
                statement=connection.prepareStatement(SqlCommand.GET_LAST_INSERTED_ID.getCommand());
                resultSet=statement.executeQuery();
                if(resultSet.next())
                    return resultSet.getInt(1);
                else return -2;
            }
            else return -3;
        } catch (SQLException e) {
            logger.catching(e);
            return -4;
        }
    }

    @Override
    public int update(User note) {
        try(Connection connection=sqlConnection.getConnection()) {
            statement=connection.prepareStatement(SqlCommand.UPDATE.getCommand());
            statement.setString(1,note.getLogin());
            statement.setString(2,note.getPassword());
            statement.setString(3,note.getType().toString());
            statement.setInt(4,note.getAvatarId());
            statement.setInt(5,note.getId());
            if(statement.executeUpdate()==1){
                return 1;
            }
            else return -3;
        } catch (SQLException e) {
            logger.catching(e);
            return -4;
        }

    }

    @Override
    public int delete(User note) {
        try(Connection connection=sqlConnection.getConnection()) {
            statement=connection.prepareStatement(SqlCommand.DELETE.getCommand());
            statement.setInt(1,note.getId());
            if(statement.execute()){
                return 1;
            }
            else return -3;
        } catch (SQLException e) {
            logger.catching(e);
            return -4;
        }
    }

    @Override
    public User receive(User note) {
        try(Connection connection=sqlConnection.getConnection()) {
            statement=connection.prepareStatement(SqlCommand.GET_BY_ID.getCommand());
            statement.setInt(1,note.getId());
            resultSet=statement.executeQuery();
            if(resultSet.next()){
                return receiveDataFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.catching(e);
        }
        return new User.Builder().build();
    }

    @Override
    public Collection<User> receiveAll() {
        try(Connection connection=sqlConnection.getConnection()) {
            statement=connection.prepareStatement(SqlCommand.GET_ALL.getCommand());
            return receiveCollection(statement.executeQuery());
        } catch (SQLException e) {
            logger.catching(e);
            return new ConcurrentLinkedQueue<User>();
        }
    }

    @Override
    public Collection<User> receiveAll(int[] bound) {
        try(Connection connection=sqlConnection.getConnection()) {
            statement=connection.prepareStatement(SqlCommand.GET_ALL_IN_LIMIT.getCommand());
            statement.setInt(1,bound[0]);
            statement.setInt(2,bound[1]);
            return receiveCollection(statement.executeQuery());
        } catch (SQLException e) {
            logger.catching(e);
            return new ConcurrentLinkedQueue<User>();
        }
    }

    private Collection<User> receiveCollection(ResultSet resultSet){
        Collection<User> data=new ConcurrentLinkedQueue<>();
        User user;
        try {
            while (resultSet.next()){
                try {// allow get date, if there was invalid note
                    user = receiveDataFromResultSet(resultSet);
                    data.add(user);
                }
                catch (SQLException e) {
                    logger.catching(e);
                }
            }
        } catch (SQLException e) {
            logger.catching(e);
        }
        return data;
    }
    private User receiveDataFromResultSet(ResultSet resultSet) throws SQLException {
        User.Builder builder=new User.Builder();
        builder.setId(resultSet.getInt("id_users"));
        builder.setLogin(resultSet.getString("login"));
        builder.setPassword(resultSet.getString("password"));
        builder.setType(User.Type.valueOf(resultSet.getString("type")));
        builder.setLastUpdate(resultSet.getDate("last_update").toLocalDate());
        builder.setCreateDate(resultSet.getDate("create_date").toLocalDate());
        builder.setAvatarId(resultSet.getInt("avatar_id"));
        return builder.build();
    }
}
