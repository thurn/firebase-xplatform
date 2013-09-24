package ca.thurn.firebase;

import com.google.gwt.core.client.JavaScriptObject;

public class MutableData {
	private final JavaScriptObject currentData;
	private JavaScriptObject newData;
	
	private MutableData(JavaScriptObject currentData) {
		this.currentData = currentData;
	}
	
	static MutableData wrapData(JavaScriptObject data) {
		return new MutableData(data);
	}
	
	void setNewData(JavaScriptObject newData) {
		this.newData = newData;
	}
	
	JavaScriptObject getNewData() {
		return newData;
	}
	
	public String child(String path) {
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
		return this.@ca.thurn.firebase.MutableData::currentData;
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
	
	public native void setValue(Object value) /*-{
		this.@ca.thurn.firebase.MutableData::setNewData(Lcom/google/gwt/core/client/JavaScriptObject;)(value);
	}-*/;
}
