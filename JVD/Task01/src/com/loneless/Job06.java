package com.loneless;

public class Job06 implements Execute{
    @Override
    public Object execute(Object obj) {
        double min=((double[])obj)[0];
        double max=((double[])obj)[0];
        for (double val :
                (double[]) obj) {
            if (min > val) {
                min=val;
            } else if(max<val){
                max=val;
            }
        }
        return new double[]{min,max};
    }
}
