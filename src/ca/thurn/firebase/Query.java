package ca.thurn.firebase;

import com.google.gwt.core.client.JavaScriptObject;

public class Query {
	protected JavaScriptObject firebase;
	
	protected Query(JavaScriptObject firebase) {
		this.firebase = firebase;
	}
	
	private static Query wrapQuery(JavaScriptObject firebase) {
		return new Query(firebase);
	}
	
	public native ChildEventListener addChildEventListener(ChildEventListener listener) /*-{
		var firebase = this.@ca.thurn.firebase.Query::firebase;
		var addedCallback = function(snapshot, previous) {
			var javaSnapshot = @ca.thurn.firebase.DataSnapshot::wrapDataSnapshot(Lcom/google/gwt/core/client/JavaScriptObject;)(snapshot);
			listener.@ca.thurn.firebase.ChildEventListener::onChildAdded(Lca/thurn/firebase/DataSnapshot;Ljava/lang/String;)(javaSnapshot, previous);
		};
		var changedCallback = function(snapshot, previous) {
			var javaSnapshot = @ca.thurn.firebase.DataSnapshot::wrapDataSnapshot(Lcom/google/gwt/core/client/JavaScriptObject;)(snapshot);
			listener.@ca.thurn.firebase.ChildEventListener::onChildChanged(Lca/thurn/firebase/DataSnapshot;Ljava/lang/String;)(javaSnapshot, previous);
		};
		var removedCallback = function(snapshot) {
			var javaSnapshot = @ca.thurn.firebase.DataSnapshot::wrapDataSnapshot(Lcom/google/gwt/core/client/JavaScriptObject;)(snapshot);
			listener.@ca.thurn.firebase.ChildEventListener::onChildRemoved(Lca/thurn/firebase/DataSnapshot;)(snapshot);
		};
		var movedCallback = function(snapshot, previous) {
			var javaSnapshot = @ca.thurn.firebase.DataSnapshot::wrapDataSnapshot(Lcom/google/gwt/core/client/JavaScriptObject;)(snapshot);
			listener.@ca.thurn.firebase.ChildEventListener::onChildMoved(Lca/thurn/firebase/DataSnapshot;Ljava/lang/String;)(javaSnapshot, previous);
		};
		var cancelCallback = function() {
			listener.@ca.thurn.firebase.ChildEventListener::onCancelled()();
		};
		firebase.on("child_added", addedCallback, cancelCallback);
		firebase.on("child_changed", changedCallback, cancelCallback);
		firebase.on("child_removed", removedCallback, cancelCallback);
		firebase.on("child_moved", movedCallback, cancelCallback);
		return @ca.thurn.firebase.JavascriptChildEventListener::wrapCallbacks(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(addedCallback, changedCallback, removedCallback, movedCallback);
	}-*/;
	
	public native void addListenerForSingleValueEvent(ValueEventListener listener) /*-{
		var firebase = this.@ca.thurn.firebase.Query::firebase;
		var callback = function(snapshot) {
			var javaSnapshot = @ca.thurn.firebase.DataSnapshot::wrapDataSnapshot(Lcom/google/gwt/core/client/JavaScriptObject;)(snapshot);
			listener.@ca.thurn.firebase.ValueEventListener::onDataChange(Lca/thurn/firebase/DataSnapshot;)(javaSnapshot);
		};
		var cancelCallback = function() {
			listener.@ca.thurn.firebase.ValueEventListener::onCancelled()();
		};
		firebase.once("value", callback, cancelCallback);
	}-*/;
	
	public native ValueEventListener addValueEventListener(ValueEventListener listener) /*-{
		var firebase = this.@ca.thurn.firebase.Query::firebase;
		var callback = function(snapshot) {
			var javaSnapshot = @ca.thurn.firebase.DataSnapshot::wrapDataSnapshot(Lcom/google/gwt/core/client/JavaScriptObject;)(snapshot);
			listener.@ca.thurn.firebase.ValueEventListener::onDataChange(Lca/thurn/firebase/DataSnapshot;)(javaSnapshot);
		};
		var cancelCallback = function() {
			listener.@ca.thurn.firebase.ValueEventListener::onCancelled()();
		};
		firebase.on("value", callback, cancelCallback);
		return @ca.thurn.firebase.JavascriptValueEventListener::wrapCallback(Lcom/google/gwt/core/client/JavaScriptObject;)(callback);
	}-*/;
	
	public native Query endAt() /*-{
		var firebase = this.@ca.thurn.firebase.Query::firebase;
		firebase.endAt();
		return @ca.thurn.firebase.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(firebase);
	}-*/;
	
	public native Query endAt(double priority) /*-{
		var firebase = this.@ca.thurn.firebase.Query::firebase;
		firebase.endAt(priority);
		return @ca.thurn.firebase.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(firebase);
	}-*/;
	
	public native Query endAt(double priority, String name) /*-{
		var firebase = this.@ca.thurn.firebase.Query::firebase;
		firebase.endAt(priority, name);
		return @ca.thurn.firebase.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(firebase);
	}-*/;
	
	public native Query endAt(String priority) /*-{
		var firebase = this.@ca.thurn.firebase.Query::firebase;
		firebase.endAt(priority);
		return @ca.thurn.firebase.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(firebase);
	}-*/;
	
	public native Query endAt(String priority, String name) /*-{
		var firebase = this.@ca.thurn.firebase.Query::firebase;
		firebase.endAt(priority, name);
		return @ca.thurn.firebase.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(firebase);
	}-*/;
	
	public native Firebase getRef() /*-{
		var firebase = this.@ca.thurn.firebase.Query::firebase;
		return firebase;
	}-*/;
	
	public native Query limit(int limit) /*-{
		var firebase = this.@ca.thurn.firebase.Query::firebase;
		firebase.limit(limit);
		return @ca.thurn.firebase.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(firebase);
	}-*/;
	
	public void removeEventListener(ChildEventListener listener) {
		if (!(listener instanceof JavascriptChildEventListener)) {
			throw new IllegalArgumentException("The listener provided to "
					+ "removeEventListener was not returned by a call to addEventListener");
		}
		JavascriptChildEventListener jsListener = (JavascriptChildEventListener)listener;
		removeChildEventListener(jsListener.getAddedCallback(), jsListener.getChangedCallback(),
				jsListener.getRemovedCallback(), jsListener.getMovedCallback());
	}
	
	private native void removeChildEventListener(JavaScriptObject addedCallback,
			JavaScriptObject changedCallback, JavaScriptObject removedCallback,
			JavaScriptObject movedCallback) /*-{
				var firebase = this.@ca.thurn.firebase.Query::firebase;
				firebase.off("child_added", addedCallback);
				firebase.off("child_changed", changedCallback);
				firebase.off("child_removed", removedCallback);
				firebase.off("child_moved", movedCallback);
			}-*/;
	
	public void removeEventListener(ValueEventListener listener) {
		if (!(listener instanceof JavascriptValueEventListener)) {
			throw new IllegalArgumentException("The listener provided to "
					+ "removeEventListener was not returned by a call to addEventListener");
		}
		JavascriptValueEventListener jsListener = (JavascriptValueEventListener)listener;
		removeValueEventListener(jsListener.getCallback());
	}
	
	private native void removeValueEventListener(JavaScriptObject callback) /*-{
		var firebase = this.@ca.thurn.firebase.Query::firebase;
		firebase.off("value", callback);
	}-*/;
	
	public native Query startAt() /*-{
		var firebase = this.@ca.thurn.firebase.Query::firebase;
		firebase.startAt();
		return @ca.thurn.firebase.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(firebase);
	}-*/;
	
	public native Query startAt(double priority) /*-{
		var firebase = this.@ca.thurn.firebase.Query::firebase;
		firebase.startAt(priority);
		return @ca.thurn.firebase.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(firebase);
	}-*/;
	
	public native Query startAt(double priority, String name) /*-{
		var firebase = this.@ca.thurn.firebase.Query::firebase;
		firebase.startAt(priority, name);
		return @ca.thurn.firebase.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(firebase);	}-*/;
	
	public native Query startAt(String priority) /*-{
		var firebase = this.@ca.thurn.firebase.Query::firebase;
		firebase.startAt(priority);
		return @ca.thurn.firebase.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(firebase);	}-*/;
	
	public native Query startAt(String priority, String name) /*-{
		var firebase = this.@ca.thurn.firebase.Query::firebase;
		firebase.startAt(priority, name);
		return @ca.thurn.firebase.Query::wrapQuery(Lcom/google/gwt/core/client/JavaScriptObject;)(firebase);	}-*/;
}
