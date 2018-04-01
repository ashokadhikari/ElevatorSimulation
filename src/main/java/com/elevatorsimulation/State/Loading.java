package com.elevatorsimulation.State;

import com.elevatorsimulation.Message.Message;
import com.elevatorsimulation.entities.Elevator;

public class Loading implements IState<Elevator> {
    private static Loading instance = null;

    private Loading() {}

    public static Loading getInstance() {
        if (instance == null) {
            instance = new Loading();
        }
        return instance;
    }

    public void enter(Elevator owner) {
        System.out.println("Entering Loading state.");
    }

    public void execute(Elevator owner) {
        System.out.println("Executing Loading state.");
    }

    public void exit(Elevator owner) {
        System.out.println("Exiting Loading state.");
    }

    public void onMessage(Elevator owner, Message message) {
        System.out.println("onMessage Loading state.");
    }
}
