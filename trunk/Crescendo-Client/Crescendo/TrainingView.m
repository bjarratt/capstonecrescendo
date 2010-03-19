#import "TrainingView.h"

@implementation TrainingView

#pragma mark UIScrollViewDelegate methods

- (void) scrollViewDidEndDragging: (UIScrollView *) scrollView willDecelerate: (BOOL) decelerate {
	if (decelerate == YES) // Wait until it is done decelerating (calls scrollViewDidEndDecelerating)
		return;
	
	[self determineScrollViewPage:scrollView];
}

- (void) scrollViewDidEndDecelerating: (UIScrollView *) scrollView {
	[self determineScrollViewPage:scrollView];
}

#pragma mark Determine Scroll Page

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

#pragma mark Initialize ScrollView 

- (IBAction) gotoScrollView {
    myLengthScrollView.contentSize = CGSizeMake(885, 200);
	myPitchScrollView.contentSize = CGSizeMake(2395, 200);
	[myLengthScrollView setDelegate:self];
	[myPitchScrollView setDelegate:self];
	[self addSubview:myHolderView];
}

- (void) dealloc {
	[myHolderView release];
	[myPitchScrollView release];
	[myLengthScrollView release];
    [super dealloc];
}

@end
