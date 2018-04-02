package com.elevatorsimulation;

import com.elevatorsimulation.Message.Direction;
import com.elevatorsimulation.State.Idle;
import com.elevatorsimulation.State.Loading;
import com.elevatorsimulation.State.Moving;
import com.elevatorsimulation.entities.Elevator;
import com.elevatorsimulation.entities.ElevatorControl;

import junit.framework.TestCase;

public class SingleRackElevatorStateChangeTest extends TestCase {

  private ElevatorControl elevatorControl;
  private Elevator elevator;

  public void setUp() throws Exception {
    super.setUp();
    elevatorControl = new ElevatorControl(1, 12, 12, 0);
    elevator = elevatorControl.getElevators().get(0);
  }

  public void tearDown() throws Exception {
    super.tearDown();
  }

  public void testElevatorCreation() {
    assertEquals(elevatorControl.getElevators().size(), 1);
    assertEquals(elevator.getCurrentFloor(), 0);
    assertEquals(elevator.getDirection(), Direction.NONE);
    assertEquals(elevator.getStateMachine().getCurrentState(), Idle.getInstance());
    assertTrue(elevator.getStateMachine().isInState(Idle.getInstance()));
  }

  public void testElevatorChangeState() {
    assertEquals(elevator.getStateMachine().getCurrentState(), Idle.getInstance());
    elevator.getStateMachine().changeState(Loading.getInstance());
    assertTrue(elevator.getStateMachine().isInState(Loading.getInstance()));
  }

  public void testElevatorDistanceWhenIdle() {
    // When elevator is Idle, and in 0th floor, if someone from floor 2 calls it, the distance should be 2 no matter
    // what direction it is called.
    assertEquals(2, elevatorControl.computeElevatorDistance(2, Direction.UP, elevator));
    assertEquals(2, elevatorControl.computeElevatorDistance(2, Direction.DOWN, elevator));
  }

  public void testElevatorDistanceWhenMovingUpScenario1() {
    /*
     * Elevator is moving up, current floor is 3.
     * Elevator has [5, 6, 8] in destination list.
     * User presses Floor=2, Direction=Down.
     * Distance between elevator and requesting floor should be 11.
     * DestinationDown should now have 2.
     * Elevator state should be Moving state.
     * */
    elevator.setCurrentFloor(3);
    elevator.getDestinationsUp().addDestinations(new int[]{5, 6, 8});
    elevator.getStateMachine().changeState(Moving.getInstance());
    elevator.setDirection(Direction.UP);
    elevatorControl.handleRequest(2, Direction.DOWN);

    assertEquals(11, elevatorControl.computeElevatorDistance(2, Direction.DOWN, elevator));
    assertEquals(1, elevator.getDestinationsDown().getDestinationList().size());
    assertEquals(2, elevator.getDestinationsDown().getNextDestination());
  }

  public void testElevatorDistanceWhenMovingUpScenario2() {
    /*
     * Elevator is moving up, current floor is 3.
     * Elevator has [5, 6, 8] in destination up list.
     * User presses Floor=2, Direction=Up.
     * Distance between elevator and requesting floor should be 11.
     * DestinationUp should now have 2.
     * Elevator state should be Moving state.
     * */
    elevator.setCurrentFloor(3);
    elevator.getDestinationsUp().addDestinations(new int[]{5, 6, 8});
    elevator.getStateMachine().changeState(Moving.getInstance());
    elevator.setDirection(Direction.UP);
    elevatorControl.handleRequest(2, Direction.DOWN);

    assertEquals(11, elevatorControl.computeElevatorDistance(2, Direction.DOWN, elevator));
    assertEquals(1, elevator.getDestinationsDown().getDestinationList().size());
    assertEquals(2, elevator.getDestinationsDown().getNextDestination());
  }

  public void testElevatorDistanceWhenMovingUpScenario3() {
    /*
     * Elevator is going UP, current floor is 3.
     * Elevator has 9, 10 in its destination up list.
     * User presses Floor=5, Direction=Up.
     * Distance between the elevator and requesting floor should be 2.
     * DestinationUp should now have 5, 9, 10 in sorted order.
     * The elevator should be in Moving state.
     * */
    elevator.setCurrentFloor(3);
    elevator.getDestinationsUp().addDestinations(new int[]{9, 10});
    elevator.getStateMachine().changeState(Moving.getInstance());
    elevator.setDirection(Direction.UP);
    assertEquals(elevator.getDirection(), Direction.UP);

    assertEquals(2, elevatorControl.computeElevatorDistance(5, Direction.UP, elevator));
    elevatorControl.handleRequest(5, Direction.UP);

    assertEquals(5, (int) elevator.getDestinationsUp().getDestinationList().get(0));
    assertEquals(9, (int) elevator.getDestinationsUp().getDestinationList().get(1));
    assertEquals(10, (int) elevator.getDestinationsUp().getDestinationList().get(2));

    assertTrue(elevator.getStateMachine().isInState(Moving.getInstance()));
  }

  public void testElevatorDistanceWhenMovingUpScenario4() {
    /*
     * Elevator is going UP, current floor is 5.
     * Elevator has [6, 7, 10] in its destination up list.
     * User presses Floor=2, Direction=DOWN.
     * Distance between elevator and requesting floor should be 13.
     * DestinationDown should now have [2].
     * The elevator should be in Moving state.
     * */
    elevator.setCurrentFloor(5);
    elevator.setDirection(Direction.UP);
    elevator.getDestinationsUp().addDestinations(new int[]{6, 7, 10});
    elevator.getStateMachine().changeState(Moving.getInstance());

    elevatorControl.handleRequest(2, Direction.DOWN);

    assertEquals(13, elevatorControl.computeElevatorDistance(2, Direction.DOWN, elevator));
    assertEquals(1, elevator.getDestinationsDown().getDestinationList().size());
    assertEquals(2, elevator.getDestinationsDown().getLastDestination());
  }
}

//    // Same test but for elevator going down.
//    elevator.setCurrentFloor(9);
//    elevator.getDestinationsDown().addDestination(1);
//    elevator.getDestinationsDown().addDestination(2);
//    elevator.getStateMachine().changeState(Moving.getInstance());
//    elevator.setDirection(Direction.DOWN);
//    assertEquals(elevator.getDirection(), Direction.DOWN);
//
//    assertEquals(4, elevatorControl.computeElevatorDistance(5, Direction.DOWN, elevator));
//    elevatorControl.handleRequest(5, Direction.DOWN);
//
//    assertEquals(5, (int) elevator.getDestinationsDown().getDestinationList().get(0));
//    assertEquals(2, (int) elevator.getDestinationsDown().getDestinationList().get(1));
//    assertEquals(1, (int) elevator.getDestinationsDown().getDestinationList().get(2));