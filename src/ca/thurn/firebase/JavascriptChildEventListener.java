package ca.thurn.firebase;

import com.google.gwt.core.client.JavaScriptObject;

class JavascriptChildEventListener implements ChildEventListener {

	private final JavaScriptObject addedCallback;
	private final JavaScriptObject changedCallback;
	private final JavaScriptObject removedCallback;
	private final JavaScriptObject movedCallback;
	
	private JavascriptChildEventListener(JavaScriptObject addedCallback, JavaScriptObject changedCallback,
			JavaScriptObject removedCallback, JavaScriptObject movedCallback) {
		this.addedCallback = addedCallback;
		this.changedCallback = changedCallback;
		this.removedCallback = removedCallback;
		this.movedCallback = movedCallback;
	}
	
	static JavascriptChildEventListener wrapCallbacks(
			JavaScriptObject addedCallback, JavaScriptObject changedCallback,
			JavaScriptObject removedCallback, JavaScriptObject movedCallback) {
		return new JavascriptChildEventListener(addedCallback, changedCallback,
				removedCallback, movedCallback);
	}

	@Override
	public void onCancelled() {
		throw new UnsupportedOperationException("You cannot re-use the listener you get back"
				+ "from addEventListener");
	}

	@Override
	public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
		throw new UnsupportedOperationException("You cannot re-use the listener you get back"
				+ "from addEventListener");
	}

	@Override
	public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
		throw new UnsupportedOperationException("You cannot re-use the listener you get back"
				+ "from addEventListener");
	}

	@Override
	public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
		throw new UnsupportedOperationException("You cannot re-use the listener you get back"
				+ "from addEventListener");
	}

	@Override
	public void onChildRemoved(DataSnapshot snapshot) {
		throw new UnsupportedOperationException("You cannot re-use the listener you get back"
				+ "from addEventListener");
	}

	JavaScriptObject getAddedCallback() {
		return addedCallback;
	}

	JavaScriptObject getChangedCallback() {
		return changedCallback;
	}

	JavaScriptObject getRemovedCallback() {
		return removedCallback;
	}

	JavaScriptObject getMovedCallback() {
		return movedCallback;
	}

}
