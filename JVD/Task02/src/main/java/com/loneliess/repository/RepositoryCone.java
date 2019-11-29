package com.loneliess.repository;

import com.loneliess.ConeParser;
import com.loneliess.entity.Cone;
import com.loneliess.resource_provider.PathManager;
import com.loneliess.servise.ConeService;
import com.loneliess.servise.ServiceFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RepositoryCone implements IRepository<Cone> {
    private Logger logger = LogManager.getLogger();
    private HashMap<Integer, Cone> data = new HashMap<>();
    private DataAccess dataAccess ;
    private ConeParser coneParser ;
    private String dataFile ;
    private ConeService service ;

    public RepositoryCone(DataAccess dataAccess,ConeParser coneParser,String dataFile,ConeService service){
        this.dataAccess=dataAccess;
        this.coneParser=coneParser;
        this.dataFile=dataFile;
        this.service=service;
    }

    public Cone getData(Integer key) {
        return data.get(key);
    }

    public Map<Integer, Cone> getConeByParamH(double left, double right) {
        Map<Integer, Cone> cones = new HashMap<>();
        for (Cone cone :
                data.values()) {
            if (cone.getH() > left && cone.getH() < right) {
                cones.put(cone.getId(), cone);
            }
        }
        return cones;
    }

    public Map<Integer, Cone> getConeByParamL(double left, double right) {
        Map<Integer, Cone> cones = new HashMap<>();
        for (Cone cone :
                data.values()) {
            if (cone.getL() > left && cone.getL() < right) {
                cones.put(cone.getId(), cone);
            }
        }
        return cones;
    }

    public Map<Integer, Cone> getConeByParamR(double left, double right) {
        Map<Integer, Cone> cones = new HashMap<>();
        for (Cone cone :
                data.values()) {
            if (cone.getR() > left && cone.getR() < right) {
                cones.put(cone.getId(), cone);
            }
        }
        return cones;
    }

    public boolean isContain(Cone cone) {
        return data.containsValue(cone);
    }


    public boolean AddALL(Collection<Cone> cones) {
        int counter = 0;
        for (Cone cone :
                cones) {
            data.put(cone.getId(), cone);
            counter++;
        }
        return counter == cones.size();
    }

    public Collection<Cone> getData() {
        return data.values();
    }

    public HashMap<Integer, Cone> loadAllCones() throws RepositoryException {
        HashMap<Integer, Cone> data = new HashMap<>();
        Cone cone;
        try (BufferedReader reader = dataAccess.getReadConnectionToFile(dataFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                cone = coneParser.parse(line);
                if (cone != null) {
                    data.put(cone.getId(), cone);
                }
            }
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e, e.getExceptionMessage());
        } catch (IOException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e, e.getMessage());
        }
        return data;
    }

    @Override
    public boolean add(Cone ob) throws RepositoryException {
        try (BufferedWriter writer = dataAccess.getAppendWriteConnectionToFile(dataFile)) {
            writer.write(service.splitToCoordinate(ob));
            return true;
        } catch (RepositoryException e) {
            throw new RepositoryException(e, e.getExceptionMessage());
        } catch (IOException e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }


    @Override
    public boolean addAll(Collection<Cone> ob) throws RepositoryException {
        try (BufferedWriter writer = dataAccess.getWriteConnectionToFile(dataFile)) {
            for (Cone cone :
                    ob) {
                writer.write(service.splitToCoordinate(cone) + "\n");
            }
            return true;
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e, e.getExceptionMessage());
        } catch (IOException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public boolean delete(Cone ob) throws RepositoryException {
        data.remove(ob.getId());
        return addAll(loadAllCones().values());
    }

    @Override
    public boolean update(Cone ob) throws RepositoryException {
        data.replace(ob.getId(), ob);
        return addAll(loadAllCones().values());
    }
}
