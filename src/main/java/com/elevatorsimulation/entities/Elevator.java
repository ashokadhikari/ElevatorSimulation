package com.elevatorsimulation.entities;

import java.util.ArrayList;
import com.elevatorsimulation.Message.Direction;
import com.elevatorsimulation.Message.Message;
import com.elevatorsimulation.State.Idle;
import com.elevatorsimulation.State.StateMachine;

public class Elevator {
    // A unique id of the elevator.
    private int id;

    // Number of people this elevator can hold.
    private int capacity;

    // The current floor the elevator is at.
    private int currentFloor;

    // The direction the elevator is going.
    private Direction direction;

    // Number of passengers inside the elevator.
    private int numPassengers;

    private StateMachine<Elevator> stateMachine;

    // List containing sorted list of destinations.
    private ArrayList<Integer> destinationUpList;
    private ArrayList<Integer> destinationDownList;

    public Elevator (int id, int capacity, int currentFloor) {
        this.id = id;
        this.capacity = capacity;
        this.destinationDownList = new ArrayList<Integer>();
        this.destinationUpList = new ArrayList<Integer>();
        this.currentFloor = currentFloor;
        this.direction = Direction.NONE;
        this.stateMachine = new StateMachine<Elevator>(this);
        this.stateMachine.setCurrentState(Idle.getInstance());
    }

    public boolean isFull() {
        return numPassengers > capacity;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getLastDestinationUpwards() {
        return destinationUpList.get(destinationUpList.size() - 1);
    }

    public int getLastDestinationDownwards() {
        return destinationDownList.get(destinationDownList.size() - 1);
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public void handleRequest(int floor) {
        Message message = new Message(floor, Direction.NONE);
        this.sendMessage(message);
    }

    public void sendMessage(Message message) {
        this.stateMachine.onMessage(message);
    }
}
