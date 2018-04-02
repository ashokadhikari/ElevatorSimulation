package com.elevatorsimulation;

import com.elevatorsimulation.Message.Direction;
import com.elevatorsimulation.Message.Request;
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
    Request upRequest = new Request(2, Direction.UP);
    Request downRequest = new Request(2, Direction.DOWN);
    assertEquals(2, elevatorControl.computeElevatorDistance(upRequest, elevator));
    assertEquals(2, elevatorControl.computeElevatorDistance(downRequest, elevator));
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
    Request request = new Request(2, Direction.DOWN);
    elevatorControl.handleRequest(request);

    assertEquals(11, elevatorControl.computeElevatorDistance(request, elevator));
    assertEquals(1, elevator.getDestinationsDown().getDestinationList().size());
    assertEquals(2, elevator.getDestinationsDown().getNextDestination());
  }

  public void testElevatorDistanceWhenMovingUpScenario2() {
    /*
     * Elevator is moving up, current floor is 3.
     * Elevator has [5, 6, 8] in destination up list.
     * User presses Floor=2, Direction=Up.
     * Distance between elevator and requesting floor should be 11.
     * DestinationDown should now have 2.
     * Elevator state should be Moving state.
     * */
    elevator.setCurrentFloor(3);
    elevator.getDestinationsUp().addDestinations(new int[]{5, 6, 8});
    elevator.getStateMachine().changeState(Moving.getInstance());
    elevator.setDirection(Direction.UP);
    Request request = new Request(2, Direction.UP);
    elevatorControl.handleRequest(request);

    assertEquals(11, elevatorControl.computeElevatorDistance(request, elevator));
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
    Request request = new Request(5, Direction.UP);
    assertEquals(elevator.getDirection(), Direction.UP);

    assertEquals(2, elevatorControl.computeElevatorDistance(request, elevator));
    elevatorControl.handleRequest(request);

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
    Request request = new Request(2, Direction.DOWN);
    elevatorControl.handleRequest(request);

    assertEquals(13, elevatorControl.computeElevatorDistance(request, elevator));
    assertEquals(1, elevator.getDestinationsDown().getDestinationList().size());
    assertEquals(2, elevator.getDestinationsDown().getLastDestination());
  }

  // Movement Down scenarios.
  public void testElevatorDistanceWhenMovingDownScenario1() {
    /*
     * Elevator is moving down, current floor is 9.
     * Elevator has [2, 4, 5] in destination list.
     * User presses Floor=10, Direction=Down.
     * Distance between elevator and requesting floor should be 15.
     * DestinationUp should now have 10.
     * Elevator state should be Moving state.
     * */
    elevator.setCurrentFloor(9);
    elevator.getDestinationsDown().addDestinations(new int[]{2, 4, 5});
    elevator.getStateMachine().changeState(Moving.getInstance());
    elevator.setDirection(Direction.DOWN);
    Request request = new Request(10, Direction.DOWN);
    elevatorControl.handleRequest(request);

    assertEquals(15, elevatorControl.computeElevatorDistance(request, elevator));
    assertEquals(1, elevator.getDestinationsUp().getDestinationList().size());
    assertEquals(10, elevator.getDestinationsUp().getNextDestination());
  }


  // Integration tests.
  public void testElevatorMovingToLoadingWhenDestinationReached() {
    /*
    * Elevator is moving up, current floor is 3.
    * Elevator has [5, 6, 7] in destination up list.
    * When elevator reaches floor 5, it changes its state to Loading state.
    * Destination up list should now have [6, 7].
    * Elevator should still have direction UP.
    * */

    elevator.setCurrentFloor(3);
    elevator.getDestinationsUp().addDestinations(new int[]{5, 6, 7});
    elevator.getStateMachine().changeState(Moving.getInstance());
    elevator.setDirection(Direction.UP);

    // Floor 4
    elevator.getStateMachine().execute();
    // Floor 5
    elevator.getStateMachine().execute();

    assertTrue(elevator.getStateMachine().isInState(Loading.getInstance()));
    assertEquals(2, elevator.getDestinationsUp().getDestinationList().size());
  }

  public void testElevatorDirectionChangeWhenTopReached() {
    /*
    * Elevator is moving up, current floor is 10.
    * Elevator has [12] in destination up list.
    * Elevator has [2] in its destination down list.
    * When elevator reaches floor 12, it changes its direction to DOWN.
    * Destination up list should now be empty.
    * Elevator direction should now be DOWN.
     */
    elevator.setCurrentFloor(10);
    elevator.getDestinationsUp().addDestinations(new int[]{12});
    elevator.getDestinationsDown().addDestinations(new int[]{2});
    elevator.getStateMachine().changeState(Moving.getInstance());
    elevator.setDirection(Direction.UP);

    // Floor 11
    elevator.getStateMachine().execute();
    // Floor 12
    elevator.getStateMachine().execute();
    // Back To Moving State
    elevator.getStateMachine().execute();

    assertTrue(elevator.getStateMachine().isInState(Moving.getInstance()));
    assertEquals(0, elevator.getDestinationsUp().getDestinationList().size());
    assertEquals(Direction.DOWN, elevator.getDirection());
  }

  public void testElevatorStateWhenTopReachedAndNoMoreDestinations() {
    /*
    * Elevator is moving up, current floor is 10.
    * Elevator has [11, 12] in destination up list.
    * When elevator reaches floor 12, it changes direction to DOWN.
    * Then elevator changes its state to Idle.
    * Then elevator direction should be NONE.
    * */
    elevator.setCurrentFloor(10);
    elevator.getDestinationsUp().addDestinations(new int[]{11, 12});
    elevator.getStateMachine().changeState(Moving.getInstance());
    elevator.setDirection(Direction.UP);

    // Floor 11
    elevator.getStateMachine().execute();
    // Back to Moving State
    elevator.getStateMachine().execute();
    // Floor 12
    elevator.getStateMachine().execute();
    // Back to Moving State
    elevator.getStateMachine().execute();

    assertTrue(elevator.getStateMachine().isInState(Idle.getInstance()));
    assertTrue(elevator.getDestinationsUp().isEmpty());
    assertEquals(Direction.NONE, elevator.getDirection());
  }

  public void testElevatorStateSameFloorInIdleState() {
    /*
    * Elevator is in Idle State, current floor is 10.
    * When User requests Floor=10, Direction=UP.
    * Then elevator should change state to Loading State.
    * And elevator direction should be NONE.
    * */
    elevator.setCurrentFloor(10);

    Request request = new Request(10, Direction.UP);
    elevatorControl.handleRequest(request);

    assertTrue(elevator.getStateMachine().isInState(Loading.getInstance()));
    assertEquals(Direction.NONE, elevator.getDirection());
  }

  public void testElevatorStateDifferentFloorInIdleState() {
    /*
    * Elevator is in Idle State, current floor is 10.
    * When User requests Floor=5, Direction=DOWN.
    * Then elevator should change state to Moving State.
    * And elevator direction should be DOWN.
    * And destination down list should have [5].
    * */
    elevator.setCurrentFloor(10);

    Request request = new Request(5, Direction.DOWN);
    elevatorControl.handleRequest(request);

    assertTrue(elevator.getStateMachine().isInState(Moving.getInstance()));
    assertEquals(Direction.DOWN, elevator.getDirection());
    assertEquals(5, elevator.getDestinationsDown().getNextDestination());
  }

}