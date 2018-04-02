package com.elevatorsimulation.entities;

import java.util.ArrayList;
import java.util.Collections;

public class Destinations {

  private boolean reversedDestinations;
  private ArrayList<Integer> destinationList;

  public Destinations(boolean reversedDestinations) {
    this.reversedDestinations = reversedDestinations;
    destinationList = new ArrayList<Integer>();
  }

  public void addDestination(int destination) {
    destinationList.add(destination);
    if (reversedDestinations) {
      Collections.sort(destinationList, Collections.<Integer>reverseOrder());
    } else {
      Collections.sort(destinationList);
    }
  }

  public void addDestinations(int[] destinations) {
    for (int destination: destinations) {
      addDestination(destination);
    }
  }

  public int getLastDestination() {
    return destinationList.get(destinationList.size() - 1);
  }

  public int getNextDestination() {
    return destinationList.get(0);
  }

  public int removeNextDestination() {
    return destinationList.remove(0);
  }

  public boolean isEmpty() {
    return destinationList.isEmpty();
  }

  public ArrayList<Integer> getDestinationList() {
    return destinationList;
  }
}
