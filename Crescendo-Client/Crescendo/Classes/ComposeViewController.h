//
//  ComposeViewController.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "ServerUpdateDelegate.h"
#import "XMLClient.h"

@interface ComposeViewController : UIViewController <UIScrollViewDelegate> {
	XMLClient *client;
	NSString *playerId;
	UIScrollView *myLengthScrollView;
	UIScrollView *myPitchScrollView;
	UIButton *buildButton;
	IBOutlet UILabel *buildLabel;
	IBOutlet UIButton *backButton;
	
	NSArray *lengthImages;
	NSArray *pitchImages;
	
	NSString *noteLength;
	NSString *notePitch;
	int previousNoteLengthPage;
	int previousNotePitchPage;
}

@property (nonatomic, retain) XMLClient *client;
@property (nonatomic, retain) NSString *playerId;
@property (nonatomic, retain) UIScrollView *myLengthScrollView;
@property (nonatomic, retain) UIScrollView *myPitchScrollView;
@property (nonatomic, retain) UIButton *buildButton;
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

- (void) sendNoteToServer: (id) sender;

- (IBAction) goBack;
- (void) updateBuildLabel;
- (void) drawPortraitView;
- (void) drawPortraitLandscapeSideView;
- (void) drawNoteLengths;
- (void) drawWholenotePitches;
- (void) drawHalfnotePitches;
- (void) drawQuarternotePitches;
- (void) drawEighthnotePitches;

@end
