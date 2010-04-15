//
//  BuildViewController.h
//  Crescendo
//
//  Created by Senior Capstone on 4/8/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "PlayNoteUpdateDelegate.h"
#import "XMLClient.h"

@interface BuildView : UIViewController <UIScrollViewDelegate> {
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

- (IBAction) goBack;
- (void) updateBuildLabel;
- (void) sendNoteToServer: (id) sender;
- (void) drawPortraitLandscapeSideView;
- (void) drawNoteLengths;
- (void) drawWholenotePitches;
- (void) drawHalfnotePitches;
- (void) drawQuarternotePitches;

@end