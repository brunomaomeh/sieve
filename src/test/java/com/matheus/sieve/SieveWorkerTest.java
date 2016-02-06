package com.matheus.sieve;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import com.matheus.sieve.messages.ComputePrimes;
import com.matheus.sieve.messages.ComputePrimesResult;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by matheus on 06/02/2016.
 */
public class SieveWorkerTest {
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
            ActorRef sieveWorker = system.actorOf(Props.create(SieveWorker.class));
            sieveWorker.tell(new ComputePrimes(4), getRef());
            ComputePrimesResult result = expectMsgClass(ComputePrimesResult.class);
            Assert.assertEquals(3, result.getResult());
        }};
    }

    @Test
    public void must_find_four_primes_up_to_ten(){

        new JavaTestKit(system){{
            ActorRef sieveWorker = system.actorOf(Props.create(SieveWorker.class));
            sieveWorker.tell(new ComputePrimes(10), getRef());
            ComputePrimesResult result = expectMsgClass(ComputePrimesResult.class);
            Assert.assertEquals(4, result.getResult());
        }};
    }

    @Test
    public void must_find_nine_primes_up_to_twenty(){
        new JavaTestKit(system){{
            ActorRef sieveWorker = system.actorOf(Props.create(SieveWorker.class));
            sieveWorker.tell(new ComputePrimes(20), getRef());
            ComputePrimesResult result = expectMsgClass(ComputePrimesResult.class);
            Assert.assertEquals(9, result.getResult());
        }};
    }
}
