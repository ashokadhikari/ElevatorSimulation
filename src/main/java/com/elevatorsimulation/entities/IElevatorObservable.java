package com.elevatorsimulation.entities;

public interface IElevatorObservable {
  /*
  * Actions to be performed when destination is reached.
  * */
  void destinationReachedEvent(int destination);
}
