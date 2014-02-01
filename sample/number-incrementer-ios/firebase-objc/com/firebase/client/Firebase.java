package com.firebase.client;

import java.util.List;
import java.util.Map;

/*-[
#import <Firebase/Firebase.h>
#include "MutableData.h"
#include "Transaction.h"
#include "DataSnapshot.h"
]-*/

public class Firebase extends Query {

  public static interface AuthListener {
    void onAuthError(FirebaseError error);

    void onAuthRevoked(FirebaseError error);

    void onAuthSuccess(Object authData);
  }

  public static interface CompletionListener {
    public void onComplete(FirebaseError error);
  }

  public static native Object createFirebase(String url) /*-[
    return [[Firebase alloc] initWithUrl: url];
  ]-*/;
  
  public Firebase(String url) {
    super(createFirebase(url));
  }

  private static native Object createDictionary() /*-[
    return [[NSMutableDictionary alloc] init];
  ]-*/;
  
  private static native Object createArray() /*-[
    return [[NSMutableArray alloc] init];
  ]-*/;
  
  private static native void addToDictionary(Object dictionary, String key, Object value) /*-[
    [dictionary setValue: value forKey: key];
  ]-*/;
  
  private static native void addToArray(Object array, Object value) /*-[
    [array addObject: value];
  ]-*/;
  
  static Object convertMapToNsDictionary(@SuppressWarnings("rawtypes") Map map) {
    Object dictionary = createDictionary();
    for (Object key : map.keySet()) {
      addToDictionary(dictionary, key.toString(), convertToObjcValue(map.get(key)));
    }
    return dictionary;
  }
  
  static Object convertListToNsArray(@SuppressWarnings("rawtypes") List list) {
    Object array = createArray();
    for (Object value : list) {
      addToArray(array, convertToObjcValue(value));
    }
    return array;
  }
  
  static Object convertToObjcValue(Object value) {
    if (value == null || value instanceof Number || value instanceof String) {
      return value;
    } else if (value instanceof Boolean) {
      return booleanToBool((Boolean)value);
    } else if (value instanceof List) {
      return convertListToNsArray((List<?>)value);
    } else if (value instanceof Map) {
      return convertMapToNsDictionary((Map<?, ?>)value);
    } else {
      throw new IllegalArgumentException("Can't convert value " + value);
    }
  }
  
  private static native Object booleanToBool(Boolean value) /*-[
    if ([value booleanValue]) {
      return @YES;
    } else {
      return @NO;
    }
  ]-*/;
  
  private static native Object wrapCompletionListener(CompletionListener listener) /*-[
    return ^(NSError *error, Firebase *ref) {
      [listener onCompleteWithFCFirebaseError: [[FCFirebaseError alloc] initWithId: error]];
    };
  ]-*/;
  
  public native String getName() /*-[
    Firebase *firebase = self->firebase_;
    return firebase.name;
  ]-*/;
  
  public native Firebase child(String pathString) /*-[
    Firebase *firebase = self->firebase_;
    return [[FCFirebase alloc] initWithId: [firebase childByAppendingPath: pathString]];
  ]-*/;
  
  public native Firebase getParent() /*-[
    Firebase *firebase = self->firebase_;
    return [[FCFirebase alloc] initWithId: firebase.parent];
  ]-*/;
  
  public native Firebase getRoot() /*-[
    Firebase *firebase = self->firebase_;
    return [[FCFirebase alloc] initWithId: firebase.root];
  ]-*/;
  
  public void setValue(Object value) {
    setValue(value, null, null);
  }

  public void setValue(Object value, CompletionListener listener) {
    setValue(value, null, listener);
  }

  public void setValue(Object value, Object priority) {
    setValue(value, priority, null);
  }
  
  public void setValue(Object value, Object priority, CompletionListener listener) {
    setValueNative(convertToObjcValue(value), convertToObjcValue(priority), wrapCompletionListener(listener));
  }
  
  public void updateChildren(Map<String, Object> children) {
    updateChildren(children, null);
  }
  
  public void updateChildren(Map<String, Object> children, CompletionListener listener) {
    updateChildrenNative(convertMapToNsDictionary(children), wrapCompletionListener(listener));
  }
  
  private native void updateChildrenNative(Object map, Object listener) /*-[
    Firebase *firebase = self->firebase_;
    if (listener == nil) {
      [firebase updateChildValues: map];
    } else {
      [firebase updateChildValues: map withCompletionBlock: listener];
    }
  ]-*/;
  
  private native void setValueNative(Object value, Object priority, Object listener) /*-[
    Firebase *firebase = self->firebase_;
    if (listener == nil) {
      if (priority == nil) {
        [firebase setValue: value];
      } else {
        [firebase setValue:value andPriority: priority];
      }
    } else {
      if (priority == nil) {
        [firebase setValue: value withCompletionBlock: listener];
      } else {
        [firebase setValue:value andPriority: priority withCompletionBlock: listener];
      }
    }
  ]-*/;
  
  public void removeValue() {
    removeValue(null);
  }
  
  public void removeValue(CompletionListener listener) {
    removeValueNative(wrapCompletionListener(listener));
  }
  
  public native void removeValueNative(Object listener) /*-[
    Firebase *firebase = self->firebase_;
    if (listener == nil) {
      [firebase removeValue];
    } else {
      [firebase removeValueWithCompletionBlock: listener];
    }
  ]-*/;
  
  public void setPriority(Object priority) {
    setPriority(priority, null);
  }

  public void setPriority(Object priority, CompletionListener listener) {
    setPriorityNative(convertToObjcValue(priority), wrapCompletionListener(listener));
  }
  
  private native void setPriorityNative(Object priority, Object listener) /*-[
    Firebase *firebase = self->firebase_;
    if (listener == nil) {
      [firebase setPriority: priority];
    } else {
      [firebase setPriority: priority withCompletionBlock: listener];
    }
  ]-*/;
  
  public void runTransaction(Transaction.Handler handler) {
    runTransaction(handler, true);
  }

  public native void runTransaction(Transaction.Handler handler, boolean fireLocalEvents) /*-[
    Firebase *firebase = self->firebase_;
    FTransactionResult* (^transactionBlock)(FMutableData*) = ^(FMutableData *currentData) {
      FCMutableData *javaMutableData = [[FCMutableData alloc] initWithId: currentData];
      FCTransaction_Result *result = [handler doTransactionWithFCMutableData: javaMutableData];
      if ([result isSuccess]) {
        return [FTransactionResult successWithValue: [result getResultData]];
      } else {
        return [FTransactionResult abort];
      }
    };
    void (^completionBlock)(NSError*, BOOL, FDataSnapshot*) =
        ^(NSError *error, BOOL committed, FDataSnapshot *snapshot) {
      [handler onCompleteWithFCFirebaseError: [[FCFirebaseError alloc] initWithId: error]
                                 withBoolean: committed
                          withFCDataSnapshot: [[FCDataSnapshot alloc] initWithId: snapshot]];
    };
    [firebase runTransactionBlock: transactionBlock
               andCompletionBlock: completionBlock
                  withLocalEvents: fireLocalEvents];
  ]-*/;
  
  public OnDisconnect onDisconnect() {
    return new OnDisconnect(firebase);
  }
  
}
