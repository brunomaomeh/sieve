package com.matheus.sieve.messages;

/**
 * Created by matheus on 09/02/2016.
 */
public final class CountMatrixOfPrimes {
    private int[][] matrix;

    public CountMatrixOfPrimes(int[][] matrix) {
        this.matrix = matrix;
    }

    public int[][] getMatrix() {
        return matrix;
    }
}
