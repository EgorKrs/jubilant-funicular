package com.loneliness.dao.sql_dao_impl;

import com.loneliness.dao.DAOException;
import com.loneliness.entity.Language;
import com.loneliness.entity.Profile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class ProfileDAO extends SQLDAO<Profile> {
    protected enum Command{
        CREATE,UPDATE,GET_BY_ID,DELETE, GET_ALL, GET_ALL_IN_LIMIT,GET_LAST_INSERTED_ID;
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
    public ProfileDAO() throws DAOException {
        super();

        StringBuffer command=new StringBuffer();
        String tableName = "profiles";
        String idField = "profile_id";

        command.append("INSERT ").append(tableName).append(" (user_id, language, rating, telegram, instagram, about," +
                "number_of_defeats,number_of_victories,number_of_game) ").
                append("VALUES(?,?,?,?,?,?,?,?,?);");
        Command.CREATE.setCommand(command.toString());

        command=new StringBuffer();
        command.append("UPDATE ").append(tableName).append(" SET user_id= ?, language =? , rating =?, telegram =?, instagram =?,about =?, " +
                "number_of_defeats =?, number_of_victories =?,number_of_game =? ").
                append("WHERE ").append(idField).append("= ? ;");
        Command.UPDATE.setCommand(command.toString());

        command=new StringBuffer();
        command.append("DELETE FROM ").append(tableName).append(" WHERE ").append(idField).append("= ? ;");
        Command.DELETE.setCommand(command.toString());

        command=new StringBuffer();
        command.append("SELECT * FROM ").append(tableName).append(" WHERE ").append(idField).append(" = ? ;");
        Command.GET_BY_ID.setCommand(command.toString());

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
    public int create(Profile note) throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.CREATE.getCommand());
            statement.setInt(1,note.getUserID());
            statement.setString(2,note.getLanguage().toString());
            statement.setInt(3,note.getRating());
            statement.setString(4,note.getTelegram());
            statement.setString(5,note.getInstagram());
            statement.setString(6,note.getAbout());
            statement.setInt(7,note.getNumberOfDefeats().get());
            statement.setInt(8,note.getNumberOfVictories().get());
            statement.setInt(9,note.getNumberOfGame().get());
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
    public int update(Profile note) throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.UPDATE.getCommand());
            statement.setInt(1,note.getUserID());
            statement.setString(2,note.getLanguage().toString());
            statement.setInt(3,note.getRating());
            statement.setString(4,note.getTelegram());
            statement.setString(5,note.getInstagram());
            statement.setString(6,note.getAbout());
            statement.setInt(7,note.getNumberOfDefeats().get());
            statement.setInt(8,note.getNumberOfVictories().get());
            statement.setInt(9,note.getNumberOfGame().get());
            statement.setInt(10,note.getId());
            if(statement.executeUpdate()==1){
                return 1;
            }
            else return -3;
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_UPDATE",e.getCause());
        }
    }

    @Override
    public int delete(Profile note) throws DAOException {
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
    public Profile receive(Profile note) throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.GET_BY_ID.getCommand());
            statement.setInt(1,note.getId());
            resultSet=statement.executeQuery();
            if(resultSet.next()){
                return receiveDataFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_RECEIVE",e.getCause());
        }
        return new Profile.Builder().build();
    }
    @Override
    public Profile receive(Integer note) throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.GET_BY_ID.getCommand());
            statement.setInt(1,note);
            resultSet=statement.executeQuery();
            if(resultSet.next()){
                return receiveDataFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_RECEIVE",e.getCause());
        }
        return new Profile.Builder().build();
    }

    @Override
    public Collection<Profile> receiveAll() throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.GET_ALL.getCommand());
            return receiveCollection(statement.executeQuery());
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_RECEIVE_ALL",e.getCause());
        }
    }

    @Override
    public Collection<Profile> receiveAll(int[] bound) throws DAOException {
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
    protected Profile receiveDataFromResultSet(ResultSet resultSet) throws SQLException {
        return new Profile.Builder().setId(resultSet.getInt("profile_id")).
                setUserID(resultSet.getInt("user_id")).
                setRating(resultSet.getInt("rating")).
                setLanguage(Language.valueOf(resultSet.getString("language"))).
                setLastUpdate(resultSet.getDate("last_update").toLocalDate()).
                setTelegram(resultSet.getString("telegram")).
                setInstagram(resultSet.getString("instagram")).
                setAbout(resultSet.getString("about")).
                setNumberOfDefeats(resultSet.getInt("number_of_defeats")).
                setNumberOfVictories(resultSet.getInt("number_of_victories")).
                setNumberOfGame(resultSet.getInt("number_of_game")).
                build();
    }
}
