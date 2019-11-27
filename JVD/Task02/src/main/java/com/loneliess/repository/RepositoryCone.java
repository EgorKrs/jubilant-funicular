package com.loneliess.repository;

import com.loneliess.Parser;
import com.loneliess.entity.Cone;
import com.loneliess.resource_provider.PathManager;
import com.loneliess.servise.ConeService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class RepositoryCone implements IRepository<Cone>{
    private Logger logger= LogManager.getLogger();
    private HashMap<Integer,Cone> data=new HashMap<>();
    private DataAccess dataAccess=DataAccess.getInstance();
    private Parser parser=new Parser();

    public Cone getData(Integer key){
        return data.get(key);
    }
    private String dataFile=PathManager.getConeDataFile();

    public boolean isContain(Cone cone){
        return data.containsValue(cone);
    }


    public  boolean AddALL(Collection<Cone> cones){
        int counter=0;
        for (Cone cone :
                cones) {
            data.put(cone.getId(),cone);
            counter++;
        }
        return counter==cones.size();
    }

    public Collection<Cone> getData() {
        return data.values();
    }

    public HashMap<Integer, Cone> loadAllCones() throws RepositoryException {
        HashMap<Integer,Cone> data=new HashMap<>();
        Cone cone;
        try (BufferedReader reader= dataAccess.getReadConnectionToFile(dataFile)){
            String line;
            while ((line=reader.readLine())!=null){
                cone=parser.parse(line);
                if(cone!=null){
                    data.put(cone.getId(),cone);
                }
            }
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR,e);
            throw new RepositoryException(e,e.getExceptionMessage());
        } catch (IOException e) {
            logger.catching(Level.ERROR,e);
            throw new RepositoryException(e,e.getMessage());
        }
        return data;
    }

    @Override
    public boolean add(Cone ob) throws RepositoryException {
        try (BufferedWriter writer=dataAccess.getAppendWriteConnectionToFile(dataFile)){
            writer.write(ConeService.getInstance().splitToCoordinate(ob));
            return true;
        } catch (RepositoryException e) {
            throw new RepositoryException(e,e.getExceptionMessage());
        } catch (IOException e) {
            throw new RepositoryException(e,e.getMessage());
        }
    }


    @Override
    public boolean addAll(Collection<Cone> ob) throws RepositoryException {
        try (BufferedWriter writer=dataAccess.getWriteConnectionToFile(dataFile)){
            for (Cone cone :
                    ob) {
                writer.write(ConeService.getInstance().splitToCoordinate(cone)+"\n");
            }
            return true;
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR,e);
            throw new RepositoryException(e,e.getExceptionMessage());
        } catch (IOException e) {
            logger.catching(Level.ERROR,e);
            throw new RepositoryException(e,e.getMessage());
        }
    }

    @Override
    public boolean delete(Cone ob) throws RepositoryException {
        data.remove(ob.getId());
        return addAll(loadAllCones().values());
    }

    @Override
    public boolean update(Cone ob) throws RepositoryException {
        data.replace(ob.getId(),ob);
        return addAll(loadAllCones().values());
    }
}
