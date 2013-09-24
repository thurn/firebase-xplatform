package ca.thurn.firebase;

import com.google.gwt.core.client.JavaScriptObject;

class JavascriptValueEventListener implements ValueEventListener {
	
	private final JavaScriptObject callback;
	
	private JavascriptValueEventListener(JavaScriptObject callback) {
		this.callback = callback;
	}
	
	static JavascriptValueEventListener wrapCallback(JavaScriptObject callback) {
		return new JavascriptValueEventListener(callback);
	}
	
	@Override
	public void onCancelled() {
		throw new UnsupportedOperationException("You cannot re-use the listener you get back"
				+ "from addEventListener");
	}
	
	@Override
	public void onDataChange(DataSnapshot snapshot) {
		throw new UnsupportedOperationException("You cannot re-use the listener you get back"
				+ "from addEventListener");
	}
	
	JavaScriptObject getCallback() {
		return callback;
	}

}
