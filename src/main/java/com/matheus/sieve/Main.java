package com.matheus.sieve;

import akka.actor.*;
import com.matheus.sieve.messages.CountMatrixOfPrimes;
import com.matheus.sieve.messages.CountMatrixOfPrimesResult;
import com.matheus.sieve.messages.CountPrimes;

import java.util.Random;

/**
 * Created by matheus on 10/02/2016.
 */
public class Main {

    public static int[][] randomMatrix(int x, int y, int n){
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int[][] matrix = new int[x][y];
        for(int i = 0; i < matrix.length; i++)
            for(int j = 0; j < matrix[i].length; j++)
                matrix[i][j] = Math.abs(random.nextInt()) % n;
        return matrix;
    }

    public static void main(String[] args){
        ActorSystem system = ActorSystem.create();
        ActorRef countPrimesWorker = system.actorOf(Props.create(CountPrimesWorker.class));
        ActorRef countPrimesMaster = system.actorOf(Props.create(CountPrimesMaster.class, countPrimesWorker));

        class Benchmark extends UntypedActor{
            private ActorRef countPrimesMaster;
            private long begin;

            private long now(){
                return System.nanoTime();
            }

            public Benchmark(ActorRef countPrimesMaster) {
                this.countPrimesMaster = countPrimesMaster;
            }

            @Override
            public void onReceive(Object message) throws Exception {
                if (message instanceof CountMatrixOfPrimes){
                    begin = now();
                    countPrimesMaster.tell(message, context().self());
                }else if(message instanceof CountMatrixOfPrimesResult){
                    long end = now();
                    long timeElapsed = (end - begin) / 1000000;
                    System.out.println("Time elapsed (ms): " + timeElapsed);
                }

            }
        }

        ActorRef benchmark = system.actorOf(Props.create(Benchmark.class, countPrimesMaster));
        CountMatrixOfPrimes countPrimes = new CountMatrixOfPrimes(randomMatrix(1000, 1000, 1000));
        benchmark.tell(countPrimes, ActorRef.noSender());
    }
}
