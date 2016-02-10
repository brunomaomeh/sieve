package com.matheus.sieve;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import akka.testkit.TestProbe;
import com.matheus.sieve.messages.CountMatrixOfPrimes;
import com.matheus.sieve.messages.CountMatrixOfPrimesResult;
import com.matheus.sieve.messages.CountPrimes;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by matheus on 09/02/2016.
 */
public class CountPrimesMasterTest {
    static ActorSystem system;

    @BeforeClass
    public static void setup(){
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown(){
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void must_generate_four_count_primes_request_to_a_2_x_2_matrix(){
        new JavaTestKit(system) {
            {
                int[][] matrix = new int[2][2];
                matrix[0][0] = 1;
                matrix[0][1] = 2;
                matrix[1][0] = 3;
                matrix[1][1] = 4;

                ActorRef countPrimesMaster = system.actorOf(Props.create(CountPrimesMaster.class, getRef()));
                countPrimesMaster.tell(new CountMatrixOfPrimes(matrix), ActorRef.noSender());

                expectMsgAllOf(new CountPrimes(0, 0, 1), new CountPrimes(0, 1, 2), new CountPrimes(1,0,3), new CountPrimes(1,1,4));
            }
        };

    }

    @Test
    public void must_count_primes_to_a_2_2_matrix(){
        new JavaTestKit(system){
            {
                int[][] matrix = new int[2][2];
                matrix[0][0] = 10; // 1 2 3 5 7 ( 5 primes)
                matrix[0][1] = 15; // 1 2 3 5 7 11 13 (7 primes)
                matrix[1][0] = 20; // 1 2 3 5 7 11 13 17 19 (9 primes)
                matrix[1][1] = 25; // 1 2 3 5 7 11 13 17 19 23 (10 primes)
                CountMatrixOfPrimes countMatrixOfPrimes = new CountMatrixOfPrimes(matrix);

                int[][] result = new int[2][2];
                result[0][0] = 5;
                result[0][1] = 7;
                result[1][0] = 9;
                result[1][1] = 10;
                CountMatrixOfPrimesResult countMatrixOfPrimesResult = new CountMatrixOfPrimesResult(result);

                ActorRef countPrimesWorker = system.actorOf(Props.create(CountPrimesWorker.class));
                ActorRef countPrimesMaster = system.actorOf(Props.create(CountPrimesMaster.class, countPrimesWorker));
                countPrimesMaster.tell(countMatrixOfPrimes, getRef());

                expectMsgEquals(countMatrixOfPrimesResult);

            }
        };
    }
}
