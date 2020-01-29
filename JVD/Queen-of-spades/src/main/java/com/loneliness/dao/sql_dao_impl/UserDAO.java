package com.loneliness.dao.sql_dao_impl;


import com.loneliness.dao.DAOException;
import com.loneliness.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;


/*
   return :
   -2-error
   -3-invalid note
   -4-db error

   */


public class UserDAO extends SQLDAO<User> {
    protected enum Command{
        CREATE,UPDATE,GET_BY_ID, GET_BY_LOGIN_AND_PASSWORD,DELETE, GET_ALL, GET_ALL_IN_LIMIT,GET_LAST_INSERTED_ID;
        Command(String command){
            this.command=command;
        }
        Command(){}

        private String command;

        public void setCommand(String command) {
            this.command = command;
        }

        public String getCommand(){
            return command;
        }
    }
    public UserDAO() throws DAOException {
        super();
        StringBuffer command=new StringBuffer();
        String tableName = "users";
        String idField = "id_users";


        command.append("INSERT ").append(tableName).append(" (login,password,type,avatar_id) ").
                append("VALUES(?,?,?,?);");
        Command.CREATE.setCommand(command.toString());

        command=new StringBuffer();
        command.append("UPDATE ").append(tableName).append(" SET login= ?, password =? ,type =?,avatar_id =? ").
                append("WHERE ").append(idField).append("= ? ;");
        Command.UPDATE.setCommand(command.toString());

        command=new StringBuffer();
        command.append("DELETE FROM ").append(tableName).append(" WHERE ").append(idField).append("= ? ;");
        Command.DELETE.setCommand(command.toString());

        command=new StringBuffer();
        command.append("SELECT * FROM ").append(tableName).append(" WHERE ").append(idField).append(" = ? ;");
        Command.GET_BY_ID.setCommand(command.toString());

        command=new StringBuffer();
        command.append("SELECT * FROM ").append(tableName).append(" WHERE login").append(" = ? AND password= ? ;");
        Command.GET_BY_LOGIN_AND_PASSWORD.setCommand(command.toString());

        command=new StringBuffer();
        command.append("SELECT * FROM ").append(tableName).append(" ;");
        Command.GET_ALL.setCommand(command.toString());

        command=new StringBuffer();
        command.append("SELECT * FROM ").append(tableName).append(" LIMIT ?, ? ;");
        Command.GET_ALL_IN_LIMIT.setCommand(command.toString());

        command=new StringBuffer();
        command.append("SELECT * FROM ").append(tableName).append(" WHERE ").append(idField).append("=LAST_INSERT_ID();");
        Command.GET_LAST_INSERTED_ID.setCommand(command.toString());
    }

    @Override
    public int create(User note) throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.CREATE.getCommand());
            statement.setString(1,note.getLogin());
            statement.setString(2,note.getPassword());
            statement.setString(3,note.getType().toString());
            statement.setInt(4,note.getAvatarId());
            if(statement.executeUpdate()==1){
                statement=connection.prepareStatement(Command.GET_LAST_INSERTED_ID.getCommand());
                resultSet=statement.executeQuery();
                if(resultSet.next())
                    return resultSet.getInt(1);
                else return -2;
            }
            else return -3;
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_CREATE",e.getCause());
        }
    }

    @Override
    public int update(User note) throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.UPDATE.getCommand());
            statement.setString(1,note.getLogin());
            statement.setString(2,note.getPassword());
            statement.setString(3,note.getType().toString());
            statement.setInt(4,note.getAvatarId());
            statement.setInt(5,note.getId());
            if(statement.executeUpdate()==1){
                return note.getId();
            }
            else return -3;
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_UPDATE",e.getCause());
        }

    }

    @Override
    public int delete(User note) throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.DELETE.getCommand());
            statement.setInt(1,note.getId());
            if(statement.execute()){
                return 1;
            }
            else return -3;
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_DELETE",e.getCause());
        }
    }
    @Override
    public int delete(int note) throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.DELETE.getCommand());
            statement.setInt(1,note);
            if(statement.execute()){
                return 1;
            }
            else return -3;
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_DELETE",e.getCause());
        }
    }

    @Override
    public User receive(User note) throws DAOException {
        try (SQLConnection connection = new SQLConnection()) {
            if(note.getLogin()!=null&&note.getLogin().length()!=0) {
                statement = connection.prepareStatement(Command.GET_BY_LOGIN_AND_PASSWORD.getCommand());
                statement.setString(1, note.getLogin());
                statement.setString(2, note.getPassword());
            }
            else {
                statement = connection.prepareStatement(Command.GET_BY_ID.getCommand());
                statement.setInt(1, note.getId());
                resultSet = statement.executeQuery();
            }
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return receiveDataFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_RECEIVE", e.getCause());
        }
        return new User.Builder().build();
    }

    public  User receive(Integer id) throws DAOException {
        try (SQLConnection connection= new SQLConnection()) {
            statement = connection.prepareStatement(Command.GET_BY_ID.getCommand());
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return receiveDataFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_RECEIVE", e.getCause());
        }
        return new User.Builder().build();
    }

    @Override
    public Collection<User> receiveAll() throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.GET_ALL.getCommand());
            return receiveCollection(statement.executeQuery());
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_RECEIVE_ALL",e.getCause());
        }
    }

    @Override
    public Collection<User> receiveAll(Integer[] bound) throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.GET_ALL_IN_LIMIT.getCommand());
            statement.setInt(1,bound[0]);
            statement.setInt(2,bound[1]);
            return receiveCollection(statement.executeQuery());
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_RECEIVE_ALL_IN_LIMIT",e.getCause());
        }
    }

    protected User receiveDataFromResultSet(ResultSet resultSet) throws SQLException {
        return new User.Builder().setId(resultSet.getInt("id_users"))
                .setLogin(resultSet.getString("login"))
                .setPassword(resultSet.getString("password"))
                .setType(User.Type.valueOf(resultSet.getString("type")))
                .setLastUpdate(resultSet.getDate("last_update").toLocalDate())
                .setCreateDate(resultSet.getDate("create_date").toLocalDate())
                .setAvatarId(resultSet.getInt("avatar_id"))
                .build();
    }
}
