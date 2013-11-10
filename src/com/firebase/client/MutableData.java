package com.firebase.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONValue;

public class MutableData {
  static MutableData wrapData(JavaScriptObject data) {
    return new MutableData(data);
  }

  private Object result;
  private final JavaScriptObject currentData;

  private MutableData(JavaScriptObject currentData) {
    this.currentData = currentData;
  }

  public MutableData child(String path) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public Iterable<MutableData> getChildren() {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public long getChildrenCount() {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public String getName() {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public MutableData getParent() {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public Object getPriority() {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public native Object getValue() /*-{
	var current = this.@com.firebase.client.MutableData::currentData;
	return @com.firebase.client.DataSnapshot::getJavaValueFn()()(current.mutableData);
  }-*/;

  public <T> T getValue(Class<T> valueType) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public <T> T getValue(GenericTypeIndicator<T> t) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public boolean hasChild(String path) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public boolean hasChildren() {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public void setPriority(Object priority) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public void setValue(Object value) {
    result = value;
  }
  
  private native JavaScriptObject jsString(String string) /*-{
    return {val: string};
  }-*/;
  
  private native JavaScriptObject jsNumber(double number) /*-{
    return {val: number};
  }-*/;
  
  private native JavaScriptObject jsBoolean(boolean bool) /*-{
    return {val: bool};
  }-*/;
  
  private native JavaScriptObject jsNull() /*-{
    return {val: null};
  }-*/;
  
  private native JavaScriptObject jsObject(JavaScriptObject object) /*-{
    return {val: object}
  }-*/;
  
  JavaScriptObject getNewData() {
    if (result == null){
      return jsNull();
    } else if (result instanceof CharSequence) {
      return jsString(result.toString());
    } else if (result instanceof Number) {
      return jsNumber(((Number) result).doubleValue());
    } else if (result instanceof Boolean) {
      return jsBoolean(((Boolean) result).booleanValue());
    } else if (result instanceof Map || result instanceof List) {
      JSONValue json = Firebase.toJsonValue(result);
      if (json.isArray() != null) {
        return jsObject(json.isArray().getJavaScriptObject());
      } else if (json.isObject() != null) {
        return jsObject(json.isObject().getJavaScriptObject());
      } else {
        throw new IllegalArgumentException("Map or List didn't convert to JSON");
      }
    } else {
      throw new IllegalArgumentException("Object " + result + " of type " + result.getClass()
          + " cannot be passed to getNewData()");
    }
  }
}
