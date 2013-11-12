//
//  main.m
//  FirebaseJUnit
//
//  Created by Derek Thurn on 11/6/13.
//
//

#import <Foundation/Foundation.h>
#import "FirebaseTest.h"
#include "org/junit/runner/JUnitCore.h"

int main(int argc, const char * argv[])
{
  @autoreleasepool {
    [FCFirebaseTest runJUnit];
  }
  return 0;
}
