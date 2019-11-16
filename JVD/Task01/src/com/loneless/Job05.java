package com.loneless;

public class Job05 implements Execute{
    @Override
    public Object execute(Object obj) {
        for (int i=0;i<3;i++){
            if(((double[])obj)[i]>0){
                ((double[])obj)[i]=Math.pow(((double[])obj)[i],2);
            }
            else if(((double[])obj)[i]<0){
                ((double[])obj)[i]=Math.pow(((double[])obj)[i],4);
            }
        }
        return obj;
    }
}
