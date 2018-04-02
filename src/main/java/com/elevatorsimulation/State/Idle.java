package com.elevatorsimulation.State;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.elevatorsimulation.Message.Direction;
import com.elevatorsimulation.Message.Message;
import com.elevatorsimulation.entities.Elevator;

public class Idle implements IState<Elevator> {

  private static final Logger logger = Logger.getLogger(Idle.class.getName());
  private static Idle instance = null;

  private Idle() {

  }

  public static Idle getInstance() {
    if (instance == null) {
      instance = new Idle();
    }
    return instance;
  }

  public void enter(Elevator owner) {
    logger.log(Level.INFO, "Idle State: Entering Idle State.");
    owner.setDirection(Direction.NONE);
  }

  public void execute(Elevator owner) {
    logger.log(Level.INFO, "Idle State: Entering Idle State.");
  }

  public void exit(Elevator owner) {
    logger.log(Level.INFO, "Idle State: Entering Idle State.");
  }

  public void onMessage(Elevator owner, Message message) {
    logger.log(Level.INFO, "Idle State: Received message.");
    if (message.getRequestFloor() == owner.getCurrentFloor()) {
      owner.getStateMachine().changeState(Loading.getInstance());
    } else if (message.getRequestFloor() > owner.getCurrentFloor()) {
      owner.getDestinationsUp().addDestination(message.getRequestFloor());
      owner.setDirection(Direction.UP);
      owner.getStateMachine().changeState(Moving.getInstance());
    } else {
      owner.getDestinationsDown().addDestination(message.getRequestFloor());
      owner.setDirection(Direction.DOWN);
      owner.getStateMachine().changeState(Moving.getInstance());
    }
  }
}
