package com.loneless;

public class Job02 implements Execute{
    @Override
    public Object execute(Object arg) {
        double[] num=(double[]) arg;
        double a=num[0];
        double b=num[1];
        double c=num[2];
        return (b+Math.pow((Math.pow(b,2)+4*a*c),0.5))/2*a-Math.pow(a,3)*c+Math.pow(b,-3);
    }
}
