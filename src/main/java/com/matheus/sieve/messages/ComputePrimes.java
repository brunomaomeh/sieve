package com.matheus.sieve.messages;

import java.io.Serializable;

/**
 * Created by matheus on 06/02/2016.
 */
public final class ComputePrimes implements Serializable {
    private Integer value;

    public ComputePrimes(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
