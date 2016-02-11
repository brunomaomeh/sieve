package com.matheus.sieve.messages;

import akka.actor.ActorRef;

/**
 * Created by matheus on 09/02/2016.
 */
public class Computing implements CountPrimesMasterData {
    private ActorRef sender;
    private int[][] matrix;
    private int count = 0;

    public Computing(ActorRef sender, int x, int y){
        this.sender = sender;
        matrix = new int[x][y];
    }

    public void setMatrixElement(int x, int y, int n){
        matrix[x][y] = n;
        count++;
    }

    public boolean computationIsDone(){
        return count == matrix.length * matrix[0].length;
    }

    public ActorRef getSender() {
        return sender;
    }

    public int[][] getMatrix(){
        return matrix;
    }

}
