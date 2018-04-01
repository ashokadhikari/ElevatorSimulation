package com.elevatorsimulation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import com.elevatorsimulation.Message.Direction;
import com.elevatorsimulation.State.IState;
import com.elevatorsimulation.State.Idle;
import com.elevatorsimulation.State.Loading;
import com.elevatorsimulation.entities.Elevator;

public class StateChangeTest {
  Elevator testElevator;

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
}
