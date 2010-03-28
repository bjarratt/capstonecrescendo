//
//  ComposeViewController.h
//  Crescendo
//
//  Created by Brandon on 3/5/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ComposeViewController : UIViewController <UIScrollViewDelegate> {
	UIScrollView *myLengthScrollView;
	UIScrollView *myPitchScrollView;
	IBOutlet UILabel *buildLabel;
	IBOutlet UIButton *backButton;
	
	NSArray *lengthImages;
	NSArray *pitchImages;
	
	NSString *noteLength;
	NSString *notePitch;
	int previousNoteLengthPage;
	int previousNotePitchPage;
}

@property (nonatomic, retain) UIScrollView *myLengthScrollView;
@property (nonatomic, retain) UIScrollView *myPitchScrollView;
@property (nonatomic, retain) IBOutlet UILabel *buildLabel;
@property (nonatomic, retain) IBOutlet UIButton *backButton;
@property (nonatomic, retain) NSArray *lengthImages;
@property (nonatomic, retain) NSArray *pitchImages;
@property (nonatomic, retain) NSString *noteLength;
@property (nonatomic, retain) NSString *notePitch;
@property (readwrite, assign) int previousNoteLengthPage;
@property (readwrite, assign) int previousNotePitchPage;

- (void) scrollViewDidEndDragging: (UIScrollView *) scrollView willDecelerate: (BOOL) decelerate;
- (void) scrollViewDidEndDecelerating: (UIScrollView *) scrollView;

- (void) determineScrollViewPage: (UIScrollView *) scrollView;

- (IBAction) goBack;
- (void) updateBuildLabel;
- (void) drawPortraitView;
- (void) drawPortraitLandscapeSideView;
- (void) drawWholenotePitches;
- (void) drawHalfnotePitches;
- (void) drawQuarternotePitches;
- (void) drawEighthnotePitches;

@end
