package com.loneless;

public class Job08 implements Execute{
    @Override
    public Object execute(Object obj) {
        double sum=0;
        for (int i=0;i<((double[][])obj)[1].length;i++){
            if( ((double[][])obj)[1][i]%((double[][])obj)[0][0]==0){
                sum+=((double[][])obj)[1][i];
            }
        }
        return sum;
    }
}
