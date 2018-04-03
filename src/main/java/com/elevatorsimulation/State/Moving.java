package com.elevatorsimulation.State;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.elevatorsimulation.Message.Direction;
import com.elevatorsimulation.Message.Message;
import com.elevatorsimulation.entities.Elevator;

public class Moving implements IState<Elevator> {

  private static final Logger logger = Logger.getLogger(Moving.class.getName());
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
    logger.log(Level.INFO, "Moving State: Entering Moving State.");
  }

  public void execute(Elevator owner) {
    logger.log(Level.INFO, "Moving state: Executing moving state.");
    if (owner.getDirection() == Direction.UP) {
      owner.setCurrentFloor(owner.getCurrentFloor() + 1);
      logger.log(Level.INFO, "Moving state: Reached floor: " + owner.getCurrentFloor());
      if (owner.getCurrentFloor() + 1 > owner.getNumFloors()) {
        logger.log(Level.INFO, "Moving state: Reached top floor, Reverting direction.");
        owner.setDirection(Direction.DOWN);
      }
      if (owner.getDestinationsUp().isEmpty()) {
        logger.log(Level.INFO, "Moving State: No more UP destinations. Reverting direction");
        owner.setDirection(Direction.DOWN);
      }
      if (owner.getDestinationsUp().getDestinationList().contains(owner.getCurrentFloor())) {
        logger.log(Level.INFO,
            "Moving state: Destination floor reached, floor = " + owner.getCurrentFloor());
        owner.destinationReachedEvent(owner.getCurrentFloor());
        owner.getDestinationsUp().removeNextDestination();
        owner.getStateMachine().changeState(Loading.getInstance());
      }
    } else {
      owner.setCurrentFloor(owner.getCurrentFloor() - 1);
      logger.log(Level.INFO, "Moving state: Reached floor: " + owner.getCurrentFloor());
      if (owner.getCurrentFloor() - 1 < 0) {
        logger.log(Level.INFO, "Moving state: Reached bottom floor, Reverting direction.");
        owner.setDirection(Direction.UP);
      }
      if (owner.getDestinationsDown().isEmpty()) {
        logger.log(Level.INFO, "Moving State: No more DOWN destinations. Reverting direction");
        owner.setDirection(Direction.UP);
      }
      if (owner.getDestinationsDown().getDestinationList().contains(owner.getCurrentFloor())) {
        logger.log(Level.INFO,
            "Moving state: Destination floor reached, floor = " + owner.getCurrentFloor());
        owner.destinationReachedEvent(owner.getCurrentFloor());
        owner.getDestinationsDown().removeNextDestination();
        owner.getStateMachine().changeState(Loading.getInstance());
      }
    }
  }

  public void exit(Elevator owner) {
    logger.log(Level.INFO, "Moving State: Exiting Moving State.");
  }

  public void onMessage(Elevator owner, Message message) {
    logger.log(Level.INFO, "Moving State: Received message.");
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
