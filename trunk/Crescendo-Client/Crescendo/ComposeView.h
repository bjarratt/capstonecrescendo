#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>

@interface ComposeView: UIView <UIScrollViewDelegate> {
    IBOutlet UIView *myHolderView;
    IBOutlet UIScrollView *myPitchScrollView;
	IBOutlet UIScrollView *myLengthScrollView;
	int pitchPage;
	int lengthPage;
}
- (void) scrollViewDidEndDragging: (UIScrollView *) scrollView willDecelerate: (BOOL) decelerate;
- (void) scrollViewDidEndDecelerating: (UIScrollView *) scrollView;
- (void) determineScrollViewPage: (UIScrollView *) scrollView;

- (IBAction) gotoComposeView;
- (IBAction) goBack;
@end
