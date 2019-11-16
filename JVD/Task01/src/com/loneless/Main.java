package com.loneless;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        Execute job=new Job01();
        System.out.println((boolean) job.execute(1234));
        double[] arg={1,2,3};
        job=new Job02();
        System.out.println(job.execute(arg));
        arg=new double[]{2,4};
        job=new Job03();
        System.out.println("S= "+((double[])job.execute(arg))[0]+"; P= "+((double[])job.execute(arg))[1]);
        arg=new double[]{-1,-1};
        job=new Job04();
        System.out.println((boolean)job.execute(arg));
        arg=new double[]{-2,2,1};
        job=new Job05();
        System.out.println(Arrays.toString((double[]) job.execute(arg)));
        arg=new double[]{-2,2,1};
        job=new Job06();
        System.out.println("min= "+((double[])job.execute(arg))[0]+"; max= "+((double[])job.execute(arg))[1]);
        arg=new double[]{-2,3,1};
        job=new Job07();
        System.out.println(Arrays.deepToString((double[][]) job.execute(arg)));

        job=new Job08();
        System.out.println(job.execute(new double[][]{{2},{1,2,3,4,5,6,7,8,9,10}}));   // ячейка [0][0] число кратности, [1] массив для проверки

        job=new Job09();
        System.out.println(Arrays.toString((double[])job.execute(new double[][]{{2},{1,2,3,4,5,6,7,8,9,10},{1,2,3,4,5,6,7,8,9,10}})));   // ячейка [0][0] число между которым вставлять, [1] массив 1,[2] массив 2

        job=new Job10();
        System.out.println(Arrays.deepToString((int[][])job.execute(6)));
    }
}
