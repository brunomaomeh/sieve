package com.matheus.sieve.messages;

import java.io.Serializable;

/**
 * Request to count the number of primes not greater than N.
 * This request is associated with a matrix of integers, so
 * the request has the value of n and the value of the indices
 * x and y of origin matrix
 */
public final class CountPrimes implements Serializable {
    private int x, y, n;

    public CountPrimes(int x, int y, int n) {
        this.x = x;
        this.y = y;
        this.n = n;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getN() {
        return n;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountPrimes that = (CountPrimes) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        return n == that.n;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + n;
        return result;
    }
}
