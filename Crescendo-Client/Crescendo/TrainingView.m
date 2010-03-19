#import "TrainingView.h"

@implementation TrainingView

- (void) scrollViewDidEndDragging: (UIScrollView *) scrollView willDecelerate: (BOOL) decelerate {
	if (decelerate == YES)
		return;
	
	[self determineScrollViewPage:scrollView];
}

- (void) scrollViewDidEndDecelerating: (UIScrollView *) scrollView {
	[self determineScrollViewPage:scrollView];
}

- (void) determineScrollViewPage: (UIScrollView *) scrollView {
	float page = scrollView.contentOffset.x / scrollView.contentSize.width;
	
	if (scrollView == myLengthScrollView)
	{
		if (page >= 0.47)
		{
			lengthPage = 4;
		}
		else if(page >= 0.285)
		{
			lengthPage = 3;
		}
		else if (page >= 0.1)
		{
			lengthPage = 2;
		}
		else if (page >= 0)
		{
			lengthPage = 1;
		}
	}
}

- (IBAction) gotoScrollView {
    myLengthScrollView.contentSize = CGSizeMake(885, 200);
	myPitchScrollView.contentSize = CGSizeMake(2395, 200);
	[myLengthScrollView setDelegate:self];
	[myPitchScrollView setDelegate:self];
	[self addSubview:myHolderView];
}

@end
