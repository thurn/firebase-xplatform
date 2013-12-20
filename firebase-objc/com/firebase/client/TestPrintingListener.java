package com.firebase.client;

class TestPrintingListener implements ValueEventListener, ChildEventListener {

  @Override
  public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
    System.out.println(">>>>> onChildAdded " + snapshot.getValue());
  }

  @Override
  public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
    System.out.println(">>>>> onChildChanged " + snapshot.getValue());
  }

  @Override
  public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
    System.out.println(">>>>> onChildMoved " + snapshot.getValue());
  }

  @Override
  public void onChildRemoved(DataSnapshot snapshot) {
    System.out.println(">>>>> onChildRemoved " + snapshot.getValue());
  }

  @Override
  public void onCancelled() {
    System.out.println(">>>>> onCancelled");
  }

  @Override
  public void onDataChange(DataSnapshot snapshot) {
    System.out.println(">>>>> onDataChange " + snapshot.getValue());
  }

}
