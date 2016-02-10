package com.matheus.sieve;

import akka.actor.AbstractFSM;
import akka.actor.ActorRef;
import com.matheus.sieve.messages.*;

/**
 * Created by matheus on 09/02/2016.
 */
public class CountPrimesMaster extends AbstractFSM<CountPrimesMasterState, CountPrimesMasterData> {
    private ActorRef countPrimesWorker;

    public CountPrimesMaster(ActorRef countPrimesWorker) {
        this.countPrimesWorker = countPrimesWorker;
    }

    {
        startWith(CountPrimesMasterState.Idle, Uninitialized.Uninitialized);

        when(CountPrimesMasterState.Idle,
                matchEvent(CountMatrixOfPrimes.class, Uninitialized.class,
                        (count, uninitialized) -> {
                            for(int i = 0; i < count.getMatrix().length; i++)
                                for(int j = 0; j < count.getMatrix()[i].length; j++)
                                    // for each element in the matrix, count the number of primes not greater than the element
                                    countPrimesWorker.tell(new CountPrimes(i,j, count.getMatrix()[i][j]), context().self());
                            return goTo(CountPrimesMasterState.Busy).using(new Computing(sender(), count.getMatrix().length, count.getMatrix()[0].length));
                        }));
        when(CountPrimesMasterState.Busy,
                matchEvent(CountPrimesResult.class, Computing.class,
                        (result, computing) -> {
                            computing.setMatrixElement(result.getX(), result.getY(), result.getResult());
                            if(computing.computationIsDone()){
                                computing.getSender().tell(new CountMatrixOfPrimesResult(computing.getMatrix()),context().self());
                                return goTo(CountPrimesMasterState.Idle).using(Uninitialized.Uninitialized);
                            } else{
                                return stay().using(computing);
                            }
                        }));

    }

}
