#import <Foundation/Foundation.h>
#import "Procedures.h"
#import "Functions.h"

typedef void(^ProcBlock0)();
typedef void(^ProcBlock1)(id);
typedef void(^ProcBlock2)(id, id);
typedef void(^ProcBlock3)(id, id, id);
typedef void(^ProcBlock4)(id, id, id, id);
typedef void(^ProcBlock5)(id, id, id, id, id);
typedef void(^ProcBlock6)(id, id, id, id, id, id);
typedef id(^FuncBlock0)();
typedef id(^FuncBlock1)(id);
typedef id(^FuncBlock2)(id, id);
typedef id(^FuncBlock3)(id, id, id);
typedef id(^FuncBlock4)(id, id, id, id);
typedef id(^FuncBlock5)(id, id, id, id, id);
typedef id(^FuncBlock6)(id, id, id, id, id, id);

@interface Procedure0 : NSObject <XXLProcedures_Procedure0>
@property(strong,nonatomic) ProcBlock0 block;
- (void)apply;
@end

@interface Procedure1 : NSObject <XXLProcedures_Procedure1>
@property(strong,nonatomic) ProcBlock1 block;
- (void)applyWithId:(id)p;
@end

@interface Procedure2 : NSObject <XXLProcedures_Procedure2>
@property(strong,nonatomic) ProcBlock2 block;
- (void)applyWithId:(id)p1 withId:(id)p2;
@end

@interface Procedure3 : NSObject <XXLProcedures_Procedure3>
@property(strong,nonatomic) ProcBlock3 block;
- (void)applyWithId:(id)p1 withId:(id)p2 withId:(id)p3;
@end

@interface Procedure4 : NSObject <XXLProcedures_Procedure4>
@property(strong,nonatomic) ProcBlock4 block;
- (void)applyWithId:(id)p1 withId:(id)p2 withId:(id)p3 withId:(id)p4;
@end

@interface Procedure5 : NSObject <XXLProcedures_Procedure5>
@property(strong,nonatomic) ProcBlock5 block;
- (void)applyWithId:(id)p1
             withId:(id)p2
             withId:(id)p3
             withId:(id)p4
             withId:(id)p5;
@end

@interface Procedure6 : NSObject <XXLProcedures_Procedure6>
@property(strong,nonatomic) ProcBlock6 block;
- (void)applyWithId:(id)p1
             withId:(id)p2
             withId:(id)p3
             withId:(id)p4
             withId:(id)p5
             withId:(id)p6;
@end

@interface Function0 : NSObject <XXLFunctions_Function0>
@property(strong,nonatomic) FuncBlock0 block;
- (id)apply;
@end

@interface Function1 : NSObject <XXLFunctions_Function1>
@property(strong,nonatomic) FuncBlock1 block;
- (id)applyWithId:(id)p;
@end

@interface Function2 : NSObject <XXLFunctions_Function2>
@property(strong,nonatomic) FuncBlock2 block;
- (id)applyWithId:(id)p1 withId:(id)p2;
@end

@interface Function3 : NSObject <XXLFunctions_Function3>
@property(strong,nonatomic) FuncBlock3 block;
- (id)applyWithId:(id)p1 withId:(id)p2 withId:(id)p3;
@end

@interface Function4 : NSObject <XXLFunctions_Function4>
@property(strong,nonatomic) FuncBlock4 block;
- (id)applyWithId:(id)p1 withId:(id)p2 withId:(id)p3 withId:(id)p4;
@end

@interface Function5 : NSObject <XXLFunctions_Function5>
@property(strong,nonatomic) FuncBlock5 block;
- (id)applyWithId:(id)p1
           withId:(id)p2
           withId:(id)p3
           withId:(id)p4
           withId:(id)p5;
@end

@interface Function6 : NSObject <XXLFunctions_Function6>
@property(strong,nonatomic) FuncBlock6 block;
- (id)applyWithId:(id)p1
           withId:(id)p2
           withId:(id)p3
           withId:(id)p4
           withId:(id)p5
           withId:(id)p6;
@end
