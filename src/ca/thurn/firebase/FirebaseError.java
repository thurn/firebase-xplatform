package ca.thurn.firebase;

import com.google.gwt.core.client.JavaScriptObject;

public class FirebaseError {
	private JavaScriptObject error;
	
	FirebaseError(JavaScriptObject error) {
		this.error = error;
	}
	
	public String toString() {
		return error.toString();
	}
}
