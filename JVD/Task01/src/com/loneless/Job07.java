package com.loneless;

import java.util.HashMap;
import java.util.Map;

public class Job07 implements Execute{
    @Override
    public Object execute(Object obj) {
        double a=((double[])obj)[0];
        double b=((double[])obj)[1];
        double h=((double[])obj)[2];
        double[][] res =new double[((int)((b-a)/h))][2];
        for(int i=0;a<b;a+=h,i++){
            res[i][0]=a;
            res[i][1]=(Math.pow(Math.sin(a),2)-Math.cos(2*a));
        }
        return res;
    }
}
