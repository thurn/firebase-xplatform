package com.firebase.client;

import com.google.gwt.core.client.JavaScriptObject;

class JavascriptValueEventListener implements ValueEventListener {

  static JavascriptValueEventListener wrapCallback(JavaScriptObject callback) {
    return new JavascriptValueEventListener(callback);
  }

  private final JavaScriptObject callback;

  private JavascriptValueEventListener(JavaScriptObject callback) {
    this.callback = callback;
  }

  @Override
  public void onCancelled(FirebaseError error) {
    throw new UnsupportedOperationException("You cannot re-use the listener you get back "
        + "from addEventListener");
  }

  @Override
  public void onDataChange(DataSnapshot snapshot) {
    throw new UnsupportedOperationException("You cannot re-use the listener you get back "
        + "from addEventListener");
  }

  JavaScriptObject getCallback() {
    return callback;
  }

}
