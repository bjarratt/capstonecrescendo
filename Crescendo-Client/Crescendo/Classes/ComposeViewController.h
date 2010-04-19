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
	
	BOOL inGame;
	
	UIScrollView *myLengthScrollView;
	UIScrollView *myPitchScrollView;
	UIButton *buildButton;
	IBOutlet UILabel *buildLabel;
	IBOutlet UIButton *backButton;
	IBOutlet UILabel *gameLabel;
	IBOutlet UIImageView *backgroundImage;
	
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
	UILabel *keyText;
	UILabel *timeText;
	UILabel *tempoText;
	UILabel *barsText;
	UILabel *keyLabel;
	UILabel *timeLabel;
	UILabel *tempoLabel;
	UILabel *barsLabel;
	
	UIButton *startButton;
	UIButton *pauseButton;
	UIButton *disconnectButton;
}

@property (nonatomic, retain) XMLClient *client;
@property (nonatomic, retain) NSString *playerId;
@property (readwrite, assign) BOOL inGame;
@property (nonatomic, retain) UIScrollView *myLengthScrollView;
@property (nonatomic, retain) UIScrollView *myPitchScrollView;
@property (nonatomic, retain) UIButton *buildButton;
@property (nonatomic, retain) IBOutlet UILabel *buildLabel;
@property (nonatomic, retain) IBOutlet UIButton *backButton;
@property (nonatomic, retain) IBOutlet UILabel *gameLabel;
@property (nonatomic, retain) IBOutlet UIImageView *backgroundImage;
@property (nonatomic, retain) NSArray *lengthImages;
@property (nonatomic, retain) NSArray *pitchImages;
@property (nonatomic, retain) NSString *noteLength;
@property (nonatomic, retain) NSString *notePitch;
@property (readwrite, assign) int previousNoteLengthPage;
@property (readwrite, assign) int previousNotePitchPage;
@property (nonatomic, retain) UISlider *keySlider;
@property (nonatomic, retain) UISlider *timeSlider;
@property (nonatomic, retain) UISlider *tempoSlider;
@property (nonatomic, retain) UISlider *barsSlider;
@property (nonatomic, retain) UILabel *keyText;
@property (nonatomic, retain) UILabel *timeText;
@property (nonatomic, retain) UILabel *tempoText;
@property (nonatomic, retain) UILabel *barsText;
@property (nonatomic, retain) UILabel *keyLabel;
@property (nonatomic, retain) UILabel *timeLabel;
@property (nonatomic, retain) UILabel *tempoLabel;
@property (nonatomic, retain) UILabel *barsLabel;
@property (nonatomic, retain) UIButton *startButton;
@property (nonatomic, retain) UIButton *pauseButton;
@property (nonatomic, retain) UIButton *disconnectButton;

- (void) scrollViewDidEndDragging: (UIScrollView *) scrollView willDecelerate: (BOOL) decelerate;
- (void) scrollViewDidEndDecelerating: (UIScrollView *) scrollView;

- (void) determineScrollViewPage: (UIScrollView *) scrollView;

- (IBAction) goBack;
- (void) pause;
- (void) disconnect;
- (void) keySliderValueChanged:(id)sender;
- (void) timeSliderValueChanged:(id)sender;
- (void) tempoSliderValueChanged:(id)sender;
- (void) barsSliderValueChanged:(id)sender;  
- (void) updateBuildLabel;
- (void) sendNoteToServer: (id) sender;
- (void) drawPortraitView;
- (void) drawPortraitLandscapeSideView;
- (void) drawNoteLengths;
- (void) drawWholenotePitches;
- (void) drawHalfnotePitches;
- (void) drawQuarternotePitches;
- (void) drawEighthnotePitches;
- (void) drawGameOptions;
- (void) drawGamePlayOptions;

@end
