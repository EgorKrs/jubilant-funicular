package com.loneless;

public class Job10 implements Execute{
    @Override
    public Object execute(Object obj) {
        int n = (int) obj;
        int[][] matrix = new int[n][n];
        int v = 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                v=0;
            } else {
                v=n+1;
            }
            for (int j = 0; j < n; j++) {
                if (i % 2 == 0) {
                    v++;
                } else {
                    v--;
                }
                matrix[i][j] = v;
            }

        }
        return matrix;
    }
}
