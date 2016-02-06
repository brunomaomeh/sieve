package com.matheus.sieve;

import akka.actor.UntypedActor;
import com.matheus.sieve.messages.ComputePrimes;
import com.matheus.sieve.messages.ComputePrimesResult;

import java.util.BitSet;

/**
 * Created by matheus on 06/02/2016.
 */
public class SieveWorker extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ComputePrimes){
            ComputePrimes computePrimes = (ComputePrimes) message;
            Integer result = computeSieve(computePrimes.getValue());
            sender().tell(new ComputePrimesResult(result), self());
        }
    }

    /**
     * Compute all primes up to 'size' parameter. The algorithm used is the Sieve of Erathostenes.
     * Firstly is created a sequence of all numbers up to n. So we begin from 2 marking all multiples
     * of 2. So we repeat to first non marked number in previous step (the number 3). We repeat this algorithm
     * to all numbers less than square root of N. After this steps, all non marked numbers are primes
     * @param n size of the sieve
     * @return how many prime there is in the sieve
     */
    private Integer computeSieve(Integer n){
        BitSet sieve = new BitSet(n);
        for(int i = 2; i < Math.sqrt(n); i++){
            if (!sieve.get(i-1))
                for(int j = 2*i; j <= n; j += i)
                    sieve.set(j-1);
        }
        Integer count = 0;
        for(int i = 0; i < n; i++)
            if(!sieve.get(i))
                count++;

        return count;
    }
}
