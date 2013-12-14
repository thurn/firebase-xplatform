package ca.thurn.testing;

import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.runner.JUnitCore;

import junit.framework.TestCase;

/**
 * A subclass for sharing asynchronous GWTTestCase tests between client-side and server-side code.
 * 
 * Usage: At the end of your asynchronous test's main body, invoke awaitFinished(). On the server,
 * this blocks the test thread. On the client, this sets up a listener. When your asynchronous
 * callback completes, invoke finished() to unblock the thread (on the server) or mark the test
 * finished (on the client).
 */
public abstract class SharedTestCase extends TestCase {

  AtomicBoolean didSetUpTestCase = new AtomicBoolean(false);
  Monitor testMonitor;
  
  public static enum TestMode {
    JAVA,
    JAVASCRIPT,
    OBJECTIVE_C
  }
  
  static class OneTimeRunnable implements Runnable {
    private final AtomicBoolean ran = new AtomicBoolean(false);
    private final Runnable runnable;
    
    OneTimeRunnable(Runnable runnable) {
      this.runnable = runnable;
    }
    
    @Override
    public void run() {
      if (ran.getAndSet(true) == false) {
        runnable.run();
      }
    }
  }
  
  public static class BooleanReference {
    private AtomicBoolean value;
    
    public BooleanReference(boolean value) {
      this.value = new AtomicBoolean(value);
    }
    
    public void set(boolean newValue) {
      value.set(newValue);
    }
    
    public boolean get() {
      return value.get();
    }
    
    public boolean getAndSet(boolean newValue) {
      return value.getAndSet(newValue);
    }
  }
  
  public static class IntegerReference {
    private AtomicInteger value;
    
    public IntegerReference(int value) {
      this.value = new AtomicInteger(value);
    }
    
    public void set(int newValue) {
      value.set(newValue);
    }
    
    public int get() {
      return value.get();
    }
    
    public int getAndSet(int newValue) {
      return value.getAndSet(newValue);
    }
    
    public int getAndIncrement() {
      return value.getAndIncrement();
    }
  }
  
  public static void runMain(String name) {
    JUnitCore.main(name);
  }
  
  public abstract String getJavascriptModuleName();
  
  @Override
  public void setUp() throws Exception {
    super.setUp();
    gwtSetUp();
  }
  
  @Override
  public void tearDown() throws Exception {
    super.tearDown();
    gwtTearDown();
  }
  
  public final void gwtSetUp() {
    beginAsyncTestBlock();
    final Runnable runFinished = new OneTimeRunnable(new Runnable() {
      @Override
      public void run() {
        finished();
      }});
    sharedSetUpTestCase(runFinished);    
    if (didSetUpTestCase.getAndSet(true) == false) {
      Runnable runSetUp = new OneTimeRunnable(new Runnable() {
        @Override
        public void run() {
          sharedSetUp(runFinished);
        }
      });
      sharedSetUpTestCase(runSetUp);
    } else {
      sharedSetUp(runFinished);
    }
    endAsyncTestBlock();
  }
  
  public final void gwtTearDown() {
    sharedTearDown();
  }
  
  public void sharedSetUpTestCase(Runnable done) {
    done.run();
  }
  
  public void sharedSetUp(Runnable done) {
    done.run();
  }
  
  public void sharedTearDown() {
  }
  
  public void injectScript(String url, final Runnable onComplete) {
    if (onComplete != null) {
      onComplete.run();
    }
  }
  
  public void beginAsyncTestBlock() {
    beginAsyncTestBlock(1);
  }

  public void beginAsyncTestBlock(int numFinishesExpected) {
    testMonitor = new Monitor(numFinishesExpected);
  }
  
  public void endAsyncTestBlock() {
    boolean timedOut = testMonitor.waitForSignals();
    if (timedOut) {
      fail("Test Timed Out!");
    }
  }

  public TestMode getTestMode() {
    return TestMode.OBJECTIVE_C;
  }

  /**
   * Indicates that your test, where you previously called awaitFinished(), is done executing.
   */
  public void finished() {
    testMonitor.signal();
  }

  public void schedule(int delayMillis, final Runnable runnable) {
    new java.util.Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        runnable.run();
      }
    }, delayMillis);    
  }

  public int randomInteger() {
    return new java.util.Random().nextInt();
  }

  public void assertDeepEquals(Object o1, Object o2) {
    assertDeepEquals("(no message)", o1, o2);
  }
  
  public void assertDeepEquals(String msg, Object o1, Object o2) {
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
  
}
