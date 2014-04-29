package com.firebase.client;


public interface ValueEventListener {
  void onCancelled(FirebaseError error);

  void onDataChange(DataSnapshot snapshot);
}
