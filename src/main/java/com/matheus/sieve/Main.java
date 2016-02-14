package com.matheus.sieve;

import akka.actor.*;
import akka.cluster.Cluster;
import akka.routing.FromConfig;
import com.matheus.sieve.messages.CountMatrixOfPrimes;
import com.matheus.sieve.messages.CountMatrixOfPrimesResult;
import com.matheus.sieve.messages.CountPrimes;

import java.util.Random;

/**
 * Created by matheus on 10/02/2016.
 */
public class Main {

    private static int[][] randomMatrix(int x, int y, int n){
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int[][] matrix = new int[x][y];
        for(int i = 0; i < matrix.length; i++)
            for(int j = 0; j < matrix[i].length; j++)
                matrix[i][j] = Math.abs(random.nextInt()) % n;
        return matrix;
    }

    private static int[][] generateMatrix(int x, int y, int n){
        int[][] matrix = new int[x][y];
        for(int i = 0; i < matrix.length; i++)
            for(int j = 0; j < matrix[i].length; j++)
                matrix[i][j] = n;
        return matrix;
    }

    public static void main(String[] args){
        ActorSystem system = ActorSystem.create("sieve");
        ActorRef countPrimesWorker = system.actorOf(FromConfig.getInstance().props(Props.create(CountPrimesWorker.class)), "CountPrimesWorker");

        Cluster.get(system).getSelfRoles().stream().filter(role -> role.equals("MASTER")).findFirst().ifPresent(x -> Cluster.get(system).registerOnMemberUp(() -> {
            ActorRef countPrimesMaster = system.actorOf(Props.create(CountPrimesMaster.class, countPrimesWorker), "CountPrimesMaster");
            CountMatrixOfPrimes countPrimes = new CountMatrixOfPrimes(randomMatrix(100, 100, 1000000));
            countPrimesMaster.tell(countPrimes, ActorRef.noSender());
        }));

    }
}
