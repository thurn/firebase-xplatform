#import "ViewController.h"
#import "Firebase.h"
#import "NumberIncrementer.h"
#import "CallbackWrappers.h"
#import "java/lang/Long.h"

@interface ViewController ()
@property(strong,nonatomic) NINumberIncrementer* numberIncrementer;
@end

@implementation ViewController

- (void)viewDidLoad
{
  [super viewDidLoad];
  FCFirebase *firebase = [[FCFirebase alloc]
                          initWithNSString: @"https://incrementer.firebaseio-demo.com/"];
  self.numberIncrementer = [[NINumberIncrementer alloc] initWithFCFirebase: firebase];
}

- (IBAction)onIncrement:(id)sender {
  Procedure1 *procedure = [Procedure1 new];
  procedure.block = ^(JavaLangLong* javaLong) {
    NSString *message = [NSString stringWithFormat: @"%ld increments!", [javaLong longValue]];
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil
                                                    message: message
                                                   delegate:nil
                                          cancelButtonTitle:@"OK"
                                          otherButtonTitles:nil];
    [alert show];
  };
  [self.numberIncrementer incrementWithXXLProcedures_Procedure1: procedure];
}

- (void)didReceiveMemoryWarning
{
  [super didReceiveMemoryWarning];
  // Dispose of any resources that can be recreated.
}

@end