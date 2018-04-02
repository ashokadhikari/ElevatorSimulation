package com.elevatorsimulation.entities;

import java.util.ArrayList;
import com.elevatorsimulation.Message.Direction;
import com.elevatorsimulation.Message.Message;
import com.elevatorsimulation.State.Idle;
import com.elevatorsimulation.State.StateMachine;

public class Elevator implements IElevatorObservable{

  // Number of people this elevator can hold.
  private int capacity;

  // The current floor the elevator is at.
  private int currentFloor;

  // The total number of floors.
  private int numFloors;

  // The direction the elevator is going.
  private Direction direction;

  // Number of passengers inside the elevator.
  private int numPassengers;

  private StateMachine<Elevator> stateMachine;

  // List containing sorted list of destinations.
  private Destinations destinationsUp;
  private Destinations destinationsDown;

  private ArrayList<IElevatorObserver> observers;

  public Elevator(int capacity, int currentFloor, int numFloors) {
    this.capacity = capacity;
    this.currentFloor = currentFloor;
    this.numFloors = numFloors;
    this.direction = Direction.NONE;
    this.stateMachine = new StateMachine<Elevator>(this);
    this.stateMachine.setCurrentState(Idle.getInstance());
    this.destinationsDown = new Destinations(true);
    this.destinationsUp = new Destinations(false);
    observers = new ArrayList<IElevatorObserver>();
  }

  /* Implementation of observer methods. */
  public void addObserver(IElevatorObserver observer) {
    observers.add(observer);
  }

  private void notifyDestinationReachedEvent(int destination) {
    for (IElevatorObserver observer: observers) {
      observer.onDestinationReachedEvent(destination);
    }
  }

  public boolean isFull() {
    return numPassengers > capacity;
  }

  /* Getters and Setters */

  public int getCurrentFloor() {
    return currentFloor;
  }

  public void setCurrentFloor(int currentFloor) {
    this.currentFloor = currentFloor;
  }

  public int getNumFloors() {
    return numFloors;
  }

  public void setNumFloors(int numFloors) {
    this.numFloors = numFloors;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public Destinations getDestinationsUp() {
    return destinationsUp;
  }

  public Destinations getDestinationsDown() {
    return destinationsDown;
  }

  public StateMachine getStateMachine() {
    return stateMachine;
  }

  /* Message handling methods */

  public void handleRequest(int floor) {
    Message message = new Message(floor, Direction.NONE);
    this.sendMessage(message);
  }

  public void sendMessage(Message message) {
    stateMachine.onMessage(message);
  }

  /* Implement Interface methods */

  public void destinationReachedEvent(int destination) {
    this.notifyDestinationReachedEvent(destination);
  }
}
