package com.loneliess.repository;

import com.loneliess.entity.Cone;
import com.loneliess.subscriber.ConeRegistrar;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RepositoryConeRegistrar implements IRepository<ConeRegistrar<Cone>, Map<Integer,ConeRegistrar<Cone>>>{
    private Map<Integer,ConeRegistrar<Cone>> coneRegistrarMap=new ConcurrentHashMap<>();

    @Override
    public Map<Integer, ConeRegistrar<Cone>> getMap() throws RepositoryException {
        return coneRegistrarMap;
    }

    @Override
    public boolean addNode(ConeRegistrar<Cone> ob) throws RepositoryException {
        return coneRegistrarMap.put(ob.getId(),ob)!=null;
    }

    @Override
    public boolean save(Map<Integer, ConeRegistrar<Cone>> ob) throws RepositoryException {
        int counter=0;
        for (ConeRegistrar<Cone> cone :
                ob.values()) {
            addNode(cone);
            counter++;
        }
        return counter==ob.size();
    }

    @Override
    public boolean delete(ConeRegistrar<Cone> ob) throws RepositoryException {
        return coneRegistrarMap.remove(ob.getId(),ob);
    }

    @Override
    public boolean update(ConeRegistrar<Cone> ob) throws RepositoryException {
        return coneRegistrarMap.put(ob.getId(),ob)!=null;
    }
}
