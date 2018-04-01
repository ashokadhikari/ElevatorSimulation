package com.elevatorsimulation.Message;

public class Message {
    private int requestFloor;

    private Direction direction;

    public Message(int requestFloor, Direction direction) {
        this.requestFloor = requestFloor;
        this.direction = direction;
    }

    public int getRequestFloor() {
        return requestFloor;
    }

    public void setRequestFloor(int requestFloor) {
        this.requestFloor = requestFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
