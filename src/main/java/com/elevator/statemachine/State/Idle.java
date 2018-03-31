package com.elevator.statemachine.State;

import com.com.elevatorsimulation.Message.Message;
import com.elevatorsimulation.entities.Elevator;

public class Idle implements IState<Elevator> {

    public static Idle instance;

    private Idle() {

    }

    public static Idle getInstance() {
        if (instance == null) {
            instance = new Idle();
        }
        return instance;
    }

    public void enter(Elevator owner) {
        System.out.println("Entering idle state.");
    }

    public void execute(Elevator owner) {
        System.out.println("Executing idle state.");
    }

    public void exit(Elevator owner) {
        System.out.println("Exiting idle state.");
    }

    public void onMessage(Elevator owner, Message message) {
        System.out.println("Reveived message.");
    }
}
