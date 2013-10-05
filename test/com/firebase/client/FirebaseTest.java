package com.firebase.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;

public class FirebaseTest extends GWTTestCase {

  static class TestChildEventListener implements ChildEventListener {

    @Override
    public void onCancelled() {
      fail("unexpected cancellation");
    }

    @Override
    public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
    }

    @Override
    public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
    }

    @Override
    public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
    }

    @Override
    public void onChildRemoved(DataSnapshot snapshot) {
    }

  }

  static abstract class TestValueEventListener implements ValueEventListener {
    @Override
    public void onCancelled() {
      fail("Unexpected cancellation");
    }
  }

  private static void assertDeepEquals(String msg, Object o1, Object o2) {
    if (o1 instanceof Iterable && o2 instanceof Iterable) {
      @SuppressWarnings("unchecked")
      Iterator<Object> ite1 = ((Iterable<Object>) o1).iterator();
      @SuppressWarnings("unchecked")
      Iterator<Object> ite2 = ((Iterable<Object>) o2).iterator();
      while (ite1.hasNext() && ite2.hasNext()) {
        assertDeepEquals(msg, ite1.next(), ite2.next());
      }
      assertFalse("Iterable sizes differ", ite1.hasNext() || ite2.hasNext());
    } else if (o1 instanceof Map && o2 instanceof Map) {
      @SuppressWarnings("unchecked")
      Map<Object, Object> map1 = (Map<Object, Object>) o1;
      @SuppressWarnings("unchecked")
      Map<Object, Object> map2 = (Map<Object, Object>) o2;
      assertEquals("Map sizes differ", map1.size(), map2.size());
      for (Map.Entry<Object, Object> entry : map1.entrySet()) {
        assertTrue(map2.containsKey(entry.getKey()));
        assertDeepEquals(msg, entry.getValue(), map2.get(entry.getKey()));
      }
    } else {
      assertEquals(msg, o1, o2);
    }
  }

  private boolean didSetup = false;

  @Override
  protected void gwtSetUp() throws Exception {
    delayTestFinish(10000);
    if (didSetup == false) {
      ScriptInjector.fromUrl("https://cdn.firebase.com/v0/firebase.js")
          .setCallback(new Callback<Void, Exception>() {
            @Override
            public void onFailure(Exception reason) {}

            @Override
            public void onSuccess(Void result) {
              didSetup = true;
              finishTest();
            }
          }).inject();
    } else {
      finishTest();
    }
  }

  @Override
  public String getModuleName() {
    return "com.firebase.Firebase";
  }

  public void testChild() {
    String name = randomName();
    Firebase firebase = new Firebase("http://www.example.com/foo");
    Firebase child = firebase.child(name);
    assertEquals(name, child.getName());
    assertEquals("foo", child.getParent().getName());
  }

  public void testGetClassLiteral() {
    delayTestFinish(5000);
    Firebase child = makeFirebase();
    child.addListenerForSingleValueEvent(new TestValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        Long value = snapshot.getValue(Long.class);
        assertEquals(new Long(123), value);
        finishTest();
      }
    });
    child.setValue(123L);
  }

  public void testGetGenericTypeIndicator() {
    delayTestFinish(5000);
    Firebase child = makeFirebase();
    final List<Object> list = new ArrayList<Object>();
    list.add("one");
    list.add(2.3);
    list.add(true);
    child.addListenerForSingleValueEvent(new TestValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        List<Object> result = snapshot.getValue(new GenericTypeIndicator<List<Object>>() {});
        assertDeepEquals("list value was wrong", list, result);
        finishTest();
      }
    });
    child.setValue(list);
  }

  public void testGetName() {
    Firebase firebase = new Firebase("http://www.example.com/foo");
    assertEquals("foo", firebase.getName());
  }

  public void testNullMapException() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("one", true);
    map.put("two", null);
    try {
      runTestSet(map);
      fail("Expected exception from null map value");
    } catch (IllegalArgumentException expected) {
      finishTest();
    }
  }

  public void testNullRootName() {
    Firebase firebase = new Firebase("http://www.example.com/foo");
    assertEquals(null, firebase.getParent().getName());
    assertEquals(null, firebase.getRoot().getName());
  }

  public void testSetBoolean() {
    runTestSet(true);
  }

  public void testSetByteException() {
    try {
      runTestSet((byte) 12);
      fail("Expected exception setting byte");
    } catch (IllegalArgumentException expected) {
      finishTest();
    }
  }

  public void testSetDouble() {
    runTestSet(41.0000000000001);
  }

  public void testSetEmptyList() {
    List<Double> doubles = new ArrayList<Double>();
    delayTestFinish(5000);
    Firebase child = makeFirebase();
    child.addListenerForSingleValueEvent(new TestValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        assertEquals(null, snapshot.getValue());
        finishTest();
      }
    });
    child.setValue(doubles);
  }

  public void testSetEmptyMap() {
    Map<String, String> map = new HashMap<String, String>();
    delayTestFinish(5000);
    Firebase child = makeFirebase();
    child.addListenerForSingleValueEvent(new TestValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        assertEquals(null, snapshot.getValue());
        finishTest();
      }
    });
    child.setValue(map);
  }

  public void testSetInt() {
    runTestSet(42L);
  }

  public void testSetLongList() {
    List<Long> list = new ArrayList<Long>();
    list.add(123L);
    runTestSet(list);
  }

  public void testSetLongMap() {
    Map<String, Long> map = new HashMap<String, Long>();
    map.put("123", 123L);
    runTestSet(map);
  }

  public void testSetList() {
    List<Double> doubles = new ArrayList<Double>();
    doubles.add(12.2);
    doubles.add(24.3);
    runTestSet(doubles);
  }

  public void testSetListInsideMap() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("one", "one");
    List<Object> list = new ArrayList<Object>();
    list.add("one");
    list.add(2.222);
    map.put("list", list);
    runTestSet(map);
  }

  public void testSetIntException() {
    try {
      runTestSet(123);
      fail("Expected exception setting integer");
    } catch (IllegalArgumentException expected) {
      finishTest();
    }
  }

  public void testSetMapDoubles() {
    Map<String, Double> doubles = new HashMap<String, Double>();
    doubles.put("one", 11.1);
    doubles.put("two", 22.2);
    runTestSet(doubles);
  }

  public void testSetMapInsideList() {
    List<Object> list = new ArrayList<Object>();
    list.add("one");
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("two", 2.230);
    map.put("three", true);
    list.add(map);
    runTestSet(list);
  }

  public void testSetMixedList() {
    List<Object> list = new ArrayList<Object>();
    list.add("one");
    list.add(21L);
    list.add(true);
    runTestSet(list);
  }

  public void testSetMixedMap() {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("one", true);
    map.put("two", 3L);
    map.put("three", "four");
    runTestSet(map);
  }

  public void testSetNestedLists() {
    List<Object> list = new ArrayList<Object>();
    list.add("one");
    List<Object> sublist = new ArrayList<Object>();
    sublist.add(2.1);
    sublist.add(false);
    list.add(sublist);
    runTestSet(list);
  }

  public void testSetNestedMaps() {
    Map<String, Object> map = new HashMap<String, Object>();
    Map<String, Object> one = new HashMap<String, Object>();
    one.put("one", 1L);
    Map<String, Object> two = new HashMap<String, Object>();
    two.put("two", "two");
    map.put("one", one);
    map.put("two", two);
    runTestSet(map);
  }

  public void testSetNull() {
    runTestSet(null);
  }

  public void testSetNullsListException() {
    List<Object> list = new ArrayList<Object>();
    list.add("one");
    list.add(2.340);
    list.add(null);
    try {
      runTestSet(list);
      fail("Expected null value in a list to cause an exception");
    } catch (IllegalArgumentException expected) {
      finishTest();
    }
  }

  public void testSetString() {
    runTestSet("str");
  }

  public void testUpdateReadOnce() {
    delayTestFinish(5000);
    Firebase child = makeFirebase();
    Firebase alpha = child.child("alpha");
    alpha.addListenerForSingleValueEvent(new TestValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        assertEquals("fred", snapshot.getValue());
      }
    });
    Firebase beta = child.child("beta");
    beta.addListenerForSingleValueEvent(new TestValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        assertEquals(2L, snapshot.getValue());
        finishTest();
      }
    });
    Map<String, Object> update = new HashMap<String, Object>();
    update.put("alpha", "fred");
    update.put("beta", 2L);
    child.updateChildren(update);
  }

  public void testAddChild() {
    delayTestFinish(5000);
    Firebase firebase = makeFirebase();
    Firebase child = firebase.child("foo");
    firebase.addChildEventListener(new TestChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot snapshot, String previousName) {
        assertEquals("va", snapshot.getValue());
        assertNull(previousName);
        finishTest();
      }
    });
    child.setValue("va");
  }

  public void testAddRemoveChild() {
    delayTestFinish(5000);
    Firebase fb = makeFirebase();
    final Firebase child = fb.child("new");
    fb.addChildEventListener(new TestChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot snapshot, String previousName) {
        assertEquals(1234L, snapshot.getValue());
        assertNull(previousName);
        child.removeValue();
      }

      @Override
      public void onChildRemoved(DataSnapshot snapshot) {
        assertEquals(1234L, snapshot.getValue());
        finishTest();
      }
    });
    child.setValue(1234L);
  }

  public void testChangeChild() {
    delayTestFinish(5000);
    final Firebase fb = makeFirebase();
    final Firebase child = fb.child("new");
    fb.addChildEventListener(new TestChildEventListener() {
      @Override
      public void onChildChanged(DataSnapshot snapshot, String prevChild) {
        assertEquals(4321L, snapshot.getValue());
        finishTest();
      }
    });
    child.setValue(1234L);
    child.setValue(4321L);
  }

  public void testMoveChild() {
    delayTestFinish(5000);
    final Firebase fb = makeFirebase();
    final Firebase child = fb.child("new");
    final Firebase one = child.child("one");
    child.addChildEventListener(new TestChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
        one.setPriority(333L);
      }
      @Override
      public void onChildMoved(DataSnapshot snapshot, String prevChild) {
        assertEquals(111L, snapshot.getValue());
        assertEquals("zero", prevChild);
        finishTest();
      }
    });
    child.child("zero").setValue(9L, 9L);
    one.setValue(111L, 111L);
    child.child("two").setValue(222L, 222L);
    child.child("three").setValue(444L, 444L);
  }

  public void testRemoveChildListener() {
    delayTestFinish(5000);
    Firebase firebase = makeFirebase();
    Firebase child = firebase.child("child");
    final boolean[] failed = {false};
    ChildEventListener listener = firebase.addChildEventListener(new TestChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot snapshot, String previousName) {
        failed[0] = true;
        fail("Unexpected call to onChildAdded");
      }
    });
    firebase.removeEventListener(listener);
    (new Timer(){
      @Override
      public void run() {
        if (failed[0] == false) {
          finishTest();
        }
      }}).schedule(4000);
    child.setValue("va");
  }

  public void testRemoveValueListener() {
    delayTestFinish(5000);
    Firebase firebase = makeFirebase();
    Firebase child = firebase.child("child");
    final boolean[] failed = {false};
     ValueEventListener listener = firebase.addValueEventListener(new TestValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        failed[0] = true;
        fail("Unexpected call to onChildAdded");
      }
    });
    firebase.removeEventListener(listener);
    (new Timer(){
      @Override
      public void run() {
        if (failed[0] == false) {
          finishTest();
        }
      }}).schedule(4000);
    child.setValue("va");
  }

  public void testSingleValueEventListener() {
    delayTestFinish(5000);
    final Firebase child = makeFirebase();
    final int[] numChanges = {0};
    child.addListenerForSingleValueEvent(new TestValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        numChanges[0]++;
        if (numChanges[0] > 1) {
          fail("Too many calls to onDataChange");
        }
        child.setValue(randomName());
      }
    });
    child.setValue(randomName());
    (new Timer(){
      @Override
      public void run() {
        if (numChanges[0] == 1) {
          finishTest();
        }
      }}).schedule(4000);
  }
//
//  public void testTransaction() {
//    delayTestFinish(5000);
//    final Firebase child = firebase.child(randomName());
//    child.setValue(123L);
//    new Timer(){
//      @Override
//      public void run() {
//        child.runTransaction(new Transaction.Handler(){
//          @Override
//          public Result doTransaction(MutableData currentData) {
//            assertEquals(123L, currentData.getValue());
//            currentData.setValue(456L);
//            return Transaction.success(currentData);
//          }
//          @Override
//          public void onComplete(FirebaseError error, boolean committed, DataSnapshot currentData) {
//            assertNull(error);
//            assertTrue(committed);
//            assertEquals(456L, currentData.getValue());
//            finishTest();
//          }});
//      }}.schedule(2500);
//  }

  public void testStartAt() {
    Firebase child = makeFirebase();
    Query limited = child.startAt(15.0);
    runQueryLimitingTest(2, limited);
  }

  public void testEndAt() {
    Firebase child = makeFirebase();
    Query limited = child.endAt(15.0);
    runQueryLimitingTest(3, limited);
  }

  private Firebase makeFirebase() {
    return new Firebase("http://www.example.com/" + randomName());
  }

  public void testStartEndAt() {
    Firebase child = makeFirebase();
    Query limited = child.startAt(15.0).endAt(15.0);
    runQueryLimitingTest(1, limited);
  }

  public void testStartAtLimit() {
    Firebase firebase = makeFirebase();
    Query limited = firebase.startAt(10.0).limit(1);
    runQueryLimitingTest(1, limited);
  }

  public void testStartAtLimit2() {
    Firebase firebase = makeFirebase();
    Query limited = firebase.startAt(10.0).limit(2);
    runQueryLimitingTest(2, limited);
  }

  public void testStartAtLimit3() {
    Firebase firebase = makeFirebase();
    Query limited = firebase.startAt().limit(2);
    runQueryLimitingTest(2, limited);
  }


  public void testEndAtLimit() {
    Firebase firebase = makeFirebase();
    Query limited = firebase.endAt(15.0).limit(1);
    runQueryLimitingTest(1, limited, true);
  }

  public void testEndAtLimit2() {
    Firebase firebase = makeFirebase();
    Query limited = firebase.endAt(15.0).limit(2);
    runQueryLimitingTest(2, limited, true);
  }

  public void testEndAtLimit3() {
    Firebase firebase = makeFirebase();
    Query limited = firebase.endAt().limit(1);
    runQueryLimitingTest(1, limited, true);
  }

  private void runQueryLimitingTest(int expected, Query limited) {
    runQueryLimitingTest(expected, limited, false);
  }

  private void runQueryLimitingTest(final int expected, Query limited, boolean reverse) {
    delayTestFinish(5000);
    Firebase base = limited.getRef();
    final int[] addedCount = {0};
    limited.addChildEventListener(new TestChildEventListener(){
      @Override
      public void onChildAdded(DataSnapshot snapshot, String previousChild) {
        addedCount[0]++;
      }
    });
    if (reverse) {
      base.child("four").setValue("four", 20.0);
      base.child("three").setValue("three", 15.0);
      base.child("two").setValue("two", 10.0);
      base.child("one").setValue("one", 5.0);
    } else {
      base.child("one").setValue("one", 5.0);
      base.child("two").setValue("two", 10.0);
      base.child("three").setValue("three", 15.0);
      base.child("four").setValue("four", 20.0);
    }
    new Timer(){
      @Override
      public void run() {
        assertEquals(expected, addedCount[0]);
        finishTest();
      }}.schedule(4000);
  }

  private String randomName() {
    return Math.abs(Random.nextInt()) + "";
  }

  private void runTestSet(Object object) {
    runTestSetOnce(object, null);
    if (object != null) {
      runTestSetOnce(object, "string");
      runTestSetOnce(object, 123.2);
    } else {
      try {
        runTestSetOnce(object, "123.0");
        fail("expected setting a priority on an null value to cause an exception");
      } catch (IllegalArgumentException expected) {
        finishTest();
      }
    }
  }

  private void runTestSetOnce(final Object object, final Object priority) {
    delayTestFinish(5000);
    final Firebase child = makeFirebase();
    child.addListenerForSingleValueEvent(new TestValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        assertDeepEquals("values do not match", object, snapshot.getValue());
        assertEquals(child.getName(), snapshot.getName());
        assertEquals("priorities do not match", priority, snapshot.getPriority());
        assertEquals(child.getName(), snapshot.getRef().getName());
        finishTest();
      }
    });
    if (priority == null) {
      child.setValue(object);
    } else {
      child.setValue(object, priority);
    }
  }
}
