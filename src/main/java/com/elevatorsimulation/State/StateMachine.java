package com.elevatorsimulation.State;

import com.elevatorsimulation.Message.Message;

public class StateMachine<T> {

  private T owner;

  private IState<T> currentState;

  private IState<T> previousState;

  private IState<T> globalState;

  public StateMachine(T owner) {
    this.owner = owner;
    currentState = null;
    previousState = null;
    globalState = null;
  }

  public void changeState(IState<T> newState) {
    if (currentState != null) {
      currentState.exit(owner);
    }
    previousState = currentState;
    currentState = newState;
    if (currentState != null) {
      currentState.enter(owner);
    }
  }

  public IState<T> getCurrentState() {
    return currentState;
  }

  public void setCurrentState(IState<T> currentState) {
    this.currentState = currentState;
  }

  public IState<T> getPreviousState() {
    return previousState;
  }

  public IState<T> getGlobalState() {
    return globalState;
  }

  public void returnToPreviousState() {
    this.changeState(previousState);
  }

  public void execute() {
    if (globalState != null) {
      globalState.execute(owner);
    }
    if (previousState != null) {
      currentState.execute(owner);
    }
  }

  public boolean isInState(IState state) {
    return currentState.getClass() == state.getClass();
  }

  public void onMessage(Message message) {
    this.currentState.onMessage(owner, message);
  }

}
