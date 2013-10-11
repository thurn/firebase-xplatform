package com.firebase.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import ca.thurn.gwt.SharedGWTTestCase;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class FirebaseTest extends SharedGWTTestCase {

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

  @Override
  public void gwtSetUp() {
    injectScript("https://cdn.firebase.com/v0/firebase.js");
  }

  @Override
  public String getModuleName() {
    if (isServer()) {
      return null;
    } else {
      return "com.firebase.Firebase";
    }
  }

  public void testChild() {
    String name = randomName();
    Firebase firebase = new Firebase("http://www.example.com/foo");
    Firebase child = firebase.child(name);
    assertEquals(name, child.getName());
    assertEquals("foo", child.getParent().getName());
  }

  public void testGetClassLiteral() {
	beginAsyncTestBlock();
    Firebase child = makeFirebase();
    child.addListenerForSingleValueEvent(new TestValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        Long value = snapshot.getValue(Long.class);
        assertEquals(new Long(123), value);
        finished();
      }
    });
    child.setValue(123L);
    endAsyncTestBlock();
  }

  public void testGetGenericTypeIndicator() {
    beginAsyncTestBlock();
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
        finished();
      }
    });
    child.setValue(list);
    endAsyncTestBlock();
  }

  public void testGetName() {
    Firebase firebase = new Firebase("http://www.example.com/foo");
    assertEquals("foo", firebase.getName());
  }

  public void testNullRootName() {
    Firebase firebase = new Firebase("http://www.example.com/foo");
    assertEquals(null, firebase.getParent().getName());
    assertEquals(null, firebase.getRoot().getName());
  }

  public void testSetBoolean() {
    runTestSet(true);
  }

  public void testSetByte() {
    runTestSet((byte) 12, new Long(12));
  }

  public void testSetDouble() {
    runTestSet(41.0000000000001);
  }

  public void testSetEmptyList() {
    beginAsyncTestBlock();
    List<Double> doubles = new ArrayList<Double>();
    Firebase child = makeFirebase();
    child.addListenerForSingleValueEvent(new TestValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        assertEquals(null, snapshot.getValue());
        finished();
      }
    });
    child.setValue(doubles);
    endAsyncTestBlock();
  }

  public void testSetEmptyMap() {
    beginAsyncTestBlock();
    Map<String, String> map = new HashMap<String, String>();
    Firebase child = makeFirebase();
    child.addListenerForSingleValueEvent(new TestValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        assertEquals(null, snapshot.getValue());
        finished();
      }
    });
    child.setValue(map);
    endAsyncTestBlock();
  }

  public void testSetLong() {
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

  public void testSetInt() {
    runTestSet(123, new Long(123));
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
    beginAsyncTestBlock();
    final Firebase child = makeFirebase();
    child.addListenerForSingleValueEvent(new TestValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        assertEquals(null, snapshot.getValue());
        finished();
      }
    });
    child.setValue(null);
    endAsyncTestBlock();
  }

  public void testSetNullsListException() {
    if (isServer()) {
      return; // Regular firebase doesn't throw here
    }
    List<Object> list = new ArrayList<Object>();
    list.add("one");
    list.add(2.340);
    list.add(null);
    try {
      runTestSet(list);
      fail("Expected null value in a list to cause an exception");
    } catch (IllegalArgumentException expected) {
      finished();
    }
  }

  public void testSetString() {
    runTestSet("str");
  }

  public void testUpdateReadOnce() {
    beginAsyncTestBlock();
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
        finished();
      }
    });
    Map<String, Object> update = new HashMap<String, Object>();
    update.put("alpha", "fred");
    update.put("beta", 2L);
    child.updateChildren(update);
    endAsyncTestBlock();
  }

  public void testAddChild() {
    beginAsyncTestBlock();
    Firebase firebase = makeFirebase();
    Firebase child = firebase.child("foo");
    firebase.addChildEventListener(new TestChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot snapshot, String previousName) {
        assertEquals("va", snapshot.getValue());
        assertNull(previousName);
        finished();
      }
    });
    child.setValue("va");
    endAsyncTestBlock();
  }

  public void testAddRemoveChild() {
    beginAsyncTestBlock();
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
        finished();
      }
    });
    child.setValue(1234L);
    endAsyncTestBlock();
  }

  public void testChangeChild() {
    beginAsyncTestBlock();
    final Firebase fb = makeFirebase();
    final Firebase child = fb.child("new");
    fb.addChildEventListener(new TestChildEventListener() {
      @Override
      public void onChildChanged(DataSnapshot snapshot, String prevChild) {
        assertEquals(4321L, snapshot.getValue());
        finished();
      }
    });
    child.setValue(1234L);
    child.setValue(4321L);
    endAsyncTestBlock();
  }

  public void testMoveChild() {
    beginAsyncTestBlock();
    final Firebase fb = makeFirebase();
    final Firebase child = fb.child("new");
    final Firebase one = child.child("one");
    child.addChildEventListener(new TestChildEventListener() {
      @Override
      public void onChildMoved(DataSnapshot snapshot, String prevChild) {
        assertEquals(111L, snapshot.getValue());
        assertEquals("two", prevChild);
        finished();
      }
    });
    child.child("zero").setValue(9L, 100L);
    one.setValue(111L, 111L);
    child.child("two").setValue(222L, 222L);
    child.child("three").setValue(444L, 444L);
    schedule(1000, new Runnable() {
      @Override
      public void run() {
        one.setPriority(333L);
      }
    });
    endAsyncTestBlock();
  }

  public void testRemoveChildListener() {
    beginAsyncTestBlock();
    Firebase firebase = makeFirebase();
    Firebase child = firebase.child("child");
    final AtomicBoolean failed = new AtomicBoolean(false);
    ChildEventListener listener = firebase.addChildEventListener(new TestChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot snapshot, String previousName) {
        failed.set(true);
        fail("Unexpected call to onChildAdded");
      }
    });
    firebase.removeEventListener(listener);
    schedule(4000, new Runnable() {
	  @Override
	  public void run() {
	    if (!failed.get()) {
	      finished();
		}
      }
    });
    child.setValue("va");
    endAsyncTestBlock();
  }

  public void testRemoveValueListener() {
    beginAsyncTestBlock();
    Firebase firebase = makeFirebase();
    Firebase child = firebase.child("child");
    final AtomicBoolean failed = new AtomicBoolean(false);
     ValueEventListener listener = firebase.addValueEventListener(new TestValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        failed.set(true);
        fail("Unexpected call to onChildAdded");
      }
    });
    firebase.removeEventListener(listener);
    schedule(4000, new Runnable() {
	  @Override
	  public void run() {
        if (!failed.get()) {
          finished();
        }
      }
    });
    child.setValue("va");
    endAsyncTestBlock();
  }

  public void testSingleValueEventListener() {
    beginAsyncTestBlock();
    final Firebase child = makeFirebase();
    final AtomicInteger numChanges = new AtomicInteger(0);
    child.addListenerForSingleValueEvent(new TestValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        numChanges.getAndIncrement();
        if (numChanges.get() > 1) {
          fail("Too many calls to onDataChange");
        }
        child.setValue(randomName());
      }
    });
    child.setValue(randomName());
    schedule(4000, new Runnable() {
	  @Override
	  public void run() {
	    if (numChanges.get() == 1) {
	      finished();
	    }
      }
    });
    endAsyncTestBlock();
  }

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
    beginAsyncTestBlock();
    Firebase base = limited.getRef();
    final AtomicInteger addedCount = new AtomicInteger(0);
    limited.addChildEventListener(new TestChildEventListener(){
      @Override
      public void onChildAdded(DataSnapshot snapshot, String previousChild) {
        addedCount.getAndIncrement();
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
    schedule(4000, new Runnable() {
	  @Override
	  public void run() {
	    assertEquals(expected, addedCount.get());
	    finished();
      }
    });
    endAsyncTestBlock();
  }

  private String randomName() {
    return Math.abs(randomInteger()) + "";
  }

  private void runTestSet(Object object) {
	  runTestSet(object, object);
  }
  
  private void runTestSet(Object object, Object expected) {
    runTestSetOnce(object, null, expected);
    runTestSetOnce(object, "string", expected);
    runTestSetOnce(object, 123.2, expected);
  }

  private void runTestSetOnce(final Object object, final Object priority, final Object expected) {
    beginAsyncTestBlock();
    final Firebase child = makeFirebase();
    child.addListenerForSingleValueEvent(new TestValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        assertDeepEquals("values do not match", expected, snapshot.getValue());
        assertEquals(child.getName(), snapshot.getName());
        assertEquals("priorities do not match", priority, snapshot.getPriority());
        assertEquals(child.getName(), snapshot.getRef().getName());
        finished();
      }
    });
    if (priority == null) {
      child.setValue(object);
    } else {
      child.setValue(object, priority);
    }
    endAsyncTestBlock();
  }
}
