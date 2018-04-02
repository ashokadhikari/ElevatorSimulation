package com.elevatorsimulation.entities;

import java.util.ArrayList;
import com.elevatorsimulation.Message.Direction;
import com.elevatorsimulation.Message.Message;

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
      elevators.add(new Elevator(i, elevatorCapacity, elevatorRestingFloor));
    }
  }

  /*
   * handleRequest - Find out the best elevator and send it the request.
   * @param floor - number of floor the user wants to go.
   * @param direction - the direction user wants to go.
   * TODO: Add logic to check for elevator capacity. If the best elevator is already full,
   * select the second best one
   */
  public void handleRequest(int floor, Direction direction) {
    Elevator bestElevator = null;
    int minimum_distance = Integer.MAX_VALUE;
    for (Elevator elevator : this.elevators) {
      int distance = computeElevatorDistance(floor, direction, elevator);
      if (distance < minimum_distance) {
        minimum_distance = distance;
        bestElevator = elevator;
      }
    }
    if (bestElevator != null) {
      bestElevator.sendMessage(new Message(floor, direction));
    }
  }

  /*
   * computeElevatorDistance Computes the distance for current floor and direction to the given
   * elevator.
   * @param floor
   * @param direction
   * @param elevator - the elevator to compute distance with
   */
  public int computeElevatorDistance(int floor, Direction direction, Elevator elevator) {
    if (elevator.getDirection() == Direction.UP) {
      if (floor > elevator.getCurrentFloor()) {
        return floor - elevator.getCurrentFloor();
      } else {
        System.out.println(elevator.getDestinationsUp().getLastDestination());
        return 2 * (elevator.getDestinationsUp().getLastDestination()) - elevator.getCurrentFloor()
            - floor;
      }
    } else if (elevator.getDirection() == Direction.DOWN) {
      if (floor < elevator.getCurrentFloor()) {
        return elevator.getCurrentFloor() - floor;
      } else {
        return elevator.getCurrentFloor() + floor - 2 * (elevator.getDestinationsDown()
            .getLastDestination());
      }
    } else {
      if (floor < elevator.getCurrentFloor()) {
        return elevator.getCurrentFloor() - floor;
      } else {
        return floor - elevator.getCurrentFloor();
      }
    }
  }

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
