package com.loneliness.dao;

import com.loneliness.entity.Language;
import com.loneliness.entity.Profile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ProfileDAO implements DAO<Profile>{
    private Logger logger = LogManager.getLogger();
    private SQLConnection sqlConnection=SQLConnection.getInstance();
    private PreparedStatement statement;
    private ResultSet resultSet;
    private enum Command{
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
    public ProfileDAO() throws PropertyVetoException {
        StringBuffer command=new StringBuffer();
        String tableName = "profiles";
        String idField = "profile_id";


        command.append("INSERT ").append(tableName).append(" (user_id, language, rating, telegram, instagram, about) ").
                append("VALUES(?,?,?,?,?,?);");
        Command.CREATE.setCommand(command.toString());

        command=new StringBuffer();
        command.append("UPDATE ").append(tableName).append(" SET user_id= ?, language =? , rating =?, telegram =?, instagram =?,about =? ").
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
    public int create(Profile note) {
        try(Connection connection=sqlConnection.getConnection()) {
            statement=connection.prepareStatement(Command.CREATE.getCommand());
            statement.setInt(1,note.getUserID());
            statement.setString(2,note.getLanguage().toString());
            statement.setInt(3,note.getRating());
            statement.setString(4,note.getTelegram());
            statement.setString(5,note.getInstagram());
            statement.setString(6,note.getAbout());
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
            return -4;
        }
    }

    @Override
    public int update(Profile note) {
        try(Connection connection=sqlConnection.getConnection()) {
            statement=connection.prepareStatement(Command.UPDATE.getCommand());
            statement.setInt(1,note.getUserID());
            statement.setString(2,note.getLanguage().toString());
            statement.setInt(3,note.getRating());
            statement.setString(4,note.getTelegram());
            statement.setString(5,note.getInstagram());
            statement.setString(6,note.getAbout());
            statement.setInt(7,note.getId());
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
    public int delete(Profile note) {
        try(Connection connection=sqlConnection.getConnection()) {
            statement=connection.prepareStatement(Command.DELETE.getCommand());
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
    public Profile receive(Profile note) {
        try(Connection connection=sqlConnection.getConnection()) {
            statement=connection.prepareStatement(Command.GET_BY_ID.getCommand());
            statement.setInt(1,note.getId());
            resultSet=statement.executeQuery();
            if(resultSet.next()){
                return receiveDataFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.catching(e);
        }
        return new Profile.Builder().build();
    }

    @Override
    public Collection<Profile> receiveAll() {
        try(Connection connection=sqlConnection.getConnection()) {
            statement=connection.prepareStatement(Command.GET_ALL.getCommand());
            return receiveCollection(statement.executeQuery());
        } catch (SQLException e) {
            logger.catching(e);
            return new ConcurrentLinkedQueue<Profile>();
        }
    }

    @Override
    public Collection<Profile> receiveAll(int[] bound) {
        try(Connection connection=sqlConnection.getConnection()) {
            statement=connection.prepareStatement(Command.GET_ALL_IN_LIMIT.getCommand());
            statement.setInt(1,bound[0]);
            statement.setInt(2,bound[1]);
            return receiveCollection(statement.executeQuery());
        } catch (SQLException e) {
            logger.catching(e);
            return new ConcurrentLinkedQueue<Profile>();
        }
    }
    private Collection<Profile> receiveCollection(ResultSet resultSet){
        Collection<Profile> data=new ConcurrentLinkedQueue<>();
        Profile user;
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
    private Profile receiveDataFromResultSet(ResultSet resultSet) throws SQLException {
        Profile.Builder builder=new Profile.Builder();
        builder.setId(resultSet.getInt("profile_id"));
        builder.setUserID(resultSet.getInt("user_id"));
        builder.setRating(resultSet.getInt("rating"));
        builder.setLanguage(Language.valueOf(resultSet.getString("language")));
        builder.setLastUpdate(resultSet.getDate("last_update").toLocalDate());
        builder.setTelegram(resultSet.getString("telegram"));
        builder.setInstagram(resultSet.getString("instagram"));
        builder.setAbout(resultSet.getString("about"));
        return builder.build();
    }
}
