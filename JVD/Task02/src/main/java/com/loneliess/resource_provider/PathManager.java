package com.loneliess.resource_provider;

public class PathManager {
    private static final PathManager instance=new PathManager();
    private PathManager(){}

    public static PathManager getInstance() {
        return instance;
    }
    public String getConeDataFile(){return "data\\ConeData.txt";}
}
