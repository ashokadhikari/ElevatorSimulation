package com.elevatorsimulation.State;

import com.elevatorsimulation.Message.Direction;
import com.elevatorsimulation.Message.Message;
import com.elevatorsimulation.entities.Elevator;

public class Moving implements IState<Elevator> {

  private static Moving instance = null;

  private Moving() {
  }

  public static Moving getInstance() {
    if (instance == null) {
      instance = new Moving();
    }
    return instance;
  }

  public void enter(Elevator owner) {
    System.out.println("Entering Moving state.");
  }

  public void execute(Elevator owner) {
    System.out.println("Executing Moving state.");
    if (owner.getDirection() == Direction.UP) {
      System.out.println("Going to floor " + owner.getDestinationsUp().getNextDestination());
      owner.setCurrentFloor(owner.getDestinationsUp().removeNextDestination());
      System.out.println("Reached floor " + owner.getCurrentFloor());
    } else {
      System.out.println("Going to floor " + owner.getDestinationsDown().getNextDestination());
      owner.setCurrentFloor(owner.getDestinationsDown().removeNextDestination());
      System.out.println("Reached floor " + owner.getCurrentFloor());
    }
  }

  public void exit(Elevator owner) {
    System.out.println("Exiting Moving state.");
  }

  public void onMessage(Elevator owner, Message message) {
    System.out.println("Received message while on Moving state, (Floor: " + message.getRequestFloor()
        + " Direction: " + message.getDestinationListDirection() + ")");
    switch (message.getDestinationListDirection()) {
      case UP:
        owner.getDestinationsUp().addDestination(message.getRequestFloor());
        break;

      case DOWN:
        owner.getDestinationsDown().addDestination(message.getRequestFloor());
        break;

      default:
        break;
    }
  }
}
