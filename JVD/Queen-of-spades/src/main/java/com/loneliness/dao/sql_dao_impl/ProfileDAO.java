package com.loneliness.dao.sql_dao_impl;

import com.loneliness.dao.DAOException;
import com.loneliness.dao.WorkWithUserDAO;
import com.loneliness.entity.Language;
import com.loneliness.entity.Profile;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
/**
 * класс для получения данных @see com.loneliness.Profile из sql базы данных
 * @author Egor Krasouski
 *
 */
public class ProfileDAO extends SQLDAO<Profile> implements WorkWithUserDAO<Profile> {
    public ProfileDAO()  {
        super();

        StringBuffer command = new StringBuffer();
        String tableName = "profiles";
        String idField = "profile_id";

        command.append("INSERT ").append(tableName).append(" (user_id, language, rating, about, score, " +
                "number_of_defeats, number_of_victories, number_of_game) ").
                append("VALUES(?,?,?,?,?,?,?,?);");
        Command.CREATE.setCommand(command.toString());

        command = new StringBuffer();
        command.append("UPDATE ").append(tableName).append(" SET user_id= ?, language =? , rating =?, score =?,about =?, " +
                " number_of_defeats =?, number_of_victories =?,number_of_game =? ").
                append("WHERE ").append(idField).append("= ? ;");
        Command.UPDATE.setCommand(command.toString());

        command = new StringBuffer();
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

        command = new StringBuffer();
        command.append("SELECT * FROM ").append(tableName).append(" WHERE user_id=?;");
        Command.GET_PROFILE_BY_USER_ID.setCommand(command.toString());

        command = new StringBuffer();
        command.append("UPDATE ").append(tableName).append(" SET score =?").
                append("WHERE user_id= ? ;");
        Command.UPDATE_SCORE.setCommand(command.toString());

        command = new StringBuffer();
        command.append("SELECT * FROM ").append(tableName).append(" WHERE user_id = ? ;");
        Command.RECEIVE_BY_USER_ID.setCommand(command.toString());

    }

    public Integer updateScore(Integer userId, BigDecimal score) throws DAOException {
        try (SQLConnection connection = new SQLConnection()) {
            statement = connection.prepareStatement(Command.UPDATE_SCORE.getCommand());
            statement.setBigDecimal(1, score);
            statement.setInt(2, userId);
            if (statement.executeUpdate() == 1) {
                return 1;
            } else return -3;
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_UPDATE_SCORE", e.getCause());
        }
    }

    @Override
    public int create(Profile note) throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement = connection.prepareStatement(Command.CREATE.getCommand());
            statement.setInt(1, note.getUserID());
            statement.setString(2, note.getLanguage().toString());
            statement.setInt(3, note.getRating());
            statement.setBigDecimal(5, note.getScore());
            statement.setString(4, note.getAbout());
            statement.setInt(6, note.getNumberOfDefeats().get());
            statement.setInt(7, note.getNumberOfVictories().get());
            statement.setInt(8, note.getNumberOfGame().get());
            if (statement.executeUpdate() == 1) {
                statement = connection.prepareStatement(Command.GET_LAST_INSERTED_ID.getCommand());
                resultSet = statement.executeQuery();
                if (resultSet.next())
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
            statement.setInt(4, note.getScore().intValue());
            statement.setString(5, note.getAbout());
            statement.setInt(6, note.getNumberOfDefeats().get());
            statement.setInt(7, note.getNumberOfVictories().get());
            statement.setInt(8, note.getNumberOfGame().get());
            statement.setInt(9, note.getId());
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
    public Collection<Profile> receiveAll(Integer[] bound) throws DAOException {
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

    public Profile getProfileByUserId(Integer userID) throws DAOException {
        try (SQLConnection connection = new SQLConnection()) {
            statement = connection.prepareStatement(Command.GET_PROFILE_BY_USER_ID.getCommand());
            statement.setInt(1, userID);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return receiveDataFromResultSet(resultSet);
            }
        } catch (SQLException | DAOException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_RECEIVE", e.getCause());
        }
        return new Profile.Builder().build();
    }

    @Override
    public Profile receiveByUserId(Integer id) throws DAOException {
        try (SQLConnection connection = new SQLConnection()) {
            statement = connection.prepareStatement(Command.RECEIVE_BY_USER_ID.getCommand());
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return receiveDataFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_RECEIVE_BY_USER_ID", e.getCause());
        }
        return new Profile.Builder().build();
    }

    protected Profile receiveDataFromResultSet(ResultSet resultSet) throws SQLException {
        return new Profile.Builder().setId(resultSet.getInt("profile_id")).
                setUserID(resultSet.getInt("user_id")).
                setRating(resultSet.getInt("rating")).
                setLanguage(Language.valueOf(resultSet.getString("language"))).
                setLastUpdate(resultSet.getDate("last_update").toLocalDate()).
                setScore(resultSet.getBigDecimal("score")).
                setAbout(resultSet.getString("about")).
                setNumberOfDefeats(resultSet.getInt("number_of_defeats")).
                setNumberOfVictories(resultSet.getInt("number_of_victories")).
                setNumberOfGame(resultSet.getInt("number_of_game")).
                build();
    }

    protected enum Command {
        CREATE, UPDATE, GET_BY_ID, DELETE, GET_ALL, GET_ALL_IN_LIMIT, GET_LAST_INSERTED_ID, GET_PROFILE_BY_USER_ID,
        UPDATE_SCORE, RECEIVE_BY_USER_ID;

        Command(String command) {
            this.command = command;
        }

        Command() {
        }

        private String command;

        public void setCommand(String command) {
            this.command = command;
        }

        public String getCommand() {
            return command;
        }
    }
}
