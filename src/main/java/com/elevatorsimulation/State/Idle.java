package com.elevatorsimulation.State;

import com.elevatorsimulation.Message.Message;
import com.elevatorsimulation.entities.Elevator;

public class Idle implements IState<Elevator> {

  private static Idle instance = null;

  private Idle() {

  }

  public static Idle getInstance() {
    if (instance == null) {
      instance = new Idle();
    }
    System.out.println("Getting instance from Idle.");
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
