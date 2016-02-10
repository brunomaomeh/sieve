package com.matheus.sieve.messages;

import java.util.Arrays;

/**
 * Created by matheus on 09/02/2016.
 */
public class CountMatrixOfPrimesResult {
    private int[][] matrix;

    public CountMatrixOfPrimesResult(int[][] matrix) {
        this.matrix = matrix;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountMatrixOfPrimesResult that = (CountMatrixOfPrimesResult) o;

        return Arrays.deepEquals(matrix, that.matrix);

    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(matrix);
    }
}
