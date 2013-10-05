package com.firebase.client;

import com.google.gwt.core.client.JavaScriptObject;

class JavascriptChildEventListener implements ChildEventListener {

  static JavascriptChildEventListener wrapCallbacks(JavaScriptObject addedCallback,
      JavaScriptObject changedCallback, JavaScriptObject removedCallback,
      JavaScriptObject movedCallback) {
    return new JavascriptChildEventListener(addedCallback, changedCallback, removedCallback,
        movedCallback);
  }
  private final JavaScriptObject addedCallback;
  private final JavaScriptObject changedCallback;
  private final JavaScriptObject removedCallback;

  private final JavaScriptObject movedCallback;

  private JavascriptChildEventListener(JavaScriptObject addedCallback,
      JavaScriptObject changedCallback, JavaScriptObject removedCallback,
      JavaScriptObject movedCallback) {
    this.addedCallback = addedCallback;
    this.changedCallback = changedCallback;
    this.removedCallback = removedCallback;
    this.movedCallback = movedCallback;
  }

  @Override
  public void onCancelled() {
    throw new UnsupportedOperationException("You cannot re-use the listener you get back"
        + "from addEventListener");
  }

  @Override
  public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
    throw new UnsupportedOperationException("You cannot re-use the listener you get back"
        + "from addEventListener");
  }

  @Override
  public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
    throw new UnsupportedOperationException("You cannot re-use the listener you get back"
        + "from addEventListener");
  }

  @Override
  public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
    throw new UnsupportedOperationException("You cannot re-use the listener you get back"
        + "from addEventListener");
  }

  @Override
  public void onChildRemoved(DataSnapshot snapshot) {
    throw new UnsupportedOperationException("You cannot re-use the listener you get back"
        + "from addEventListener");
  }

  JavaScriptObject getAddedCallback() {
    return addedCallback;
  }

  JavaScriptObject getChangedCallback() {
    return changedCallback;
  }

  JavaScriptObject getMovedCallback() {
    return movedCallback;
  }

  JavaScriptObject getRemovedCallback() {
    return removedCallback;
  }

}
