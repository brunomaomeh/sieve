package com.matheus.sieve;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import com.matheus.sieve.messages.CountPrimes;
import com.matheus.sieve.messages.CountPrimesResult;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by matheus on 06/02/2016.
 */
public class CountPrimesWorkerTest {
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
    public void must_find_three_primes_up_to_four(){
        new JavaTestKit(system){{
            ActorRef sieveWorker = system.actorOf(Props.create(CountPrimesWorker.class));
            sieveWorker.tell(new CountPrimes(4), getRef());
            CountPrimesResult result = expectMsgClass(CountPrimesResult.class);
            Assert.assertEquals(3, result.getResult());
        }};
    }

    @Test
    public void must_find_four_primes_up_to_ten(){

        new JavaTestKit(system){{
            ActorRef sieveWorker = system.actorOf(Props.create(CountPrimesWorker.class));
            sieveWorker.tell(new CountPrimes(10), getRef());
            CountPrimesResult result = expectMsgClass(CountPrimesResult.class);
            Assert.assertEquals(4, result.getResult());
        }};
    }

    @Test
    public void must_find_nine_primes_up_to_twenty(){
        new JavaTestKit(system){{
            ActorRef sieveWorker = system.actorOf(Props.create(CountPrimesWorker.class));
            sieveWorker.tell(new CountPrimes(20), getRef());
            CountPrimesResult result = expectMsgClass(CountPrimesResult.class);
            Assert.assertEquals(9, result.getResult());
        }};
    }
}
