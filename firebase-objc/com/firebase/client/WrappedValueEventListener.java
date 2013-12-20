package com.firebase.client;

class WrappedValueEventListener implements ValueEventListener {

  long handle;
  
  WrappedValueEventListener(long handle) {
    this.handle = handle;
  }
  
  @Override
  public void onCancelled() {
    throw new RuntimeException("You cannot re-use the listener you get back from addValueEventListener()");
  }

  @Override
  public void onDataChange(DataSnapshot snapshot) {
    throw new RuntimeException("You cannot re-use the listener you get back from addValueEventListener()");
  }
  
  long getHandle() {
    return handle;
  }

}
