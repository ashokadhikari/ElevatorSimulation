package com.elevatorsimulation.entities;

import java.util.ArrayList;
import com.elevatorsimulation.Message.Direction;
import com.elevatorsimulation.Message.Message;
import com.elevatorsimulation.Message.Request;

public class ElevatorControl {

  private int numRacks;

  private int numFloors;

  private int elevatorCapacity;

  private int elevatorRestingFloor;

  private ArrayList<Elevator> elevators;

  public ElevatorControl(int numRacks, int numFloors, int elevatorCapacity,
      int elevatorRestingFloor) {
    this.numRacks = numRacks;
    this.numFloors = numFloors;
    this.elevatorCapacity = elevatorCapacity;
    this.elevatorRestingFloor = elevatorRestingFloor;

    elevators = new ArrayList<Elevator>(this.numRacks);

    for (int i = 0; i < this.numRacks; ++i) {
      elevators.add(new Elevator(elevatorCapacity, elevatorRestingFloor, numFloors));
    }
  }

  /*
   * handleRequest - Find out the best elevator and send it the request.
   * @param floor - number of floor the user wants to go.
   * @param direction - the direction user wants to go.
   * TODO: Add logic to check for elevator capacity. If the best elevator is already full,
   * select the second best one
   */
  public void handleRequest(Request request) {
    Elevator bestElevator = null;
    int minimum_distance = Integer.MAX_VALUE;
    for (Elevator elevator : this.elevators) {
      int distance = computeElevatorDistance(request, elevator);
      if (distance < minimum_distance) {
        minimum_distance = distance;
        bestElevator = elevator;
      }
    }
    Message message = new Message();
    message.setRequestFloor(request.getFloor());
    if (bestElevator != null) {
      if (bestElevator.getDirection() == Direction.UP && bestElevator.getCurrentFloor() > request
          .getFloor()) {
        message.setDestinationListDirection(Direction.DOWN);
      } else if (bestElevator.getDirection() == Direction.DOWN
          && bestElevator.getCurrentFloor() < request.getFloor()) {
        message.setDestinationListDirection(Direction.UP);
      } else {
        message.setDestinationListDirection(request.getDirection());
      }
      bestElevator.sendMessage(message);
    }
  }

  /*
   * computeElevatorDistance Computes the distance for current floor and direction to the given
   * elevator.
   * @param floor
   * @param direction
   * @param elevator - the elevator to compute distance with
   */
  public int computeElevatorDistance(Request request, Elevator elevator) {
    if (elevator.getDirection() == Direction.UP) {
      if (request.getFloor() > elevator.getCurrentFloor()) {
        return request.getFloor() - elevator.getCurrentFloor();
      } else {
        System.out.println(elevator.getDestinationsUp().getLastDestination());
        return 2 * (elevator.getDestinationsUp().getLastDestination()) - elevator.getCurrentFloor()
            - request.getFloor();
      }
    } else if (elevator.getDirection() == Direction.DOWN) {
      if (request.getFloor() < elevator.getCurrentFloor()) {
        return elevator.getCurrentFloor() - request.getFloor();
      } else {
        return elevator.getCurrentFloor() + request.getFloor() - 2 * (elevator.getDestinationsDown()
            .getLastDestination());
      }
    } else {
      if (request.getFloor() < elevator.getCurrentFloor()) {
        return elevator.getCurrentFloor() - request.getFloor();
      } else {
        return request.getFloor() - elevator.getCurrentFloor();
      }
    }
  }

  // Getters and setters
  public int getNumRacks() {
    return numRacks;
  }

  public int getNumFloors() {
    return numFloors;
  }

  public int getElevatorCapacity() {
    return elevatorCapacity;
  }

  public int getElevatorRestingFloor() {
    return elevatorRestingFloor;
  }

  public ArrayList<Elevator> getElevators() {
    return elevators;
  }
}
