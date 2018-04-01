package com.elevatorsimulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import com.elevatorsimulation.Message.Direction;
import com.elevatorsimulation.State.Idle;
import com.elevatorsimulation.State.Loading;
import com.elevatorsimulation.entities.Elevator;
import com.elevatorsimulation.entities.ElevatorControl;

public class StateChangeTest {
  @Test
  public void testElevatorCreation() {
    Elevator elevator = new Elevator(1, 12, 0);
    assertEquals(elevator.getCurrentFloor(), 0);
    assertEquals(elevator.getDirection(), Direction.NONE);
  }

  @Test
  public void testElevatorInitialState() {
    Elevator elevator = new Elevator(1, 12, 0);
    assertEquals(elevator.getStateMachine().getCurrentState(), Idle.getInstance());
    assertTrue(elevator.getStateMachine().isInState(Idle.getInstance()));
  }

  @Test
  public void testElevatorChangeState() {
    Elevator elevator = new Elevator(1, 12, 0);
    assertEquals(elevator.getStateMachine().getCurrentState(), Idle.getInstance());
    elevator.getStateMachine().changeState(Loading.getInstance());
    assertTrue(elevator.getStateMachine().isInState(Loading.getInstance()));
  }

  @Test
  public void testElevatorDistance() {
    ElevatorControl elevatorControl = new ElevatorControl(3, 12, 0);
    for (Elevator elevator : elevatorControl.getElevators()) {
      assertEquals(elevator.getStateMachine().getCurrentState(), Idle.getInstance());
    }
    assertEquals(elevatorControl.getElevators().size(), 3);

    // When elevator is Idle, and in 0th floor, if someone from floor 2 calls it, the distance should be 2 no matter
    // what direction it is called.
    assertEquals(2, elevatorControl
        .computeElevatorDistance(2, Direction.UP, elevatorControl.getElevators().get(0)));
    assertEquals(2, elevatorControl
        .computeElevatorDistance(2, Direction.DOWN, elevatorControl.getElevators().get(0)));
  }
}
