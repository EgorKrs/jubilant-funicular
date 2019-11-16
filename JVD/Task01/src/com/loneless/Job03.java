package com.loneless;

public class Job03 implements Execute{
    @Override
    public Object execute(Object obj) {
        double a=((double[])obj)[0];
        double b=((double[])obj)[1];
        double S=0.5*a*b;
        double c=Math.pow(Math.pow(a,2)+Math.pow(b,2),0.5);
        double P=a+b+c;
        return new double[]{S,P};
    }
}
