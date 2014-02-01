package com.firebase.client;

/*-[
#import <Firebase/Firebase.h>
#import "DataSnapshot.h"
#import "WrappedValueEventListener.h"
#import "WrappedChildEventListener.h"
]-*/

public class Query {

  final Object firebase;

  public Query(Object firebase) {
    this.firebase = firebase;
  }
  
  public void helloWorld() {
    System.out.println("Hello, world!");
  }
  
  public native void addListenerForSingleValueEvent(ValueEventListener listener) /*-[
    Firebase *firebase = self->firebase_;
    void (^onDataChange)(FDataSnapshot*) = ^(FDataSnapshot *snapshot) {
       FCDataSnapshot *javaSnapshot = [[FCDataSnapshot alloc] initWithId: snapshot];
       [listener onDataChangeWithFCDataSnapshot: javaSnapshot];
    };
    void (^onCancel)() = ^{
      [listener onCancelled];
    };
    [firebase observeSingleEventOfType: FEventTypeValue
                             withBlock: onDataChange
                       withCancelBlock: onCancel];
  ]-*/;
  
  public native ValueEventListener addValueEventListener(ValueEventListener listener) /*-[
    Firebase *firebase = self->firebase_;
    void (^onDataChange)(FDataSnapshot*) = ^(FDataSnapshot *snapshot) {
      FCDataSnapshot *javaSnapshot = [[FCDataSnapshot alloc] initWithId: snapshot];
      [listener onDataChangeWithFCDataSnapshot: javaSnapshot];
    };
    void (^onCancel)() = ^{
      [listener onCancelled];
    };
    FirebaseHandle handle = [firebase observeEventType: FEventTypeValue
                                             withBlock: onDataChange
                                       withCancelBlock: onCancel];
    id<FCValueEventListener> result = [[FCWrappedValueEventListener alloc] initWithLong: handle];
    return result;
  ]-*/;
  
  public native ChildEventListener addChildEventListener(ChildEventListener listener)  /*-[
    Firebase *firebase = self->firebase_;
    void (^addedCallback)(FDataSnapshot*, NSString*) = ^(FDataSnapshot* snapshot, NSString* previous) {
      FCDataSnapshot *javaSnapshot = [[FCDataSnapshot alloc] initWithId: snapshot];
      [listener onChildAddedWithFCDataSnapshot: javaSnapshot withNSString: previous];
    };
    void (^changedCallback)(FDataSnapshot*, NSString*) = ^(FDataSnapshot* snapshot, NSString* previous) {
      FCDataSnapshot *javaSnapshot = [[FCDataSnapshot alloc] initWithId: snapshot];
      [listener onChildChangedWithFCDataSnapshot: javaSnapshot withNSString: previous];
    };
    void (^movedCallback)(FDataSnapshot*, NSString*) = ^(FDataSnapshot* snapshot, NSString* previous) {
      FCDataSnapshot *javaSnapshot = [[FCDataSnapshot alloc] initWithId: snapshot];
      [listener onChildMovedWithFCDataSnapshot: javaSnapshot withNSString: previous];
    };
    void (^removedCallback)(FDataSnapshot*) = ^(FDataSnapshot* snapshot) {
      FCDataSnapshot *javaSnapshot = [[FCDataSnapshot alloc] initWithId: snapshot];
      [listener onChildRemovedWithFCDataSnapshot: javaSnapshot];
    };
    void (^cancelledCallback)() = ^{
      [listener onCancelled];
    };
    FirebaseHandle addedHandle = [firebase observeEventType: FEventTypeChildAdded
                            andPreviousSiblingNameWithBlock: addedCallback
                                            withCancelBlock: cancelledCallback];
    FirebaseHandle changedHandle = [firebase observeEventType: FEventTypeChildChanged
                              andPreviousSiblingNameWithBlock: changedCallback
                                              withCancelBlock: cancelledCallback];
    FirebaseHandle movedHandle = [firebase observeEventType: FEventTypeChildMoved
                            andPreviousSiblingNameWithBlock: movedCallback
                                            withCancelBlock: cancelledCallback];
    FirebaseHandle removedHandle = [firebase observeEventType: FEventTypeChildRemoved
                                                    withBlock: removedCallback
                                              withCancelBlock: cancelledCallback];
    return [[FCWrappedChildEventListener alloc] initWithLong: addedHandle
                                                withLong: changedHandle
                                                withLong: movedHandle
                                                withLong: removedHandle];
  ]-*/;
  
  public void removeEventListener(ChildEventListener listener) {
    if (!(listener instanceof WrappedChildEventListener)) {
      throw new IllegalArgumentException("The listener provided to "
          + "removeEventListener was not returned by a call to addEventListener");
    }
    WrappedChildEventListener wrapped = (WrappedChildEventListener)listener;
    removeChildEventListeners(wrapped.getChildAdded(), wrapped.getChildChanged(),
        wrapped.getChildMoved(), wrapped.getChildRemoved());
  }
  
  private native void removeChildEventListeners(long addedHandle, long changedHandle,
      long movedHandle, long removedHandle) /*-[
    Firebase *firebase = self->firebase_;
    [firebase removeObserverWithHandle: addedHandle];
    [firebase removeObserverWithHandle: changedHandle];
    [firebase removeObserverWithHandle: movedHandle];
    [firebase removeObserverWithHandle: removedHandle];
  ]-*/;
  
  public void removeEventListener(ValueEventListener listener) {
    if (!(listener instanceof WrappedValueEventListener)) {
      throw new IllegalArgumentException("The listener provided to "
          + "removeEventListener was not returned by a call to addEventListener");
    }
    WrappedValueEventListener wrapped = (WrappedValueEventListener)listener;
    removeValueEventListener(wrapped.getHandle());
  }
  
  private native void removeValueEventListener(long handle) /*-[
    Firebase *firebase = self->firebase_;
    [firebase removeObserverWithHandle: handle];
  ]-*/;
  
  public Query endAt() {
    return this;
  }
  
  public native Query endAt(double priority) /*-[
    Firebase *firebase = self->firebase_;
    NSNumber *number = [[NSNumber alloc] initWithDouble: priority];
    FQuery *result = [firebase queryEndingAtPriority: number];
    return [[FCQuery alloc] initWithId: result];
  ]-*/;
  
  public native Query endAt(double priority, String name) /*-[
    Firebase *firebase = self->firebase_;
    NSNumber *number = [[NSNumber alloc] initWithDouble: priority];
    FQuery *result = [firebase queryEndingAtPriority: number andChildName: name];
    return [[FCQuery alloc] initWithId: result];
  ]-*/;
  
  public native Query endAt(String priority) /*-[
    Firebase *firebase = self->firebase_;
    FQuery *result = [firebase queryEndingAtPriority: priority];
    return [[FCQuery alloc] initWithId: result];
  ]-*/;
  
  public native Query endAt(String priority, String name) /*-[
    Firebase *firebase = self->firebase_;
    FQuery *result = [firebase queryEndingAtPriority: priority andChildName: name];
    return [[FCQuery alloc] initWithId: result];
  ]-*/;
  
  public Query startAt() {
    throw new UnsupportedOperationException("Firebase ObjC does not support startAt()");
  }
  
  public native Query startAt(double priority) /*-[
    Firebase *firebase = self->firebase_;
    NSNumber *number = [[NSNumber alloc] initWithDouble: priority];
    FQuery *result = [firebase queryStartingAtPriority: number];
    return [[FCQuery alloc] initWithId: result];
  ]-*/;
  
  public native Query startAt(double priority, String name) /*-[
    Firebase *firebase = self->firebase_;
    NSNumber *number = [[NSNumber alloc] initWithDouble: priority];
    FQuery *result = [firebase queryStartingAtPriority: number andChildName: name];
    return [[FCQuery alloc] initWithId: result];
  ]-*/;
  
  public native Query startAt(String priority) /*-[
    Firebase *firebase = self->firebase_;
    FQuery *result = [firebase queryStartingAtPriority: priority];
    return [[FCQuery alloc] initWithId: result];
  ]-*/;
  
  public native Query startAt(String priority, String name) /*-[
    Firebase *firebase = self->firebase_;
    FQuery *result = [firebase queryStartingAtPriority: priority andChildName: name];
    return [[FCQuery alloc] initWithId: result];
  ]-*/;
  
  public native Query limit(int limit) /*-[
    Firebase *firebase = self->firebase_;
    FQuery *result = [firebase queryLimitedToNumberOfChildren: limit];
    return [[FCQuery alloc] initWithId: result];
  ]-*/;
  
  public Firebase getRef() {
    throw new UnsupportedOperationException("Firebase ObjC does not support getRef()");
  }
}
