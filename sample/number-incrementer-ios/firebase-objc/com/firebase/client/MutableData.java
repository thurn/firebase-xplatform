package com.firebase.client;

/*-[
#import <Firebase/Firebase.h>
#include "DataSnapshot.h"
#include "Firebase.h"
#include "java/util/ArrayList.h"
]-*/

public class MutableData {
  private final Object mutableData;

  MutableData(Object currentData) {
    this.mutableData = currentData;
  }

  public native MutableData child(String path) /*-[
    FMutableData *mutableData = self->mutableData_;
    return [[FCMutableData alloc] initWithId: [mutableData childDataByAppendingPath: path]];
  ]-*/;

  public native Iterable<MutableData> getChildren() /*-[
    FMutableData *mutableData = self->mutableData_;
    JavaUtilArrayList *result = [[JavaUtilArrayList alloc] init];
    for (FMutableData* child in mutableData.children) {
      [result addWithId: child];
    }
    return result;
  ]-*/;

  public native long getChildrenCount() /*-[
    FMutableData *mutableData = self->mutableData_;
    return mutableData.childrenCount;
  ]-*/;

  public native String getName() /*-[
    FMutableData *mutableData = self->mutableData_;
    return mutableData.name;
  ]-*/;

  public native MutableData getParent() /*-[
    FMutableData *mutableData = self->mutableData_;
    return [[FCMutableData alloc] initWithId: mutableData.parent];
  ]-*/;

  public native Object getPriority() /*-[
    FMutableData *mutableData = self->mutableData_;
    return [FCDataSnapshot convertToJavaObjectWithId: mutableData.priority];
  ]-*/;
  
  public native Object getValue() /*-[
    FMutableData *mutableData = self->mutableData_;
    return [FCDataSnapshot convertToJavaObjectWithId: mutableData.value];  
  ]-*/;

  @SuppressWarnings("unchecked")
  public <T> T getValue(Class<T> valueType) {
    return (T)getValue();
  }

  @SuppressWarnings("unchecked")
  public <T> T getValue(GenericTypeIndicator<T> t) {
    return (T)getValue();
  }

  public native boolean hasChild(String path) /*-[
    FMutableData *mutableData = self->mutableData_;
    return [mutableData hasChildAtPath: path];
  ]-*/;

  public native boolean hasChildren() /*-[
    FMutableData *mutableData = self->mutableData_;
    return [mutableData hasChildren];
  ]-*/;

  public native void setPriority(Object priority) /*-[
    FMutableData *mutableData = self->mutableData_;
    mutableData.priority = [FCFirebase convertToObjcValueWithId: priority];
  ]-*/;

  public native void setValue(Object value) /*-[
    FMutableData *mutableData = self->mutableData_;
    mutableData.value = [FCFirebase convertToObjcValueWithId: value]; 
  ]-*/;
  
  Object getMutableData() {
    return mutableData;
  }
}
