//  Copyright (c) 2013 Travis Jeffery (tj@travisjeffery, @travisjeffery)

//  Permission is hereby granted, free of charge, to any person obtaining
//  a copy of this software and associated documentation files (the
//  "Software"), to deal in the Software without restriction, including
//  without limitation the rights to use, copy, modify, merge, publish,
//  distribute, sublicense, and/or sell copies of the Software, and to
//  permit persons to whom the Software is furnished to do so, subject to
//  the following conditions:

//  The above copyright notice and this permission notice shall be
//  included in all copies or substantial portions of the Software.

//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
//  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
//  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
//  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
//  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
//  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
//  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


#import "TRVSMonitor.h"

@implementation TRVSMonitor {
    long long int _signalsRemaining;
    long long int _expectedSignalCount;
}

+ (instancetype)monitor {
    return [[self alloc] init];
}

- (instancetype)init {
    return [self initWithExpectedSignalCount:1];
}

- (instancetype)initWithExpectedSignalCount:(NSInteger)expectedSignalCount {
    self = [super init];
    if (self) {
        _expectedSignalCount = expectedSignalCount;
        [self reset];
    }
    return self;
}

- (void)wait {
    [self waitWithTimeout:0];
}

- (void)waitWithSignalHandler:(TRVSMonitorHandler)handler {
    [self waitWithTimeout:0 signalHandler:handler];
}

- (BOOL)waitWithTimeout:(NSUInteger)timeout {
    return [self waitWithTimeout:timeout signalHandler:nil];
}

- (BOOL)waitWithTimeout:(NSTimeInterval)timeout signalHandler:(TRVSMonitorHandler)handler {
    NSDate *start = [NSDate date];

    while (_signalsRemaining > 0) {
        [[NSRunLoop currentRunLoop] runUntilDate:[NSDate dateWithTimeIntervalSinceNow:.1]];
        if ([self didTimeOut:timeout fromStartDate:start]) {
            [self reset];
            return NO;
        }
        if (handler) handler(self);
    };
    [self reset];
    return YES;
}

- (void)signal {
    --_signalsRemaining;
}

- (void)reset {
    _signalsRemaining = _expectedSignalCount;
}

#pragma mark - Private

- (BOOL)didTimeOut:(NSTimeInterval)timeout fromStartDate:(NSDate *)startDate {
    return (timeout > 0 && [[NSDate date] timeIntervalSinceDate:startDate] > timeout);
}

@end
