package com.loneliness.dao.sql_dao_impl;


import com.loneliness.dao.DAOException;
import com.loneliness.entity.Picture;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PictureDAO extends SQLDAO<Picture>{
    private String tableName = "pictures";
    private String idField = "id_pictures";
    protected enum Command{
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

    public PictureDAO() throws  DAOException {
        super();
        StringBuffer command=new StringBuffer();



        command.append("INSERT ").append(tableName).append(" (image,name) ").
                append("VALUES(?,?);");
        Command.CREATE.setCommand(command.toString());
        command=new StringBuffer();

        command.append("UPDATE ").append(tableName).append(" SET image= ?, name =? ").
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
    public int create(Picture note) throws DAOException {
        try (SQLConnection connection= new SQLConnection(); InputStream stream=getStream(note)) {
            if(stream!=null) {
                statement = connection.prepareStatement(Command.CREATE.getCommand());
                statement.setBinaryStream(1, stream, note.getByteImage().length);
                statement.setString(2, note.getName());
                if (statement.executeUpdate() == 1) {
                    statement = connection.prepareStatement(Command.GET_LAST_INSERTED_ID.getCommand());
                    resultSet = statement.executeQuery();
                    if (resultSet.next())
                        return resultSet.getInt(1);
                    else return -3;
                }
            }
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_CREATE",e.getCause());
        } catch (IOException e) {
            logger.catching(e);
            throw new DAOException("ERROR_WHILE_CREATE_IN_PICTURE_FILE",e.getCause());
        }
        return -2;
    }


    @Override
    public int update(Picture note) throws DAOException {
        try(SQLConnection connection= new SQLConnection(); InputStream stream=getStream(note)) {
            if(stream!=null) {
                statement = connection.prepareStatement(Command.UPDATE.getCommand());
                statement.setBinaryStream(1, stream, note.getByteImage().length);
                statement.setString(2, note.getName());
                statement.setInt(3, note.getId());
                if (statement.executeUpdate() == 1) {
                    return 1;
                } else return -3;
            }
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_UPDATE",e.getCause());
        } catch (IOException e) {
            throw new DAOException("ERROR_WHILE_UPDATE_IN_PICTURE_FILE",e.getCause());
        }
        return -2;
    }

    @Override
    public int delete(Picture note) throws DAOException {
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
    public Picture receive(Picture note) throws DAOException {
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
        return new Picture.Builder().build();
    }
    @Override
    public Picture receive(int note) throws DAOException {
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
        return new Picture.Builder().build();
    }

    @Override
    public Collection<Picture> receiveAll() throws DAOException {
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(Command.GET_ALL.getCommand());
            return receiveCollection(statement.executeQuery());
        } catch (SQLException e) {
            logger.catching(e);
            throw new DAOException("ERROR_IN_RECEIVE_ALL",e.getCause());
        }
    }

    @Override
    public Collection<Picture> receiveAll(int[] bound) throws DAOException {
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

    public Collection<Picture> receivePicturesInNews(Picture picture) throws DAOException {
        String request="SELECT * FROM pictures_in_news INNER JOIN "+tableName+" ON pictures_in_news.picture_id="+
                tableName+'.'+idField+" WHERE "+idField+" =?;";
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(request);
            statement.setInt(1,picture.getId());
            return receiveCollection(statement.executeQuery());
        } catch (SQLException e) {
            logger.catching(e);
            return new ConcurrentLinkedQueue<>();
        }
    }
    public Collection<Picture> receivePicturesInNews() throws DAOException {
        String request="SELECT * FROM pictures_in_news INNER JOIN "+tableName+" ON pictures_in_news.picture_id="+
                tableName+'.'+idField+';';
        try(SQLConnection connection= new SQLConnection()) {
            statement=connection.prepareStatement(request);
            return receiveCollection(statement.executeQuery());
        } catch (SQLException e) {
            logger.catching(e);
            return new ConcurrentLinkedQueue<>();
        }
    }


    @Override
    protected Picture receiveDataFromResultSet(ResultSet resultSet) throws SQLException {
        Picture.Builder builder = new Picture.Builder();
        builder.setId(resultSet.getInt("id_pictures"))
                .setLastUpdate(resultSet.getDate("last_update").toLocalDate())
                .setName(resultSet.getString("name"));
        InputStream stream = resultSet.getBinaryStream("image");
        try {
            builder.setByteImage(stream.readAllBytes());
        } catch (IOException e) {
            logger.catching(e);
            builder.setByteImage(new byte[1]);
        }
        return builder.build();
    }
    private InputStream getStream(Picture note){
        try {
            if (note.getByteImage().length > 1) {
                return new ByteArrayInputStream(note.getByteImage());

            } else {
                return new FileInputStream(new File(note.getName()));

            }
        } catch (FileNotFoundException e) {
            logger.catching(e);
            return null;
        }
    }

}
