package com.matheus.sieve.messages;

import java.io.Serializable;

/**
 * Created by matheus on 06/02/2016.
 */
public final class CountPrimesResult implements Serializable{
    private static final long serialVersionUID = 4783813194370363041L;
    private int x, y, result;

    public CountPrimesResult(int x, int y, int result) {
        this.x = x;
        this.y = y;
        this.result = result;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getResult() {
        return result;
    }
}
