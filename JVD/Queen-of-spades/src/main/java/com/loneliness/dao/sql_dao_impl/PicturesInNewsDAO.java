package com.loneliness.dao.sql_dao_impl;

import com.loneliness.dao.DAOException;
import com.loneliness.entity.PicturesInNews;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class PicturesInNewsDAO extends SQLDAO<PicturesInNews>{


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

    public PicturesInNewsDAO() throws DAOException {
        super();
        StringBuffer command=new StringBuffer();
        String tableName = "pictures_in_news";
        String idField = "id_pictures_in_news";


        command.append("INSERT ").append(tableName).append(" (news_id,picture_id) ").
                append("VALUES(?,?);");
        Command.CREATE.setCommand(command.toString());
        command=new StringBuffer();

        command.append("UPDATE ").append(tableName).append(" SET news_id= ?, picture_id =? ").
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
    public int create(PicturesInNews note) throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.CREATE.getCommand());
            statement.setInt(1,note.getNewsID());
            statement.setInt(2,note.getPictureID());
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
    public int update(PicturesInNews note) throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.UPDATE.getCommand());
            statement.setInt(1,note.getNewsID());
            statement.setInt(2,note.getPictureID());
            statement.setInt(3,note.getId());
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
    public int delete(PicturesInNews note) throws DAOException {
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
    public PicturesInNews receive(PicturesInNews note) throws DAOException {
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
        return new PicturesInNews.Builder().build();
    }
    @Override
    public PicturesInNews receive(int note) throws DAOException {
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
        return new PicturesInNews.Builder().build();
    }

    @Override
    public Collection<PicturesInNews> receiveAll() throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.GET_ALL.getCommand());
            return receiveCollection(statement.executeQuery());
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_RECEIVE_ALL",e.getCause());
        }
    }

    @Override
    public Collection<PicturesInNews> receiveAll(int[] bound) throws DAOException {
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
    @Override
    protected PicturesInNews receiveDataFromResultSet(ResultSet resultSet) throws SQLException {
        return new PicturesInNews.Builder().setId(resultSet.getInt("id_pictures_in_news"))
                .setNewsID(resultSet.getInt("news_id"))
                .setPictureID(resultSet.getInt("picture_id"))
                .build();
    }
}
