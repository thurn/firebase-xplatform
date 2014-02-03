#import "ViewController.h"
#import "Firebase.h"
#import "NumberIncrementer.h"
#import "java/lang/Long.h"

@interface ViewController () <NINumberIncrementer_Callback>
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
    [self.numberIncrementer incrementWithNINumberIncrementer_Callback:self];
}

- (void)onIncrementWithLong:(long long)value {
    NSString *message = [NSString stringWithFormat: @"%lld increments!", value];
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil
                                                    message: message
                                                   delegate:nil
                                          cancelButtonTitle:@"OK"
                                          otherButtonTitles:nil];
    [alert show];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end