package com.elevatorsimulation.State;

import com.elevatorsimulation.Message.Message;

public interface IState<T> {

  /*
   * enter - Actions to be performed while entering the state.
   * @params owner - Owner of this state.
   */
  void enter(T owner);

  /*
   * execute - Actions to be performed while in the state.
   * @params owner - Owner of this state.
   */
  void execute(T owner);

  /*
   * exit - Actions to be performed while exiting the state.
   * @params owner - Owner of this state.
   */
  void exit(T owner);

  /*
   * onMessage - Actions to be performed while entering the state.
   * @params owner - Owner of this state.
   * @params message - Message to send to the owner entity.
   */
  void onMessage(T owner, Message message);
}
