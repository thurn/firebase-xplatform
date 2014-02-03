package com.firebase.client;

import com.google.gwt.core.client.JavaScriptObject;

public class Query {
  private static Query wrapQuery(JavaScriptObject firebase) {
    return new Query(firebase);
  }

  protected JavaScriptObject firebase;

  protected Query(JavaScriptObject firebase) {
    this.firebase = firebase;
  }

  public native ChildEventListener addChildEventListener(ChildEventListener listener) /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    var addedCallback = function(snapshot, previous) {
      var javaSnapshot = @com.firebase.client.DataSnapshot::wrapDataSnapshot(Lcom/google/gwt/core/client/JavaScriptObject;)(snapshot);
      listener.@com.firebase.client.ChildEventListener::onChildAdded(Lcom/firebase/client/DataSnapshot;Ljava/lang/String;)(javaSnapshot, previous);
    };
    var changedCallback = function(snapshot, previous) {
      var javaSnapshot = @com.firebase.client.DataSnapshot::wrapDataSnapshot(Lcom/google/gwt/core/client/JavaScriptObject;)(snapshot);
      listener.@com.firebase.client.ChildEventListener::onChildChanged(Lcom/firebase/client/DataSnapshot;Ljava/lang/String;)(javaSnapshot, previous);
    };
    var removedCallback = function(snapshot) {
      var javaSnapshot = @com.firebase.client.DataSnapshot::wrapDataSnapshot(Lcom/google/gwt/core/client/JavaScriptObject;)(snapshot);
      listener.@com.firebase.client.ChildEventListener::onChildRemoved(Lcom/firebase/client/DataSnapshot;)(javaSnapshot);
    };
    var movedCallback = function(snapshot, previous) {
      var javaSnapshot = @com.firebase.client.DataSnapshot::wrapDataSnapshot(Lcom/google/gwt/core/client/JavaScriptObject;)(snapshot);
      listener.@com.firebase.client.ChildEventListener::onChildMoved(Lcom/firebase/client/DataSnapshot;Ljava/lang/String;)(javaSnapshot, previous);
    };
    var cancelCallback = function() {
      listener.@com.firebase.client.ChildEventListener::onCancelled(Lcom/firebase/client/FirebaseError;)(null);
    };
    firebase.on("child_added", addedCallback, cancelCallback);
    firebase.on("child_changed", changedCallback, cancelCallback);
    firebase.on("child_removed", removedCallback, cancelCallback);
    firebase.on("child_moved", movedCallback, cancelCallback);
    return @com.firebase.client.JavascriptChildEventListener::wrapCallbacks(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(addedCallback, changedCallback, removedCallback, movedCallback);
  }-*/;

  public native void addListenerForSingleValueEvent(ValueEventListener listener) /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    var callback = function(snapshot) {
      var javaSnapshot = @com.firebase.client.DataSnapshot::wrapDataSnapshot(Lcom/google/gwt/core/client/JavaScriptObject;)(snapshot);
      listener.@com.firebase.client.ValueEventListener::onDataChange(Lcom/firebase/client/DataSnapshot;)(javaSnapshot);
    };
    var cancelCallback = function() {
      listener.@com.firebase.client.ValueEventListener::onCancelled()();
    };
    firebase.once("value", callback, cancelCallback);
  }-*/;

  public native ValueEventListener addValueEventListener(ValueEventListener listener) /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    var callback = function(snapshot) {
      var javaSnapshot = @com.firebase.client.DataSnapshot::wrapDataSnapshot(Lcom/google/gwt/core/client/JavaScriptObject;)(snapshot);
      listener.@com.firebase.client.ValueEventListener::onDataChange(Lcom/firebase/client/DataSnapshot;)(javaSnapshot);
    };
    var cancelCallback = function() {
      listener.@com.firebase.client.ValueEventListener::onCancelled()();
    };
    firebase.on("value", callback, cancelCallback);
    return @com.firebase.client.JavascriptValueEventListener::wrapCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(callback);
  }-*/;

  public native Query endAt() /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    var result = firebase.endAt();
    return @com.firebase.client.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
  }-*/;

  public native Query endAt(double priority) /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    var result = firebase.endAt(priority);
    return @com.firebase.client.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
  }-*/;

  public native Query endAt(double priority, String name) /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    var result = firebase.endAt(priority, name);
    return @com.firebase.client.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
  }-*/;

  public native Query endAt(String priority) /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    var result = firebase.endAt(priority);
    return @com.firebase.client.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
  }-*/;

  public native Query endAt(String priority, String name) /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    var result = firebase.endAt(priority, name);
    return @com.firebase.client.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
  }-*/;

  public native Firebase getRef() /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    return @com.firebase.client.Firebase::wrapFirebase(Lcom/google/gwt/core/client/JavaScriptObject;)(firebase.ref());
  }-*/;

  public native Query limit(int limit) /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    var result = firebase.limit(limit);
    return @com.firebase.client.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
  }-*/;

  public void removeEventListener(ChildEventListener listener) {
    if (!(listener instanceof JavascriptChildEventListener)) {
      throw new IllegalArgumentException("The listener provided to "
          + "removeEventListener was not returned by a call to addEventListener");
    }
    JavascriptChildEventListener jsListener = (JavascriptChildEventListener) listener;
    removeChildEventListener(jsListener.getAddedCallback(), jsListener.getChangedCallback(),
        jsListener.getRemovedCallback(), jsListener.getMovedCallback());
  }

  public void removeEventListener(ValueEventListener listener) {
    if (!(listener instanceof JavascriptValueEventListener)) {
      throw new IllegalArgumentException("The listener provided to "
          + "removeEventListener was not returned by a call to addEventListener");
    }
    JavascriptValueEventListener jsListener = (JavascriptValueEventListener) listener;
    removeValueEventListener(jsListener.getCallback());
  }

  public native Query startAt() /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    var result = firebase.startAt();
    return @com.firebase.client.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
  }-*/;

  public native Query startAt(double priority) /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    var result = firebase.startAt(priority);
    return @com.firebase.client.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
  }-*/;

  public native Query startAt(double priority, String name) /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    var result = firebase.startAt(priority, name);
    return @com.firebase.client.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
  }-*/;

  public native Query startAt(String priority) /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    var result = firebase.startAt(priority);
    return @com.firebase.client.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
  }-*/;

  public native Query startAt(String priority, String name) /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    var result = firebase.startAt(priority, name);
    return @com.firebase.client.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
  }-*/;

  private native void removeChildEventListener(JavaScriptObject addedCallback,
      JavaScriptObject changedCallback, JavaScriptObject removedCallback,
      JavaScriptObject movedCallback) /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    firebase.off("child_added", addedCallback);
    firebase.off("child_changed", changedCallback);
    firebase.off("child_removed", removedCallback);
    firebase.off("child_moved", movedCallback);
  }-*/;

  private native void removeValueEventListener(JavaScriptObject callback) /*-{
    var firebase = this.@com.firebase.client.Query::firebase;
    firebase.off("value", callback);
  }-*/;
}
