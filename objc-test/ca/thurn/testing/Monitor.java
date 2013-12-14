package ca.thurn.testing;

public class Monitor {
  final int expectedSignals;
  int signalCount;
  
  Monitor(int expectedSignals) {
    this.expectedSignals = expectedSignals;
    this.signalCount = 0;
  }
  
  /**
   * Waits 10 seconds for calls to the signal() method.
   * @return True if a timeout occured.
   */
  native boolean waitForSignals() /*-[
    NSDate *start = [NSDate date];
    while (self->signalCount_ < self->expectedSignals_) {
      [[NSRunLoop currentRunLoop] runUntilDate:[NSDate dateWithTimeIntervalSinceNow: 0.1]];
      if ([[NSDate date] timeIntervalSinceDate: start] > 10) {
        [self reset];
        return YES;
      }
    };
    [self reset];
    return NO;
  ]-*/;

  void signal() {
    signalCount++;
  }
  
  void reset() {
    signalCount = 0;
  }
}
