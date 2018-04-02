package com.elevatorsimulation.entities;

public interface IElevatorObserver {
  /*
  * Handler for destination reached event.
  * */
  void onDestinationReachedEvent(int destination);
}
