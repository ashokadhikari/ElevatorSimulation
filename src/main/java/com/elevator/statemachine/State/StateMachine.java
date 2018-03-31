package com.elevator.statemachine.State;

import com.com.elevatorsimulation.Message.Message;

public class StateMachine <T> {
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

    public void changeState(IState newState) {
        if (currentState != null) {
            currentState.exit(owner);
        }
        previousState = currentState;
        currentState = newState;
        if (currentState != null) {
            currentState.enter(owner);
        }
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
