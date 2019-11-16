package com.loneliess.entity;

import com.loneliess.controller.CommandProvider;
import com.loneliess.controller.ControllerException;

import java.util.HashMap;

public class ConeMap {
    private HashMap<Integer,Cone> data=new HashMap<>();
    public Cone getData(Integer key){
        return data.get(key);
    }
    private static  ConeMap instance=new ConeMap();

    public  HashMap<Integer, Cone> getData() {
        return data;
    }
    public  void setData(HashMap<Integer, Cone>cones){
        data.putAll(cones);
    }
    ConeMap() {    }

    public static ConeMap getInstance() {
        return instance;
    }
}
