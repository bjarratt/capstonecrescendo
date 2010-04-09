//
//  ComposeViewController.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "PlayNoteUpdateDelegate.h"
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
	
	UISlider *keySlider;
	UISlider *timeSlider;
	UISlider *tempoSlider;
	UISlider *barsSlider;
	
	UITextField *keyText;
	UITextField *timeText;
	UITextField *tempoText;
	UITextField *barsText;
	
	UILabel *keyLabel;
	UILabel *timeLabel;
	UILabel *tempoLabel;
	UILabel *barsLabel;
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
@property (readwrite, assign) UISlider *keySlider;
@property (readwrite, assign) UISlider *timeSlider;
@property (readwrite, assign) UISlider *tempoSlider;
@property (readwrite, assign) UISlider *barsSlider;
@property (readwrite, assign) UITextField *keyText;
@property (readwrite, assign) UITextField *timeText;
@property (readwrite, assign) UITextField *tempoText;
@property (readwrite, assign) UITextField *barsText;
@property (readwrite, assign) UILabel *keyLabel;
@property (readwrite, assign) UILabel *timeLabel;
@property (readwrite, assign) UILabel *tempoLabel;
@property (readwrite, assign) UILabel *barsLabel;

- (void) scrollViewDidEndDragging: (UIScrollView *) scrollView willDecelerate: (BOOL) decelerate;
- (void) scrollViewDidEndDecelerating: (UIScrollView *) scrollView;

- (void) determineScrollViewPage: (UIScrollView *) scrollView;

- (IBAction) goBack;
- (void) updateBuildLabel;
- (void) sendNoteToServer: (id) sender;
- (void) drawPortraitView;
- (void) drawPortraitLandscapeSideView;
- (void) drawNoteLengths;
- (void) drawWholenotePitches;
- (void) drawHalfnotePitches;
- (void) drawQuarternotePitches;
- (void) drawEighthnotePitches;

@end
