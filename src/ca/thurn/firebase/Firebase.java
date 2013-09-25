package ca.thurn.firebase;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

/**
 * General notes on using this library:
 * 1) Most methods on MutableData are not supported.
 * 2) Doubles with no fractional part will be returned as Integers. You'll need
 *    to explicitly convert all doubles back to their correct type.
 * 3) Null values inside maps and lists are not supported, as they can lead to
 *    confusing semantics (such as nulls at the end of a list getting dropped).
 * 4) Empty lists and maps are converted to "null".
 */
public class Firebase extends Query {
  public static interface AuthListener {
    void onAuthError(FirebaseError error);

    void onAuthRevoked(FirebaseError error);

    void onAuthSuccess(Object authData);
  }

  public static interface CompletionListener {
    public void onComplete(FirebaseError error);
  }

  public static Config getDefaultConfig() {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public static String getSdkVersion() {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public static void setDefaultConfig(Config config) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  /**
   * @param url The URL of a firebase.
   * @return A JavaScript firebase reference for this URL.
   */
  private static native JavaScriptObject createFirebase(String url) /*-{
    var result = new Firebase(url);
    return result;

  }-*/;

  private static Object toJavaScript(Object object) {
    JSONValue value = toJsonValue(object);
    if (value.isArray() != null) {
      return value.isArray().getJavaScriptObject();
    }
    if (value.isObject() != null) {
      return value.isObject().getJavaScriptObject();
    }
    return object;
  }

  /**
   * Wraps a JavaScript error with a FirebaseError, returning null if the input was null.
   *
   * @param object JavaScriptObject to wrap.
   * @return A corresponding FirebaseError.
   */
  private static FirebaseError wrapError(JavaScriptObject object) {
    if (object == null) {
      return null;
    } else {
      return new FirebaseError(object);
    }
  }

  /**
   * Attempts to convert a Map into a JavaScript object. In general, this will work only if the
   * input Map is built entirely out of primitive types, Strings, Lists, Maps, or JSONValues. All
   * map keys will be converted to Strings via toString().
   *
   * @param map The input Map.
   * @return A JavaScriptObject corresponding to this Map.
   * @throws IllegalArgumentException If the input cannot be converted.
   */
  @SuppressWarnings("rawtypes")
  static JavaScriptObject adaptMap(Map map) {
    JSONObject json = new JSONObject();
    for (Object key : map.keySet()) {
      if (map.get(key) == null) {
        throw new IllegalArgumentException("Firebase does not work well with null values "
            + "in maps.");
      }
      json.put(key.toString(), toJsonValue(map.get(key)));
    }
    return json.getJavaScriptObject();
  }

  /**
   * Creates a JavaScript function which wraps a CompletionListener, wrapping any error object in a
   * FirebaseError before passing it to onComplete.
   *
   * @param listener The listener to wrap.
   * @return A JavaScriptObject which is a function wrapping the listener.
   */
  static native JavaScriptObject onComplete(CompletionListener listener) /*-{
    return function(error) {
      var firebaseError = @ca.thurn.firebase.Firebase::wrapError(Lcom/google/gwt/core/client/JavaScriptObject;)(error);
      listener.@ca.thurn.firebase.Firebase.CompletionListener::onComplete(Lca/thurn/firebase/FirebaseError;)(firebaseError);
    };
  }-*/;

  static void print(Object msg) {
    if (msg != null) {
      System.out.println("[" + msg.getClass() + "] " + msg);
    } else {
      System.out.println("[null]");
    }
  }

  /**
   * Attempts to convert an Object to a JSONValue. In general, this will only be possible if the
   * object is a primitive type, String, Map, List, or a JSONValue already.
   *
   * @param object An input object to convert.
   * @return A JSONValue corresponding to this object.
   * @throws IllegalArgumentException If the object could not be converted.
   */
  @SuppressWarnings("rawtypes")
  static JSONValue toJsonValue(Object object) {
    if (object instanceof JSONValue) {
      return (JSONValue) object;
    } else if (object instanceof CharSequence) {
      return new JSONString(object.toString());
    } else if (object instanceof Number) {
      if (!(object instanceof Double || object instanceof Integer)) {
        throw new IllegalArgumentException("Only numbers of type Double or Integer "
            + "are allowed.");
      }
      return new JSONNumber(((Number) object).doubleValue());
    } else if (object instanceof Boolean) {
      return JSONBoolean.getInstance((Boolean) object);
    } else if (object instanceof JavaScriptObject) {
      return new JSONObject((JavaScriptObject) object);
    } else if (object instanceof Map) {
      return new JSONObject(adaptMap((Map) object));
    } else if (object instanceof List) {
      JSONArray result = new JSONArray();
      List list = (List) object;
      for (int i = 0; i < list.size(); ++i) {
        if (list.get(i) == null) {
          throw new IllegalArgumentException("List contained a null value: " + list);
        }
        result.set(i, toJsonValue(list.get(i)));
      }
      return result;
    } else if (object == null) {
      return JSONNull.getInstance();
    }
    throw new IllegalArgumentException("Object cannot be converted to a JSONValue." + object);
  }

  static Firebase wrapFirebase(JavaScriptObject firebase) {
    return new Firebase(firebase);
  }

  public Firebase(String url) {
    super(createFirebase(url));
  }

  private Firebase(JavaScriptObject firebase) {
    super(firebase);
  }

  public native void auth(String credential, AuthListener listener) /*-{
    var firebase = this.@ca.thurn.firebase.Query::firebase;
    var onComplete = function(error, payload) {
      if (error == null) {
        listener.@ca.thurn.firebase.Firebase.AuthListener::onAuthSuccess(Ljava/lang/Object;)(payload);
      } else {
        var firebaseError = @ca.thurn.firebase.Firebase::wrapError(Lcom/google/gwt/core/client/JavaScriptObject;)(error);
        listener.@ca.thurn.firebase.Firebase.AuthListener::onAuthError(Lca/thurn/firebase/FirebaseError;)(firebaseError);
      }
    };
    firebase.auth(credential, onComplete, onAuthError);
  }-*/;

  public native Firebase child(String pathString) /*-{
    var firebase = this.@ca.thurn.firebase.Query::firebase;
    var result = firebase.child(pathString);
    return @ca.thurn.firebase.Firebase::wrapFirebase(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
  }-*/;

  public native String getName() /*-{
    var firebase = this.@ca.thurn.firebase.Query::firebase;
    return firebase.name();
  }-*/;

  public native Firebase getParent() /*-{
    var firebase = this.@ca.thurn.firebase.Query::firebase;
    var result = firebase.parent();
    return @ca.thurn.firebase.Firebase::wrapFirebase(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
  }-*/;

  public native Firebase getRoot() /*-{
    var firebase = this.@ca.thurn.firebase.Query::firebase;
    var result = firebase.root();
    return @ca.thurn.firebase.Firebase::wrapFirebase(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
  }-*/;

  public native OnDisconnect onDisconnect() /*-{
    var firebase = this.@ca.thurn.firebase.Query::firebase;
    var result = firebase.onDisconnect();
    return @ca.thurn.firebase.OnDisconnect::wrapOnDisconnect(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
  }-*/;

  public native Firebase push() /*-{
    var firebase = this.@ca.thurn.firebase.Query::firebase;
    var result = firebase.push();
    return @ca.thurn.firebase.Firebase::wrapFirebase(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
  }-*/;

  public native void removeValue() /*-{
    var firebase = this.@ca.thurn.firebase.Query::firebase;
    firebase.remove();
  }-*/;

  public native void removeValue(Firebase.CompletionListener listener) /*-{
    var firebase = this.@ca.thurn.firebase.Query::firebase;
    var onComplete = @ca.thurn.firebase.Firebase::onComplete(Lca/thurn/firebase/Firebase$CompletionListener;)(listener);
    firebase.remove(onComplete);
  }-*/;

  public void runTransaction(Transaction.Handler handler) {
    runTransaction(handler, true);
  }

  public native void runTransaction(Transaction.Handler handler, boolean fireLocalEvents) /*-{
    var firebase = this.@ca.thurn.firebase.Query::firebase;
    var updateFunction = function(currentData) {
      var mutableData = @ca.thurn.firebase.MutableData::wrapData(Lcom/google/gwt/core/client/JavaScriptObject;)(currentData);
      var result = handler.@ca.thurn.firebase.Transaction.Handler::doTransaction(Lca/thurn/firebase/MutableData;)(mutableData);
      var jsResult = @ca.thurn.firebase.Transaction.Result::getResultData(Lca/thurn/firebase/Transaction$Result;)(result);
      return jsResult;
    };
    var completionFunction = function(error, wasCommitted, snapshot) {
      var firebaseError = @ca.thurn.firebase.Firebase::wrapError(Lcom/google/gwt/core/client/JavaScriptObject;)(error);
      var dataSnapshot = @ca.thurn.firebase.DataSnapshot::wrapDataSnapshot(Lcom/google/gwt/core/client/JavaScriptObject;)(snapshot);
      handler.@ca.thurn.firebase.Transaction.Handler::onComplete(Lca/thurn/firebase/FirebaseError;ZLca/thurn/firebase/DataSnapshot;)(firebaseError, wasCommitted, dataSnapshot);
    };
    firebase.transaction(updateFunction, completionFunction, fireLocalEvents);
  }-*/;

  public void setPriority(Object priority) {
    setPriority(priority, null);
  }

  public void setPriority(Object priority, Firebase.CompletionListener listener) {
    if (priority == null || priority instanceof String) {
      setPriorityString((String)priority, listener);
    } else if (priority instanceof Double || priority instanceof Integer) {
      setPriorityDouble(((Number)priority).doubleValue(), listener);
    }
  }

  public native void setPriorityDouble(double priority, Firebase.CompletionListener listener) /*-{
    var firebase = this.@ca.thurn.firebase.Query::firebase;
    if (listener) {
      var onComplete = @ca.thurn.firebase.Firebase::onComplete(Lca/thurn/firebase/Firebase$CompletionListener;)(listener);
      firebase.setPriority(priority, onComplete);
    } else {
      firebase.setPriority(priority);
    }
  }-*/;

  public native void setPriorityString(String priority, Firebase.CompletionListener listener) /*-{
    var firebase = this.@ca.thurn.firebase.Query::firebase;
    if (listener) {
      var onComplete = @ca.thurn.firebase.Firebase::onComplete(Lca/thurn/firebase/Firebase$CompletionListener;)(listener);
      firebase.setPriority(priority, onComplete);
    } else {
      firebase.setPriority(priority);
    }
  }-*/;

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
    if (value == null) {
      if (priority != null) {
        throw new IllegalArgumentException("Cannot set a priority on a value of null.");
      }
      setValueNull(listener);
    } else if (priority instanceof Double || priority instanceof Integer) {
      setDoublePriority(value, ((Number)priority).doubleValue(), listener);
    } else if (priority == null || priority instanceof String) {
      setStringPriority(value, (String) priority, listener);
    } else {
      throw new IllegalArgumentException("Priority must be a double, integer, or string");
    }
  }

  @Override
  public native String toString() /*-{
    var firebase = this.@ca.thurn.firebase.Query::firebase;
    return firebase.toString();
  }-*/;

  public native void unauth() /*-{
    var firebase = this.@ca.thurn.firebase.Query::firebase;
    firebase.unauth();
  }-*/;

  public void updateChildren(Map<String, Object> children) {
    updateChildrenRaw(children);
  }

  public void updateChildren(Map<String, Object> children, Firebase.CompletionListener listener) {
    updateChildrenRaw(children, listener);
  }

  private native JavaScriptObject getSetValueFn() /*-{
    return function(value, priority, listener) {
      var firebase = this.@ca.thurn.firebase.Query::firebase;
      if (priority == null) {
        if (listener == null) {
          firebase.set(value);
        } else {
          var onComplete = @ca.thurn.firebase.Firebase::onComplete(Lca/thurn/firebase/Firebase$CompletionListener;)(listener);
          firebase.set(value, onComplete);
        }
      } else {
        if (listener == null) {
          firebase.setWithPriority(value, priority);
        } else {
          var onComplete = @ca.thurn.firebase.Firebase::onComplete(Lca/thurn/firebase/Firebase$CompletionListener;)(listener);
          firebase.setWithPriority(value, priority, onComplete);
        }
      }
    };
  }-*/;

  private void setDoublePriority(Object value, double priority, CompletionListener listener) {
    if (value instanceof CharSequence) {
      setValueStringDouble(value.toString(), priority, listener);
    } else if (value instanceof Number) {
      if (!(value instanceof Double || value instanceof Integer)) {
        throw new IllegalArgumentException("Only numbers of type Double or Integer can "
            + "be passed to setValue()");
      }
      setValueDoubleDouble(((Number) value).doubleValue(), priority, listener);
    } else if (value instanceof Boolean) {
      setValueBooleanDouble(((Boolean) value).booleanValue(), priority, listener);
    } else if (value instanceof Map || value instanceof List) {
      JSONValue json = toJsonValue(value);
      if (json.isArray() != null) {
        setValueObjectDouble(json.isArray().getJavaScriptObject(), priority, listener);
      } else if (json.isObject() != null) {
        setValueObjectDouble(json.isObject().getJavaScriptObject(), priority, listener);
      }
    } else {
      throw new IllegalArgumentException("Object " + value + " of type " + value.getClass()
          + " cannot be passed to setValue()");
    }
  }

  private void setStringPriority(Object value, String priority, CompletionListener listener) {
    if (value instanceof CharSequence) {
      setValueStringString(value.toString(), priority, listener);
    } else if (value instanceof Number) {
      if (!(value instanceof Double || value instanceof Integer)) {
        throw new IllegalArgumentException("Only numbers of type Double or Integer can "
            + "be passed to setValue()");
      }
      setValueDoubleString(((Number) value).doubleValue(), priority, listener);
    } else if (value instanceof Boolean) {
      setValueBooleanString(((Boolean) value).booleanValue(), priority, listener);
    } else if (value instanceof Map || value instanceof List) {
      JSONValue json = toJsonValue(value);
      if (json.isArray() != null) {
        setValueObjectString(json.isArray().getJavaScriptObject(), priority, listener);
      } else if (json.isObject() != null) {
        setValueObjectString(json.isObject().getJavaScriptObject(), priority, listener);
      }
    } else {
      throw new IllegalArgumentException("Object " + value + " of type " + value.getClass()
          + " cannot be passed to setValue()");
    }
  }

  private native void setValueBooleanDouble(boolean value, double priority,
      CompletionListener listener) /*-{
    this.@ca.thurn.firebase.Firebase::getSetValueFn()().call(this, value,
        priority, listener);
  }-*/;

  private native void setValueBooleanString(boolean value, String priority,
      CompletionListener listener) /*-{
    this.@ca.thurn.firebase.Firebase::getSetValueFn()().call(this, value,
        priority, listener);
  }-*/;

  private native void setValueDoubleDouble(double value, double priority,
      CompletionListener listener) /*-{
    this.@ca.thurn.firebase.Firebase::getSetValueFn()().call(this, value,
        priority, listener);
  }-*/;

  private native void setValueDoubleString(double value, String priority,
      CompletionListener listener) /*-{
    this.@ca.thurn.firebase.Firebase::getSetValueFn()().call(this, value,
        priority, listener);
  }-*/;

  private native void setValueNull(CompletionListener listener) /*-{
    var firebase = this.@ca.thurn.firebase.Query::firebase;
    if (listener == null) {
      firebase.set(null);
    } else {
      var onComplete = @ca.thurn.firebase.Firebase::onComplete(Lca/thurn/firebase/Firebase$CompletionListener;)(listener);
      firebase.set(null, onComplete);
    }
  }-*/;

  private native void setValueObjectDouble(JavaScriptObject value, double priority,
      CompletionListener listener) /*-{
    this.@ca.thurn.firebase.Firebase::getSetValueFn()().call(this, value,
        priority, listener);
  }-*/;

  private native void setValueObjectString(JavaScriptObject value, String priority,
      CompletionListener listener) /*-{
    this.@ca.thurn.firebase.Firebase::getSetValueFn()().call(this, value,
        priority, listener);
  }-*/;

  private native void setValueStringDouble(String value, double priority,
      CompletionListener listener) /*-{
    this.@ca.thurn.firebase.Firebase::getSetValueFn()().call(this, value,
        priority, listener);
  }-*/;

  private native void setValueStringString(String value, String priority,
      CompletionListener listener) /*-{
    this.@ca.thurn.firebase.Firebase::getSetValueFn()().call(this, value,
        priority, listener);
  }-*/;

  private native void updateChildrenRaw(@SuppressWarnings("rawtypes") Map children) /*-{
    var firebase = this.@ca.thurn.firebase.Query::firebase;
    var object = @ca.thurn.firebase.Firebase::adaptMap(Ljava/util/Map;)(children);
    firebase.update(object);
  }-*/;

  private native void updateChildrenRaw(@SuppressWarnings("rawtypes") Map children,
      Firebase.CompletionListener listener) /*-{
  	var firebase = this.@ca.thurn.firebase.Query::firebase;
	var object = @ca.thurn.firebase.Firebase::adaptMap(Ljava/util/Map;)(children);
	var onComplete = @ca.thurn.firebase.Firebase::onComplete(Lca/thurn/firebase/Firebase$CompletionListener;)(listener);
    firebase.update(object, onComplete);
  }-*/;
}
