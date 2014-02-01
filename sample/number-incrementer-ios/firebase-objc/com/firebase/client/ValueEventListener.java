package com.firebase.client;


public interface ValueEventListener {
  void onCancelled();

  void onDataChange(DataSnapshot snapshot);
}
