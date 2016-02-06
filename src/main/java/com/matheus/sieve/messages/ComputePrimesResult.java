package com.matheus.sieve.messages;

/**
 * Created by matheus on 06/02/2016.
 */
public final class ComputePrimesResult {
    private int result;

    public ComputePrimesResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }
}
