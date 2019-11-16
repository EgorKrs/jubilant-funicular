package com.loneless;

public class Job04 implements Execute {
    @Override
    public Object execute(Object obj) {
        double[] point=(double[])obj;
        if(point[1]>2||point[1]<-1.5) {
            return false;
        }
        else if(point[0]>2||point[0]<-2) {
            return false;
        }
        else if(point[0]>1&&point[1]>0){
            return false;
        }
        else return !(point[0] > -1) || !(point[1] > 0);
    }
}
