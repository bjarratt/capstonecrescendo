#import "TrainingView.h"

@implementation TrainingView
- (IBAction)gotoScrollView {
    myLengthScrollView.contentSize = CGSizeMake(740, 200);
	myPitchScrollView.contentSize = CGSizeMake(2250, 200);
	[self addSubview:myHolderView];
}
@end
