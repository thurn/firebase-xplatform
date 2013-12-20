package com.example.incrementer.shared;

import org.eclipse.xtext.xbase.lib.Procedures;

import com.firebase.client.Firebase;
import com.firebase.client.Firebase.CompletionListener;
import com.firebase.client.FirebaseError;

import ca.thurn.testing.SharedTestCase;

public class NumberIncrementerTest extends SharedTestCase {
  private Firebase firebase;
  
  @Override
  public void sharedSetUp(final Runnable done) {
    firebase = new Firebase("https://incrementer-test.firebaseio-demo.com");
    firebase.child("value").setValue(42, new CompletionListener() {
      @Override
      public void onComplete(FirebaseError error, Firebase ref) {
        done.run();
      }
    });
  }

  public void testIncrementNumber() {
    beginAsyncTestBlock();
    NumberIncrementer incrementer = new NumberIncrementer(firebase);
    incrementer.increment(new Procedures.Procedure1<Long>() {
      @Override
      public void apply(Long numIncrements) {
        assertEquals(new Long(43), numIncrements);
        finished();
      }
    });
    endAsyncTestBlock();
  }
}
