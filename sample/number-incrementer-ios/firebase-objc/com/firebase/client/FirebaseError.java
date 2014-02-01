package com.firebase.client;


public class FirebaseError {
  private Object error;

  FirebaseError(Object error) {
    this.error = error;
  }

  @Override
  public String toString() {
    return error.toString();
  }
}
