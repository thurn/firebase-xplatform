package ca.thurn.firebase;

public interface ValueEventListener {
  void onCancelled();

  void onDataChange(DataSnapshot snapshot);
}
