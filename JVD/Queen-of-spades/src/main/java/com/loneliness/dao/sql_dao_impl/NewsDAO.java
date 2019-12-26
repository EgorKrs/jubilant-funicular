package com.loneliness.dao.sql_dao_impl;

import com.loneliness.entity.News;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NewsDAO extends SQLDAO<News>{

    private enum Command{
        CREATE,UPDATE,GET_BY_ID,DELETE, GET_ALL, GET_ALL_IN_LIMIT,GET_LAST_INSERTED_ID;
        Command(){}

        private String command;

        public void setCommand(String command) {
            this.command = command;
        }

        public String getCommand(){
            return command;
        }
    }

    public NewsDAO() throws PropertyVetoException {
        StringBuffer command=new StringBuffer();
        String tableName = "news";
        String idField = "id_news";


        command.append("INSERT ").append(tableName).append(" (text) ").
                append("VALUES(?);");
        Command.CREATE.setCommand(command.toString());
        command=new StringBuffer();

        command.append("UPDATE ").append(tableName).append(" SET text= ? ").
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
    public int create(News note) {
        try(Connection connection=sqlConnection.getConnection()) {
            statement=connection.prepareStatement(Command.CREATE.getCommand());
            statement.setString(1,note.getText());
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
    public int update(News note) {
        try(Connection connection=sqlConnection.getConnection()) {
            statement=connection.prepareStatement(Command.UPDATE.getCommand());
            statement.setString(1,note.getText());
            statement.setInt(2,note.getId());
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
    public int delete(News note) {
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
    public News receive(News note) {
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
        return new News.Builder().build();
    }

    @Override
    public Collection<News> receiveAll() {
        try(Connection connection=sqlConnection.getConnection()) {
            statement=connection.prepareStatement(Command.GET_ALL.getCommand());
            return receiveCollection(statement.executeQuery());
        } catch (SQLException e) {
            logger.catching(e);
            return new ConcurrentLinkedQueue<News>();
        }
    }

    @Override
    public Collection<News> receiveAll(int[] bound) {
        try(Connection connection=sqlConnection.getConnection()) {
            statement=connection.prepareStatement(Command.GET_ALL_IN_LIMIT.getCommand());
            statement.setInt(1,bound[0]);
            statement.setInt(2,bound[1]);
            return receiveCollection(statement.executeQuery());
        } catch (SQLException e) {
            logger.catching(e);
            return new ConcurrentLinkedQueue<News>();
        }
    }

    @Override
    protected News receiveDataFromResultSet(ResultSet resultSet) throws SQLException {
        News.Builder builder = new News.Builder();
        builder.setId(resultSet.getInt("id_news"))
                .setText(resultSet.getString("text"))
                .setLast_update(resultSet.getDate("last_update").toLocalDate());
        return builder.build();
    }
}
