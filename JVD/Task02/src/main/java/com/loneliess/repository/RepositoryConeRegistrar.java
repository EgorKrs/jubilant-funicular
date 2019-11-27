package com.loneliess.repository;

import com.loneliess.entity.Cone;
import com.loneliess.subscriber.ConeRegistrar;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RepositoryConeRegistrar implements IRepository<ConeRegistrar<Cone>>{
    private Map<Integer,ConeRegistrar<Cone>> coneRegistrarMap=new ConcurrentHashMap<>();


    public Map<Integer, ConeRegistrar<Cone>> getConeRegistrarMap() {
        return coneRegistrarMap;
    }

    public void setConeRegistrarMap(Map<Integer, ConeRegistrar<Cone>> coneRegistrarMap) {
        this.coneRegistrarMap = coneRegistrarMap;
    }

    @Override
    public boolean add(ConeRegistrar<Cone> ob) throws RepositoryException {
        return coneRegistrarMap.put(ob.getId(),ob)!=null;
    }

    @Override
    public boolean addAll(Collection<ConeRegistrar<Cone>> ob) throws RepositoryException {
        int counter=0;
        for (ConeRegistrar<Cone> cone :
                ob) {
            add(cone);
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
