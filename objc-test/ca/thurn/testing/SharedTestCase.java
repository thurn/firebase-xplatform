package ca.thurn.testing;

import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;

import junit.framework.TestCase;

/*-[
#import "TRVSMonitor.h"
TRVSMonitor *global_trvs_monitor;
]-*/

/**
 * A subclass for sharing asynchronous GWTTestCase tests between client-side and server-side code.
 * 
 * Usage: At the end of your asynchronous test's main body, invoke awaitFinished(). On the server,
 * this blocks the test thread. On the client, this sets up a listener. When your asynchronous
 * callback completes, invoke finished() to unblock the thread (on the server) or mark the test
 * finished (on the client).
 */
public abstract class SharedTestCase extends TestCase {

  public static enum TestMode {
    JAVA,
    JAVASCRIPT,
    OBJECTIVE_C
  }
  
  public void gwtSetUp() {
  }
  
  public abstract String getModuleName();

  public void injectScript(String url) {
    injectScript(url, null);
  }
  
  public void injectScript(String url, final Runnable onComplete) {
    if (onComplete != null) {
      onComplete.run();
    }
  }
  
  public void beginAsyncTestBlock() {
    beginAsyncTestBlock(1);
  }

  public synchronized native void beginAsyncTestBlock(int numFinishesExpected) /*-[
    global_trvs_monitor = [[TRVSMonitor alloc] initWithExpectedSignalCount:1];
  ]-*/;

  public native void endAsyncTestBlock() /*-[
    BOOL signaled = [global_trvs_monitor waitWithTimeout: 10.0];
    if (!signaled) {
      [JunitFrameworkAssert failWithNSString: @"test timed out"];
    }
  ]-*/;

  public TestMode getTestMode() {
    return TestMode.OBJECTIVE_C;
  }

  /**
   * Indicates that your test, where you previously called awaitFinished(), is done executing.
   */
  public synchronized native void finished() /*-[
    [global_trvs_monitor signal];
  ]-*/;

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
