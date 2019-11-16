package com.loneless;

public class Job09 implements Execute{
    @Override
    public Object execute(Object obj) {
        int k=(int)((double[][])obj)[0][0];
        double[] res=new double[((double[][])obj)[1].length+((double[][])obj)[2].length];
            for(int j=0,i=0;i<((double[][])obj)[1].length;j++,i++){
                if(j==k){
                    j=copy(((double[][])obj)[2],k,res);
                    j--;
                    i--;
                }
                else {
                    res[j]=((double[][])obj)[1][i];
                }
            }
        return res;
    }
    private int copy(double[] b,int pos,double[] res){
        int i=pos;
        for(int j=0;j<b.length;i++,j++){
            res[i]=b[j];
        }
        return i;
    }
}
