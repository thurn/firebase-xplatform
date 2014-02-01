#import "CallbackWrappers.h"

@implementation Procedure0
- (void)apply {
  self.block();
}
@end

@implementation Procedure1
- (void)applyWithId:(id)p {
  self.block(p);
}
@end

@implementation Procedure2
- (void)applyWithId:(id)p1 withId:(id)p2 {
  self.block(p1, p2);
}
@end

@implementation Procedure3
- (void)applyWithId:(id)p1 withId:(id)p2 withId:(id)p3 {
  self.block(p1, p2, p3);
}
@end

@implementation Procedure4
- (void)applyWithId:(id)p1 withId:(id)p2 withId:(id)p3 withId:(id)p4 {
  self.block(p1, p2, p3, p4);
}
@end

@implementation Procedure5
- (void)applyWithId:(id)p1
             withId:(id)p2
             withId:(id)p3
             withId:(id)p4
             withId:(id)p5 {
  self.block(p1, p2, p3, p4, p5);
}
@end

@implementation Procedure6
- (void)applyWithId:(id)p1
             withId:(id)p2
             withId:(id)p3
             withId:(id)p4
             withId:(id)p5
             withId:(id)p6 {
  self.block(p1, p2, p3, p4, p5, p6);
}
@end

@implementation Function0
- (id)apply {
  return self.block();
}
@end

@implementation Function1
- (id)applyWithId:(id)p {
  return self.block(p);
}
@end

@implementation Function2
- (id)applyWithId:(id)p1 withId:(id)p2 {
  return self.block(p1, p2);
}
@end

@implementation Function3
- (id)applyWithId:(id)p1 withId:(id)p2 withId:(id)p3 {
  return self.block(p1, p2, p3);
}
@end

@implementation Function4
- (id)applyWithId:(id)p1 withId:(id)p2 withId:(id)p3 withId:(id)p4 {
  return self.block(p1, p2, p3, p4);
}
@end

@implementation Function5
- (id)applyWithId:(id)p1
           withId:(id)p2
           withId:(id)p3
           withId:(id)p4
           withId:(id)p5 {
  return self.block(p1, p2, p3, p4, p5);
}
@end

@implementation Function6
- (id)applyWithId:(id)p1
           withId:(id)p2
           withId:(id)p3
           withId:(id)p4
           withId:(id)p5
           withId:(id)p6 {
  return self.block(p1, p2, p3, p4, p5, p6);
}
@end
