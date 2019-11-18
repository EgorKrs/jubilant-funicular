package com.loneliess.repository;

import com.loneliess.entity.Cone;
import com.loneliess.entity.ConeMap;
import com.loneliess.resource_provider.LogManager;
import com.loneliess.resource_provider.PathManager;
import com.loneliess.servise.ConeLogic;
import com.loneliess.servise.ServiceFactory;
import org.apache.logging.log4j.Level;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

public class RepositoryCone implements IRepository<Cone, HashMap<Integer,Cone>>{

    @Override
    public HashMap<Integer, Cone> getMap() throws RepositoryException {
        HashMap<Integer,Cone> data=new HashMap<>();
        try (BufferedReader reader= DataAccess.getInstance().getReadConnectionToFile(PathManager.getInstance().getConeDataFile())){
            String line;
            while ((line=reader.readLine())!=null){
                String[] arg =line.split(" ");
                Cone cone;
               if(arg.length<=10){
                   try {
                       cone = new Cone(Integer.parseInt(arg[0]), Double.parseDouble(arg[1]), Double.parseDouble(arg[2])
                               , Double.parseDouble(arg[3]), Double.parseDouble(arg[4]), Double.parseDouble(arg[5]), Double.parseDouble(arg[6]),
                               Double.parseDouble(arg[7]), Double.parseDouble(arg[8]), Double.parseDouble(arg[9]));
                       if (ServiceFactory.getInstance().getServiceValidation().validate(cone).size() == 0) {
                           data.put(cone.getId(), cone);
                       }
                   }
                   catch (NumberFormatException ex){
                       LogManager.getInstance().getLogger().catching(Level.INFO,ex);
                   }
               }
               else{
                   LogManager.getInstance().getLogger().error("силишком большая строка");
               }
            }
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().catching(Level.ERROR,e);
            throw new RepositoryException(e,e.getExceptionMessage());
        } catch (IOException e) {
            LogManager.getInstance().getLogger().catching(Level.ERROR,e);
            throw new RepositoryException(e,e.getMessage());
        }
        return data;
    }

    @Override
    public boolean addNode(Cone ob) throws RepositoryException {
        try (BufferedWriter writer=DataAccess.getInstance().getAppendWriteConnectionToFile(PathManager.getInstance().getConeDataFile())){
            writer.write(ConeLogic.getInstance().splitToCoordinate(ob));
            return true;
        } catch (RepositoryException e) {
            throw new RepositoryException(e,e.getExceptionMessage());
        } catch (IOException e) {
            throw new RepositoryException(e,e.getMessage());
        }
    }


    @Override
    public boolean save(HashMap<Integer,Cone> ob) throws RepositoryException {
        try (BufferedWriter writer=DataAccess.getInstance().getWriteConnectionToFile(PathManager.getInstance().getConeDataFile())){
            for (Cone cone :
                    ob.values()) {
                writer.write(ConeLogic.getInstance().splitToCoordinate(cone)+"\n");
            }
            return true;
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().catching(Level.ERROR,e);
            throw new RepositoryException(e,e.getExceptionMessage());
        } catch (IOException e) {
            LogManager.getInstance().getLogger().catching(Level.ERROR,e);
            throw new RepositoryException(e,e.getMessage());
        }
    }

    @Override
    public boolean delete(Cone ob) throws RepositoryException {
        ConeMap.getInstance().getData().remove(ob.getId());
        return save(ConeMap.getInstance().getData());
    }

    @Override
    public boolean update(Cone ob) throws RepositoryException {
        ConeMap.getInstance().getData().replace(ob.getId(),ob);
        return save(ConeMap.getInstance().getData());
    }
}
