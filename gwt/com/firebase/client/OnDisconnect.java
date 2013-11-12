package com.firebase.client;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

public class OnDisconnect {
  static OnDisconnect wrapOnDisconnect(JavaScriptObject onDisconnect) {
    return new OnDisconnect(onDisconnect);
  }

  private final JavaScriptObject onDisconnect;

  private OnDisconnect(JavaScriptObject onDisconnect) {
    this.onDisconnect = onDisconnect;
  }

  public native void cancel() /*-{
		var onDisconnect = this.@com.firebase.client.OnDisconnect::onDisconnect;
		onDisconnect.cancel();
  }-*/;

  public native void cancel(Firebase.CompletionListener listener) /*-{
		var onDisconnect = this.@com.firebase.client.OnDisconnect::onDisconnect;
		var onComplete = @com.firebase.client.Firebase::onComplete(Lcom/firebase/client/Firebase$CompletionListener;)(listener);
		onDisconnect.cancel(onComplete);
  }-*/;

  public native void removeValue() /*-{
		var onDisconnect = this.@com.firebase.client.OnDisconnect::onDisconnect;
		onDisconnect.remove();
  }-*/;

  public native void removeValue(Firebase.CompletionListener listener) /*-{
		var onDisconnect = this.@com.firebase.client.OnDisconnect::onDisconnect;
		var onComplete = @com.firebase.client.Firebase::onComplete(Lcom/firebase/client/Firebase$CompletionListener;)(listener);
		onDisconnect.remove(onComplete);
  }-*/;

  public native void setValue(Object value) /*-{
		var onDisconnect = this.@com.firebase.client.OnDisconnect::onDisconnect;
		onDisconnect.set(value);
  }-*/;

  public native void setValue(Object value, double priority) /*-{
		var onDisconnect = this.@com.firebase.client.OnDisconnect::onDisconnect;
		onDisconnect.setWithPriority(value, priority);
  }-*/;

  public native void setValue(Object value, double priority, Firebase.CompletionListener listener) /*-{
		var onDisconnect = this.@com.firebase.client.OnDisconnect::onDisconnect;
		var onComplete = @com.firebase.client.Firebase::onComplete(Lcom/firebase/client/Firebase$CompletionListener;)(listener);
		onDisconnect.setWithPriority(value, priority, onComplete);
  }-*/;

  public native void setValue(Object value, Firebase.CompletionListener listener) /*-{
		var onDisconnect = this.@com.firebase.client.OnDisconnect::onDisconnect;
		var onComplete = @com.firebase.client.Firebase::onComplete(Lcom/firebase/client/Firebase$CompletionListener;)(listener);
		onDisconnect.set(value, onComplete);
  }-*/;

  public void setValue(Object value, Map<Object, Object> priority,
      Firebase.CompletionListener listener) {
    // What does a Map for priority even mean?
    throw new UnsupportedOperationException("Not implemented.");
  }

  public native void setValue(Object value, String priority) /*-{
		var onDisconnect = this.@com.firebase.client.OnDisconnect::onDisconnect;
		onDisconnect.setWithPriority(value, priority);
  }-*/;

  public native void setValue(Object value, String priority, Firebase.CompletionListener listener) /*-{
		var onDisconnect = this.@com.firebase.client.OnDisconnect::onDisconnect;
		var onComplete = @com.firebase.client.Firebase::onComplete(Lcom/firebase/client/Firebase$CompletionListener;)(listener);
		onDisconnect.setWithPriority(value, priority, onComplete);
  }-*/;

  public native void updateChildren(Map<String, Object> children) /*-{
		var onDisconnect = this.@com.firebase.client.OnDisconnect::onDisconnect;
		var map = @com.firebase.client.Firebase::adaptMap(Ljava/util/Map;)(children);
		onDisconnect.update(map);
  }-*/;

  public native void updateChildren(Map<String, Object> children,
      Firebase.CompletionListener listener) /*-{
		var onDisconnect = this.@com.firebase.client.OnDisconnect::onDisconnect;
		var onComplete = @com.firebase.client.Firebase::onComplete(Lcom/firebase/client/Firebase$CompletionListener;)(listener);
		var map = @com.firebase.client.Firebase::adaptMap(Ljava/util/Map;)(children);
		onDisconnect.update(map, onComplete);
  }-*/;
}
