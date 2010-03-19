#import "TrainingView.h"

@implementation TrainingView
- (IBAction)gotoScrollView {
    myLengthScrollView.contentSize = CGSizeMake(885, 200);
	myPitchScrollView.contentSize = CGSizeMake(2395, 200);
	[self addSubview:myHolderView];
}
@end
