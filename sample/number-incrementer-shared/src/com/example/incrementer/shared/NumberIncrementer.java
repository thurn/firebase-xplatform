package com.example.incrementer.shared;

import org.eclipse.xtext.xbase.lib.Procedures;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.Transaction.Handler;
import com.firebase.client.Transaction.Result;

public class NumberIncrementer {
  private final Firebase firebase;
  
  public NumberIncrementer(Firebase firebase) {
    this.firebase = firebase;
  }
  
  public void increment(final Procedures.Procedure1<Long> callback) {
    firebase.child("value").runTransaction(new Handler() {

      @Override
      public Result doTransaction(MutableData currentData) {
        Long value = currentData.getValue(Long.class);
        currentData.setValue(value == null ? 1 : value + 1);
        return Transaction.success(currentData);
      }

      @Override
      public void onComplete(FirebaseError error, boolean committed, DataSnapshot currentData) {
        callback.apply(currentData.getValue(Long.class));
      }

    });
  }
}
