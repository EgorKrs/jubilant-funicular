package com.loneliness.dao.sql_dao_impl;

import com.loneliness.dao.DAOException;
import com.loneliness.dao.WorkWithUserDAO;
import com.loneliness.entity.Account;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
/**
 * класс для получения данных @see com.loneliness.Account из sql базы данных
 * @author Egor Krasouski
 *
 */
public class AccountDAO extends SQLDAO<Account> implements WorkWithUserDAO<Account> {

    public AccountDAO() throws DAOException {
        super();

        StringBuffer command=new StringBuffer();
        String tableName = "accounts";
        String idField = "id_accounts";


        command.append("INSERT ").append(tableName).append(" (user_id,number,sum_of_money) ").
                append("VALUES(?,?,?);");
        Command.CREATE.setCommand(command.toString());
        command=new StringBuffer();

        command.append("UPDATE ").append(tableName).append(" SET user_id= ?, number =? ,sum_of_money =? ").
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

        command = new StringBuffer();
        command.append("SELECT * FROM ").append(tableName).append(" WHERE user_id=?;");
        Command.GET_BY_USER_ID.setCommand(command.toString());

        command.append("UPDATE ").append(tableName).append(" SET sum_of_money =? ").
                append("WHERE user_id = ? ;");
        Command.UPDATE_SUM_OF_MONEY_BY_USER_ID.setCommand(command.toString());

        command = new StringBuffer();
        command.append("SELECT * FROM ").append(tableName).append(" WHERE user_id = ? ;");
        Command.RECEIVE_BY_USER_ID.setCommand(command.toString());
    }

    public Account getByUserId(Integer id) throws DAOException {
        try (SQLConnection connection = new SQLConnection()) {
            statement = connection.prepareStatement(Command.GET_BY_USER_ID.getCommand());
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return receiveDataFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_RECEIVE_BY_USER_ID", e.getCause());
        }
        return new Account.Builder().build();
    }

    @Override
    public int create(Account note) throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.CREATE.getCommand());
            statement.setInt(1,note.getUserID());
            statement.setString(2,note.getCreditCardNumber());
            statement.setBigDecimal(3,note.getSumOfMoney());
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
    public int update(Account note) throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.UPDATE.getCommand());
            statement.setInt(1,note.getUserID());
            statement.setString(2,note.getCreditCardNumber());
            statement.setBigDecimal(3,note.getSumOfMoney());
            statement.setInt(4,note.getId());
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
    public int delete(Account note) throws DAOException {
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
    public Account receive(Account note) throws DAOException {
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
        return new Account.Builder().build();
    }
    @Override
    public Account receive(Integer note) throws DAOException {
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
        return new Account.Builder().build();
    }

    @Override
    public Collection<Account> receiveAll() throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.GET_ALL.getCommand());
            return receiveCollection(statement.executeQuery());
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_RECEIVE_ALL",e.getCause());
        }
    }

    @Override
    public Collection<Account> receiveAll(Integer[] bound) throws DAOException {
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

    public Integer updateSunOfMoney(Integer userId, BigDecimal sumOfMoney) throws DAOException {
        try (SQLConnection connection = new SQLConnection()) {
            statement = connection.prepareStatement(Command.UPDATE_SUM_OF_MONEY_BY_USER_ID.getCommand());
            statement.setBigDecimal(1, sumOfMoney);
            statement.setInt(2, userId);
            if (statement.executeUpdate() == 1) {
                return 1;
            } else return -3;
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_UPDATE_SUM_OF_MONEY", e.getCause());
        }
    }

    @Override
    public Account receiveByUserId(Integer id) throws DAOException {
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
        return new Account.Builder().build();
    }

    protected enum Command {
        CREATE, UPDATE, GET_BY_ID, DELETE, GET_ALL, GET_ALL_IN_LIMIT, GET_LAST_INSERTED_ID, GET_BY_USER_ID,
        UPDATE_SUM_OF_MONEY_BY_USER_ID, RECEIVE_BY_USER_ID;

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


    protected Account receiveDataFromResultSet(ResultSet resultSet) throws SQLException {
        return new Account.Builder().setId(resultSet.getInt("id_accounts"))
                .setUserID(resultSet.getInt("user_id"))
                .setNumber(resultSet.getString("number"))
                .setLastUpdate(resultSet.getDate("last_update").toLocalDate())
                .setSumOfMoney(resultSet.getBigDecimal("sum_of_money")).build();
    }
}
