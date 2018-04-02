package com.elevatorsimulation.Message;

/*
 * Object sent to the elevator controller.
 * */
public class Request {

  private int floor;
  private Direction direction;

  public Request(int floor, Direction direction) {
    this.floor = floor;
    this.direction = direction;
  }

  public int getFloor() {
    return floor;
  }

  public Direction getDirection() {
    return direction;
  }
}
