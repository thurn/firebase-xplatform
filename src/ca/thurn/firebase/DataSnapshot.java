package ca.thurn.firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public class DataSnapshot {
	@SuppressWarnings("rawtypes")
	private static Iterable<DataSnapshot> adaptArray(JsArray array) {
		ArrayList<DataSnapshot> result = new ArrayList<DataSnapshot>(array.length());
		for (int i = 0; i < array.length(); ++i) {
			result.add(wrapDataSnapshot(array.get(i)));
		}
		return result;
	}
	
	private static Object fromJavaScriptBoolean(boolean bool) {
		return bool;
	}
	
	private static Object fromJavaScriptNull() {
		return null;
	}
	
	private static Object fromJavaScriptNumber(double number) {
		if (number == (int)number) {
			return (int)number;
		} else {
			return number;
		}
	}
	
	private static Object fromJavaScriptObject(JavaScriptObject object) {
		JSONObject json = new JSONObject(object);
		return fromJsonValue(json);
	}
	
	private static Object fromJavaScriptString(String string) {
		return string;
	}
	
	private static Object fromJsArrayMixed(JsArrayMixed array) {
		List<Object> result = new ArrayList<Object>(array.length());
		for (int i = 0; i < array.length(); ++i) {
			String type = typeOfIndex(array, i);
			if (type.equals("string")) {
				result.add(array.getString(i));
			} else if (type.equals("number")) {
				result.add(fromJavaScriptNumber(array.getNumber(i)));
			} else if (type.equals("boolean")) {
				result.add(array.getBoolean(i));
			} else if (type.equals("object")){
				result.add(getJavaValue(array.getObject(i)));
			} else if (type.equals("undefined")) {
				result.add(null);
			} else {
				throw new RuntimeException("Unable to handle type " + type);
			}
		}
		return result;
	}
	
	private static Object fromJsonValue(JSONValue value) {
		if (value.isBoolean() != null) {
			return value.isBoolean().booleanValue();
		} else if (value.isString() != null) {
			return value.isString().stringValue();
		} else if (value.isNumber() != null) {
			return fromJavaScriptNumber(value.isNumber().doubleValue());
		} else if (value.isNull() != null) {
			return null;
		} else if (value.isArray() != null) {
			JSONArray array = value.isArray();
			List<Object> result = new ArrayList<Object>(array.size());
			for (int i = 0; i < array.size(); ++i) {
				Object element = array.get(i);
				if (element instanceof JSONValue) {
					result.add(fromJsonValue((JSONValue)element));
				} else {
					result.add(element);
				}
			}
			return result;
		} else if (value.isObject() != null) {
			JSONObject object = value.isObject();
			Map<String, Object> result = new HashMap<String, Object>();
			for (String key : object.keySet()) {
				Object element = object.get(key);
				if (element instanceof JSONValue) {
					result.put(key, fromJsonValue((JSONValue)element));
				} else if (element instanceof JavaScriptObject){
					result.put(key, getJavaValue((JavaScriptObject)element));
				} else {
					result.put(key, element);
				}
			}
			return result;
		}
		throw new RuntimeException("Unable to decode JSONValue " + value);
	}
	
	private static native Object getJavaValue(JavaScriptObject jso) /*-{
		return @ca.thurn.firebase.DataSnapshot::getJavaValueFn()()(jso);
	}-*/;
	
	private static native JavaScriptObject getJavaValueFn() /*-{
		return function(val) {
			if (val === null) {
				return @ca.thurn.firebase.DataSnapshot::fromJavaScriptNull()();
			} else if (typeof val === "number") {
				return @ca.thurn.firebase.DataSnapshot::fromJavaScriptNumber(D)(val);
			} else if (typeof val === "string") {
				return @ca.thurn.firebase.DataSnapshot::fromJavaScriptString(Ljava/lang/String;)(val);
			} else if (typeof val === "boolean") {
				return @ca.thurn.firebase.DataSnapshot::fromJavaScriptBoolean(Z)(val);
			} else if (val.length != null) {
				return @ca.thurn.firebase.DataSnapshot::fromJsArrayMixed(Lcom/google/gwt/core/client/JsArrayMixed;)(val);
			} else if (typeof val === "object") {
				return @ca.thurn.firebase.DataSnapshot::fromJavaScriptObject(Lcom/google/gwt/core/client/JavaScriptObject;)(val);
			} else {
				throw new Error("Unable to get java value for " + val);
			}
		};
	}-*/;
	
	private static native JsArrayMixed toMixedArray(JavaScriptObject object) /*-{
		return object;
	}-*/;
	
	private static native String typeOfIndex(JsArrayMixed array, int index) /*-{
		return typeof(array[index]);
	}-*/;
	
	static DataSnapshot wrapDataSnapshot(JavaScriptObject snapshot) {
		return new DataSnapshot(snapshot);
	}

	private final JavaScriptObject snapshot;
	
	DataSnapshot(JavaScriptObject snapshot) {
		this.snapshot = snapshot;
	}
	
	public native DataSnapshot child(String path) /*-{
		var snapshot = this.@ca.thurn.firebase.DataSnapshot::snapshot;
		var result = snapshot.child(path);
		return @ca.thurn.firebase.DataSnapshot::wrapDataSnapshot(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
	}-*/;
	
	public native Iterable<DataSnapshot> getChildren() /*-{
		var snapshot = this.@ca.thurn.firebase.DataSnapshot::snapshot;
		var array = [];
		var childAction = function(child) {
			array.push(child);
		}
		snapshot.forEach(childAction);
		return @ca.thurn.firebase.DataSnapshot::adaptArray(Lcom/google/gwt/core/client/JsArray;)(array);
	}-*/;
	
	public long getChildrenCount() {
		return getChildrenCountInt();
	}
	
	public native String getName() /*-{
		var snapshot = this.@ca.thurn.firebase.DataSnapshot::snapshot;
		return snapshot.name();
	}-*/;
	
	public native Object getPriority() /*-{
		var snapshot = this.@ca.thurn.firebase.DataSnapshot::snapshot;
		return @ca.thurn.firebase.DataSnapshot::getJavaValueFn()()(snapshot.getPriority());
	}-*/;
	
	public native Firebase getRef() /*-{
		var snapshot = this.@ca.thurn.firebase.DataSnapshot::snapshot;
		var result = snapshot.ref();
		return @ca.thurn.firebase.Firebase::wrapFirebase(Lcom/google/gwt/core/client/JavaScriptObject;)(result);
	}-*/;

	public native Object getValue() /*-{
		var snapshot = this.@ca.thurn.firebase.DataSnapshot::snapshot;
		var val = snapshot.val();
		return @ca.thurn.firebase.DataSnapshot::getJavaValueFn()()(val);
	}-*/;
	
	public native Object getValue(boolean useExportFormat) /*-{
		var snapshot = this.@ca.thurn.firebase.DataSnapshot::snapshot;
		if (useExportFormat) {
			return snapshot.exportVal();
		} else {
			return snapshot.val();
		}
	}-*/;
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(Class<T> valueType) {
		return (T)getValue();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(GenericTypeIndicator<T> t) {
		return (T)getValue();
	}
	
	public native boolean hasChild(String path) /*-{
		var snapshot = this.@ca.thurn.firebase.DataSnapshot::snapshot;
		return snapshot.hasChild(path);
	}-*/;
	
	public native boolean hasChildren() /*-{
		var snapshot = this.@ca.thurn.firebase.DataSnapshot::snapshot;
		return snapshot.hasChildren();
	}-*/;
	
	private native int getChildrenCountInt() /*-{
		var snapshot = this.@ca.thurn.firebase.DataSnapshot::snapshot;
		return snapshot.numChildren();
	}-*/;
}
