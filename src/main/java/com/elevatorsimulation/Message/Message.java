package com.elevatorsimulation.Message;

public class Message {

  private int requestFloor;

  // Represents the list to which the request floor needs to be added
  private Direction destinationListDirection;

  public Message() {

  }

  public Message(int requestFloor, Direction destinationListDirection) {
    this.requestFloor = requestFloor;
    this.destinationListDirection = destinationListDirection;
  }

  public int getRequestFloor() {
    return requestFloor;
  }

  public void setRequestFloor(int requestFloor) {
    this.requestFloor = requestFloor;
  }

  public Direction getDestinationListDirection() {
    return destinationListDirection;
  }

  public void setDestinationListDirection(Direction destinationListDirection) {
    this.destinationListDirection = destinationListDirection;
  }
}
