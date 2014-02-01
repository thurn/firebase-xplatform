package com.firebase.client;


public class WrappedChildEventListener implements ChildEventListener {

  long childAdded;
  long childRemoved;
  long childMoved;
  long childChanged;
  
  WrappedChildEventListener(long childAdded, long childChanged, long childMoved, 
      long childRemoved) {
    this.childAdded = childAdded;
    this.childChanged = childChanged;
    this.childMoved = childMoved;
    this.childRemoved = childRemoved;
  }
  
  @Override
  public void onCancelled() {
    throw new RuntimeException("You cannot re-use the listener you get back from "
        + "addChildEventListener()");
  }

  @Override
  public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
    throw new RuntimeException("You cannot re-use the listener you get back from "
        + "addChildEventListener()");
  }

  @Override
  public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
    throw new RuntimeException("You cannot re-use the listener you get back from "
        + "addChildEventListener()");
  }

  @Override
  public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
    throw new RuntimeException("You cannot re-use the listener you get back from "
        + "addChildEventListener()");
  }

  @Override
  public void onChildRemoved(DataSnapshot snapshot) {
    throw new RuntimeException("You cannot re-use the listener you get back from "
        + "addChildEventListener()");
  }

  long getChildAdded() {
    return childAdded;
  }

  long getChildRemoved() {
    return childRemoved;
  }

  long getChildMoved() {
    return childMoved;
  }

  long getChildChanged() {
    return childChanged;
  }

}
