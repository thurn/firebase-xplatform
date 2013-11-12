package com.firebase.client;

import com.google.gwt.core.client.JavaScriptObject;

public class FirebaseError {
  private JavaScriptObject error;

  FirebaseError(JavaScriptObject error) {
    this.error = error;
  }

  @Override
  public String toString() {
    return error.toString();
  }
}
