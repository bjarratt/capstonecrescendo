#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>
#import "MyScrollView.h"

@interface TrainingView : UIView {
    IBOutlet UIView *myHolderView;
    IBOutlet MyScrollView *myPitchScrollView;
	IBOutlet MyScrollView *myLengthScrollView;
}
- (IBAction)gotoScrollView;
@end
