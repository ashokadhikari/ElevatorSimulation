package com.elevatorsimulation.State;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.elevatorsimulation.Message.Message;
import com.elevatorsimulation.entities.Elevator;

public class Loading implements IState<Elevator> {

  private static final Logger logger = Logger.getLogger(Loading.class.getName());
  private static Loading instance = null;

  private Loading() {
  }

  public static Loading getInstance() {
    if (instance == null) {
      instance = new Loading();
    }
    return instance;
  }

  public void enter(Elevator owner) {
    logger.log(Level.INFO, "Loading State: Entering Loading State.");
  }

  public void execute(Elevator owner) {
    logger.log(Level.INFO, "Loading State: Executing Loading State.");
    if (owner.getDestinationsUp().isEmpty() && owner.getDestinationsDown().isEmpty()) {
      logger.log(Level.INFO,
          "Loading state: No requests in destination lists, switching to Idle State.");
      owner.getStateMachine().changeState(Idle.getInstance());
    } else {
      owner.getStateMachine().changeState(Moving.getInstance());
    }
  }

  public void exit(Elevator owner) {
    logger.log(Level.INFO, "Loading State: Exiting Loading State.");

  }

  public void onMessage(Elevator owner, Message message) {
    logger.log(Level.INFO, "Loading State: Received message");
  }
}
