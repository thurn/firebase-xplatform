package com.firebase.client;

import java.util.Map;

/*-[
#include <Firebase/Firebase.h>
]-*/

public class OnDisconnect {
  private final Object firebase;

  OnDisconnect(Object onDisconnect) {
    this.firebase = onDisconnect;
  }

  public void cancel() {
    throw new UnsupportedOperationException("OnDisconnect#cancel not supported");
  }

  public void cancel(Firebase.CompletionListener listener) {
    throw new UnsupportedOperationException("OnDisconnect#cancel not supported");
  }

  public native void removeValue() /*-[
    Firebase *firebase = self->firebase_;
    [firebase onDisconnectRemoveValue];
  ]-*/;

  public native void removeValue(Firebase.CompletionListener listener) /*-[
    Firebase *firebase = self->firebase_;
    [firebase onDisconnectRemoveValueWithCompletionBlock:
        [FCFirebase wrapCompletionListenerWithFCFirebase_CompletionListener:listener]];
  ]-*/;
  public native void setValue(Object value) /*-[
    Firebase *firebase = self->firebase_;
    [firebase onDisconnectSetValue: [FCFirebase convertToObjcValueWithId: value]];
  ]-*/;

  public native void setValue(Object value, double priority) /*-[
    Firebase *firebase = self->firebase_;
    [firebase onDisconnectSetValue: [FCFirebase convertToObjcValueWithId: value]
                       andPriority: [NSNumber numberWithDouble: priority]];
  ]-*/;

  public native void setValue(Object value, double priority, Firebase.CompletionListener listener) /*-[
    Firebase *firebase = self->firebase_;
    [firebase onDisconnectSetValue: [FCFirebase convertToObjcValueWithId: value]
                       andPriority: [NSNumber numberWithDouble: priority]
               withCompletionBlock:
         [FCFirebase wrapCompletionListenerWithFCFirebase_CompletionListener: listener]];
  ]-*/;

  public native void setValue(Object value, Firebase.CompletionListener listener) /*-[
    Firebase *firebase = self->firebase_;
    [firebase onDisconnectSetValue: [FCFirebase convertToObjcValueWithId: value]
               withCompletionBlock:
         [FCFirebase wrapCompletionListenerWithFCFirebase_CompletionListener: listener]];
  ]-*/;

  public void setValue(Object value, Map<Object, Object> priority,
      Firebase.CompletionListener listener) {
    // What does a Map for priority even mean?
    throw new UnsupportedOperationException("Not implemented.");
  }

  public native void setValue(Object value, String priority) /*-[
    Firebase *firebase = self->firebase_;
    [firebase onDisconnectSetValue: [FCFirebase convertToObjcValueWithId: value]
                       andPriority: priority];
  ]-*/;

  public native void setValue(Object value, String priority, Firebase.CompletionListener listener) /*-[
    Firebase *firebase = self->firebase_;
    [firebase onDisconnectSetValue: [FCFirebase convertToObjcValueWithId: value]
                       andPriority: priority
               withCompletionBlock:
         [FCFirebase wrapCompletionListenerWithFCFirebase_CompletionListener: listener]];
  ]-*/;

  public native void updateChildren(Map<String, Object> children) /*-[
    Firebase *firebase = self->firebase_;
    [firebase onDisconnectUpdateChildValues:
         [FCFirebase convertMapToNsDictionaryWithJavaUtilMap: children]];
  ]-*/;

  public native void updateChildren(Map<String, Object> children,
      Firebase.CompletionListener listener) /*-[
    Firebase *firebase = self->firebase_;
    [firebase onDisconnectUpdateChildValues:
        [FCFirebase convertMapToNsDictionaryWithJavaUtilMap: children]
                        withCompletionBlock:
        [FCFirebase wrapCompletionListenerWithFCFirebase_CompletionListener: listener]];
  ]-*/;
}
