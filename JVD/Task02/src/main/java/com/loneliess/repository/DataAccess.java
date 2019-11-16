package com.loneliess.repository;

import com.loneliess.entity.Cone;

import java.io.*;

public class DataAccess {
    private DataAccess(){}
    private static final DataAccess instance =new DataAccess();
    public static DataAccess getInstance(){
        return instance;
    }
    public BufferedReader getReadConnectionToFile(String fileName) throws RepositoryException {
        try {
            return new BufferedReader(new FileReader(new File(fileName)));
        } catch (FileNotFoundException e) {
            throw new RepositoryException(e,"Ошибка доступа к файлу "+fileName);
        }
    }
    public BufferedWriter getAppendWriteConnectionToFile(String fileName) throws RepositoryException {
        try {
            return new BufferedWriter(new FileWriter(new File(fileName),true));
        } catch (FileNotFoundException e) {
            throw new RepositoryException(e,"Ошибка доступа к файлу "+fileName);
        } catch (IOException e) {
            throw new RepositoryException(e,"Ошибка чтения "+fileName);
        }
    }
    public BufferedWriter getWriteConnectionToFile(String fileName) throws RepositoryException {
        try {
            return new BufferedWriter(new FileWriter(new File(fileName),false));
        } catch (FileNotFoundException e) {
            throw new RepositoryException(e,"Ошибка доступа к файлу "+fileName);
        } catch (IOException e) {
            throw new RepositoryException(e,"Ошибка чтения "+fileName);
        }
    }
}
