//
//  ComposeViewController.m
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import "ComposeViewController.h"
//#import "ServerRequest.h"


@implementation ComposeViewController

@synthesize client;
@synthesize playerId;
@synthesize myLengthScrollView;
@synthesize myPitchScrollView;
@synthesize buildButton;
@synthesize buildLabel;
@synthesize backButton;
@synthesize lengthImages;
@synthesize pitchImages;
@synthesize noteLength;
@synthesize notePitch;
@synthesize previousNoteLengthPage;
@synthesize previousNotePitchPage;

#pragma mark Shake

- (BOOL) canBecomeFirstResponder {
	return YES;
}

- (void) motionBegan: (UIEventSubtype) motion 
		   withEvent: (UIEvent *) event {
}

- (void) motionEnded: (UIEventSubtype) motion 
		   withEvent: (UIEvent *) event {
	
	if (event.type == UIEventSubtypeMotionShake) {
		//[self sendNoteToServer];
	}
}

#pragma mark Rotation

- (void) willRotateToInterfaceOrientation: (UIInterfaceOrientation) toInterfaceOrientation 
								 duration:(NSTimeInterval) duration {
	
	if (toInterfaceOrientation == UIInterfaceOrientationLandscapeLeft || toInterfaceOrientation == UIInterfaceOrientationLandscapeRight) {
		[self drawPortraitLandscapeSideView];
	}
	else {
		[self drawPortraitView];
	}
}

- (BOOL) shouldAutorotateToInterfaceOrientation: (UIInterfaceOrientation) interfaceOrientation {
	return YES;
}

#pragma mark ScrollView 

- (void) scrollViewDidEndDragging: (UIScrollView *) scrollView
				   willDecelerate: (BOOL) decelerate {
	
	if (decelerate == YES) // Wait until it is done decelerating (calls scrollViewDidEndDecelerating)
		return;
	
	[self determineScrollViewPage:scrollView];
}

- (void) scrollViewDidEndDecelerating: (UIScrollView *) scrollView {
	[self determineScrollViewPage:scrollView];
}

- (void) determineScrollViewPage: (UIScrollView *) scrollView {
	int noteLengthPage = 0;
	int notePitchPage = 0;
	
	float page = scrollView.contentOffset.y / scrollView.contentSize.height;
		
	if (scrollView == myLengthScrollView)
	{
		if (page >= 0.503)
		{
			noteLength = @"eighthnote";
			noteLengthPage = 3;
		}
		else if(page >= 0.2997)
		{
			noteLength = @"quarternote";
			noteLengthPage = 2;
		}
		else if (page >= 0.0958)
		{
			noteLength = @"halfnote";
			noteLengthPage = 1;
		}
		else if (page >= 0)
		{
			noteLength = @"wholenote";
			noteLengthPage = 0;
		}
		
		if (previousNoteLengthPage != noteLengthPage) {
			// Unhighlight unselected note length
			UIImageView * unselectedNoteLength = [lengthImages objectAtIndex:previousNoteLengthPage];
			unselectedNoteLength.highlighted = NO;
			//[unselectedNoteLength release];		
			
			// Highlight selected note length
			UIImageView * selectedNoteLength = [lengthImages objectAtIndex:noteLengthPage];
			selectedNoteLength.highlighted = YES;
			//[selectedNoteLength release];
			
			// Hide unassociated note pitches 
			for (UIImageView *currentView in pitchImages) {
				[currentView removeFromSuperview];
				[currentView release];
			}
			
			// Show associated note pitches
			switch (noteLengthPage) {
				case 0:
					[self drawWholenotePitches];
					break;
				case 1:
					[self drawHalfnotePitches];
					break;
				case 2:
					[self drawQuarternotePitches];
					break;
				case 3:
					[self drawEighthnotePitches];
					break;
				default:
					break;
			}
			
			// Highlight selected note pitch
			UIImageView *selectedView = [pitchImages objectAtIndex:previousNotePitchPage];
			selectedView.highlighted = YES;			
		}
		
		// Update Page Variable
		previousNoteLengthPage = noteLengthPage;
	}
	else if (scrollView == myPitchScrollView)
	{
		if (page >= 0.8469)
		{
			notePitch = @"E5";
			notePitchPage = 14;
		}
		else if (page >= 0.7840)
		{
			notePitch = @"FSharp5";
			notePitchPage = 13;
		}
		else if (page >= 0.7211)
		{
			notePitch = @"F5";
			notePitchPage = 12;
		}
		else if (page >= 0.6583)
		{
			notePitch = @"GSharp5";
			notePitchPage = 11;
		}
		else if (page >= 0.5954)
		{
			notePitch = @"G5";
			notePitchPage = 10;
		}
		else if (page >= 0.5325)
		{
			notePitch = @"ASharp5";
			notePitchPage = 9;
		}
		else if (page >= 0.4696)
		{
			notePitch = @"A5";
			notePitchPage = 8;
		}
		else if (page >= 0.4068)
		{
			notePitch = @"B5";
			notePitchPage = 7;
		}
		else if (page >= 0.3439)
		{
			notePitch = @"CSharp6";
			notePitchPage = 6;
		}
		else if (page >= 0.2810)
		{
			notePitch = @"C6";
			notePitchPage = 5;
		}
		else if (page >= 0.2181)
		{
			notePitch = @"DSharp6";
			notePitchPage = 4;
		}
		else if (page >= 0.1553)
		{
			notePitch = @"D6";
			notePitchPage = 3;
		}
		else if (page >= 0.0924)
		{
			notePitch = @"E6";
			notePitchPage = 2;
		}
		else if (page >= 0.0295)
		{
			notePitch = @"FSharp6";
			notePitchPage = 1;
		}
		else if (page >= 0)
		{
			notePitch = @"F6";
			notePitchPage = 0;
		}
		
		if (previousNotePitchPage != notePitchPage) {
			// Highlight selected note pitch
			UIImageView *selectedView = [pitchImages objectAtIndex:notePitchPage];
			selectedView.highlighted = YES;
			//[selectedView release];
			
			// Unhighlight previous selected note pitch
			UIImageView *previousView = [pitchImages objectAtIndex:previousNotePitchPage];
			previousView.highlighted = NO;
			//[previousView release];
		}
		
		previousNotePitchPage = notePitchPage;
	}
	
	// Update interface label
	[self updateBuildLabel];
}

#pragma mark OODSS Methods

- (void) sendNoteToServer: (id) sender {
	/*
	 *	Send notelength/notepitch to public display
	 
	NSString* inputText = [NSString stringWithFormat: @"%@:%@", playerId, buildLabel.text];
	
    // Initialize ChatRequest and set message to content's of the text field.
	ServerRequest* request = [[ServerRequest alloc] init];
    [request setMessage:inputText];
    
    // Setup the client to send the message a little later in the run loop.
    [client performSelector:@selector(sendMessage:) withObject: request];
    
    [request release];
	 */
}

#pragma mark Interface Methods

- (IBAction) goBack {
	[self dismissModalViewControllerAnimated:YES];
}

- (void) updateBuildLabel {
	buildLabel.text = [NSString stringWithFormat:@"%@_%@", noteLength, notePitch];
}

#pragma mark Draw Methods

- (void) drawPortraitView {
	/*
	 * Properties
	 */
	[myLengthScrollView removeFromSuperview];
	[myPitchScrollView removeFromSuperview];
	[buildButton removeFromSuperview];
	backButton.hidden = NO;
	buildLabel.hidden = YES;
}

- (void) drawPortraitLandscapeSideView {
	/*
	 * Initialize Variables
	 */
	noteLength = @"wholenote";
	notePitch = @"F6";
	previousNoteLengthPage = 1;
	previousNotePitchPage = 1;	
	
	/*
	 * Properties
	 */
	[myLengthScrollView removeFromSuperview];
	[myPitchScrollView removeFromSuperview];
	[buildButton removeFromSuperview];
	backButton.hidden = YES;
	buildLabel.hidden = NO;
	
	float yCoord = 0;
	
	/*
	 * Build Button
	 */
	buildButton = [UIButton buttonWithType: UIButtonTypeRoundedRect];
	buildButton.frame = CGRectMake(204, 160, 70, 37);
	[buildButton setTitle: @"Build" forState: UIControlStateNormal];
	//[buildButton setBackgroundImage:@"menu_button_up.png" forState: UIControlStateNormal];
	[buildButton addTarget:self	action:@selector(sendNoteToServer:) forControlEvents:UIControlEventTouchUpInside];
	[self.view addSubview:buildButton];
	
	/*
	 * Note Pitch Scrollview
	 */
	yCoord = 76;
	myPitchScrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(300, 0, 200, 320)];
    myPitchScrollView.delegate = self;
	myPitchScrollView.contentSize = CGSizeMake(200, yCoord + 168*15 + yCoord);
	myPitchScrollView.scrollEnabled = YES;
	myPitchScrollView.canCancelContentTouches = NO;
	myPitchScrollView.showsVerticalScrollIndicator = NO;
	myPitchScrollView.showsHorizontalScrollIndicator = NO;
	[myPitchScrollView setUserInteractionEnabled:YES];
	
	//[self drawWholenotePitches];
	
	[self.view addSubview:myPitchScrollView];	
	
	/*
	 * Note Length Scrollview
	 */
	yCoord = 76;
	myLengthScrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(20, 0, 200, 320)];
    myLengthScrollView.delegate = self;
	myLengthScrollView.contentSize = CGSizeMake(200, yCoord + 168*4 + yCoord);
	myLengthScrollView.scrollEnabled = YES;
	myLengthScrollView.canCancelContentTouches = NO;
	myLengthScrollView.showsVerticalScrollIndicator = NO;
	myLengthScrollView.showsHorizontalScrollIndicator = NO;
	[myLengthScrollView setUserInteractionEnabled:YES];
	
	[self drawNoteLengths];
	
	[self.view addSubview:myLengthScrollView];
}

- (void) drawNoteLengths {
	float yCoord = 0;

	/*
	 * Note Lengths
	 */
	yCoord = 76;
	UIImageView *lenWholenote = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[lenWholenote setImage:[UIImage imageNamed: @"wholenote.png"]];
	[lenWholenote setHighlightedImage:[UIImage imageNamed:@"wholenote_selected.png"]];
	lenWholenote.opaque = YES;
	[myLengthScrollView addSubview:lenWholenote];
	
	yCoord += 168;
	UIImageView *lenHalfnote = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[lenHalfnote setImage:[UIImage imageNamed: @"halfnote.png"]];
	[lenHalfnote setHighlightedImage:[UIImage imageNamed:@"halfnote_selected.png"]];
	lenHalfnote.opaque = YES;
	[myLengthScrollView addSubview:lenHalfnote];
	
	yCoord += 168;
	UIImageView *lenQuarternote = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[lenQuarternote setImage:[UIImage imageNamed: @"quarternote.png"]];
	[lenQuarternote setHighlightedImage:[UIImage imageNamed:@"quarternote_selected.png"]];
	lenQuarternote.opaque = YES;
	[myLengthScrollView addSubview:lenQuarternote];
	
	yCoord += 168;
	UIImageView *lenEighthnote = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[lenEighthnote setImage:[UIImage imageNamed: @"eighthnote.png"]];
	[lenEighthnote setHighlightedImage:[UIImage imageNamed:@"eighthnote_selected.png"]];
	lenEighthnote.opaque = YES;
	[myLengthScrollView addSubview:lenEighthnote];
	
	/*
	 * Note Lengths Array
	 */
	lengthImages = [[NSArray alloc] initWithObjects: lenWholenote,
					lenHalfnote,
					lenQuarternote,
					lenEighthnote, nil];
	/*
	[lenWholenote release];
	[lenHalfnote release];
	[lenQuarternote release];
	[lenEighthnote release];
	*/
	
	[self determineScrollViewPage:myLengthScrollView];
}

- (void) drawWholenotePitches {
	float yCoord = 76;
	
	/*
	 * Note Pitches (Wholenote)
	 */
	UIImageView *pitWholeF6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitWholeF6 setImage:[UIImage imageNamed: @"F6_whole.png"]];
	[pitWholeF6 setHighlightedImage:[UIImage imageNamed:@"F6_whole_selected.png"]];
	pitWholeF6.opaque = YES;
	[myPitchScrollView addSubview:pitWholeF6];
	
	yCoord += 168;
	UIImageView *pitWholeFSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitWholeFSharp6 setImage:[UIImage imageNamed: @"FSharp6_whole.png"]];
	[pitWholeFSharp6 setHighlightedImage:[UIImage imageNamed:@"FSharp6_whole_selected.png"]];
	pitWholeFSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitWholeFSharp6];
	
	yCoord += 168;
	UIImageView *pitWholeE6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitWholeE6 setImage:[UIImage imageNamed: @"E6_whole.png"]];
	[pitWholeE6 setHighlightedImage:[UIImage imageNamed:@"E6_whole_selected.png"]];
	pitWholeE6.opaque = YES;
	[myPitchScrollView addSubview:pitWholeE6];
	
	yCoord += 168;
	UIImageView *pitWholeD6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitWholeD6 setImage:[UIImage imageNamed: @"D6_whole.png"]];
	[pitWholeD6 setHighlightedImage:[UIImage imageNamed:@"D6_whole_selected.png"]];
	pitWholeD6.opaque = YES;
	[myPitchScrollView addSubview:pitWholeD6];
	
	yCoord += 168;
	UIImageView *pitWholeDSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitWholeDSharp6 setImage:[UIImage imageNamed: @"DSharp6_whole.png"]];
	[pitWholeDSharp6 setHighlightedImage:[UIImage imageNamed:@"DSharp6_whole_selected.png"]];
	pitWholeDSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitWholeDSharp6];
	
	yCoord += 168;
	UIImageView *pitWholeC6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitWholeC6 setImage:[UIImage imageNamed: @"C6_whole.png"]];
	[pitWholeC6 setHighlightedImage:[UIImage imageNamed:@"C6_whole_selected.png"]];
	pitWholeC6.opaque = YES;
	[myPitchScrollView addSubview:pitWholeC6];
	
	yCoord += 168;
	UIImageView *pitWholeCSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitWholeCSharp6 setImage:[UIImage imageNamed: @"CSharp6_whole.png"]];
	[pitWholeCSharp6 setHighlightedImage:[UIImage imageNamed:@"CSharp6_whole_selected.png"]];
	pitWholeCSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitWholeCSharp6];
	
	yCoord += 168;
	UIImageView *pitWholeB5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitWholeB5 setImage:[UIImage imageNamed: @"B5_whole.png"]];
	[pitWholeB5 setHighlightedImage:[UIImage imageNamed:@"B5_whole_selected.png"]];
	pitWholeB5.opaque = YES;
	[myPitchScrollView addSubview:pitWholeB5];
	
	yCoord += 168;
	UIImageView *pitWholeA5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitWholeA5 setImage:[UIImage imageNamed: @"A5_whole.png"]];
	[pitWholeA5 setHighlightedImage:[UIImage imageNamed:@"A5_whole_selected.png"]];
	pitWholeA5.opaque = YES;
	[myPitchScrollView addSubview:pitWholeA5];
	
	yCoord += 168;
	UIImageView *pitWholeASharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitWholeASharp5 setImage:[UIImage imageNamed: @"ASharp5_whole.png"]];
	[pitWholeASharp5 setHighlightedImage:[UIImage imageNamed:@"ASharp5_whole_selected.png"]];
	pitWholeASharp5.opaque = YES;
	[myPitchScrollView addSubview:pitWholeASharp5];
	
	yCoord += 168;
	UIImageView *pitWholeG5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitWholeG5 setImage:[UIImage imageNamed: @"G5_whole.png"]];
	[pitWholeG5 setHighlightedImage:[UIImage imageNamed:@"G5_whole_selected.png"]];
	pitWholeG5.opaque = YES;
	[myPitchScrollView addSubview:pitWholeG5];
	
	yCoord += 168;
	UIImageView *pitWholeGSharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitWholeGSharp5 setImage:[UIImage imageNamed: @"GSharp5_whole.png"]];
	[pitWholeGSharp5 setHighlightedImage:[UIImage imageNamed:@"GSharp5_whole_selected.png"]];
	pitWholeGSharp5.opaque = YES;
	[myPitchScrollView addSubview:pitWholeGSharp5];
	
	yCoord += 168;
	UIImageView *pitWholeF5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitWholeF5 setImage:[UIImage imageNamed: @"F5_whole.png"]];
	[pitWholeF5 setHighlightedImage:[UIImage imageNamed:@"F5_whole_selected.png"]];
	pitWholeF5.opaque = YES;
	[myPitchScrollView addSubview:pitWholeF5];
	
	yCoord += 168;
	UIImageView *pitWholeFSharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitWholeFSharp5 setImage:[UIImage imageNamed: @"FSharp5_whole.png"]];
	[pitWholeFSharp5 setHighlightedImage:[UIImage imageNamed:@"FSharp5_whole_selected.png"]];
	pitWholeFSharp5.opaque = YES;
	[myPitchScrollView addSubview:pitWholeFSharp5];
	
	yCoord += 168;
	UIImageView *pitWholeE5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitWholeE5 setImage:[UIImage imageNamed: @"E5_whole.png"]];
	[pitWholeE5 setHighlightedImage:[UIImage imageNamed:@"E5_whole_selected.png"]];
	pitWholeE5.opaque = YES;
	[myPitchScrollView addSubview:pitWholeE5];
	
	/*
	 * Note Pitch Array
	 */
	pitchImages = [[NSArray alloc] initWithObjects: pitWholeF6,
									 pitWholeFSharp6,
									 pitWholeE6,
									 pitWholeD6,
									 pitWholeDSharp6,
									 pitWholeC6,
									 pitWholeCSharp6,
									 pitWholeB5,
									 pitWholeA5,
									 pitWholeASharp5,
									 pitWholeG5,
									 pitWholeGSharp5,
									 pitWholeF5,
									 pitWholeFSharp5,
									 pitWholeE5, nil];
	/*
	[pitWholeF6 release];
	[pitWholeFSharp6 release];
	[pitWholeE6 release];
	[pitWholeD6 release];
	[pitWholeDSharp6 release];
	[pitWholeC6 release];
	[pitWholeCSharp6 release];
	[pitWholeB5 release];
	[pitWholeA5 release];
	[pitWholeASharp5 release];
	[pitWholeG5 release];
	[pitWholeGSharp5 release];
	[pitWholeF5 release];
	[pitWholeFSharp5 release];
	[pitWholeE5 release];
	*/
	
	[self determineScrollViewPage:myPitchScrollView];
}

- (void) drawHalfnotePitches {
	float yCoord = 76;
	
	/*
	 * Note Pitches (Halfnote)
	 */
	UIImageView *pitHalfF6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitHalfF6 setImage:[UIImage imageNamed: @"F6_half.png"]];
	[pitHalfF6 setHighlightedImage:[UIImage imageNamed:@"F6_half_selected.png"]];
	pitHalfF6.opaque = YES;
	[myPitchScrollView addSubview:pitHalfF6];
	
	yCoord += 168;
	UIImageView *pitHalfFSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitHalfFSharp6 setImage:[UIImage imageNamed: @"FSharp6_half.png"]];
	[pitHalfFSharp6 setHighlightedImage:[UIImage imageNamed:@"FSharp6_half_selected.png"]];
	pitHalfFSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitHalfFSharp6];
	
	yCoord += 168;
	UIImageView *pitHalfE6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitHalfE6 setImage:[UIImage imageNamed: @"E6_half.png"]];
	[pitHalfE6 setHighlightedImage:[UIImage imageNamed:@"E6_half_selected.png"]];
	pitHalfE6.opaque = YES;
	[myPitchScrollView addSubview:pitHalfE6];
	
	yCoord += 168;
	UIImageView *pitHalfD6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitHalfD6 setImage:[UIImage imageNamed: @"D6_half.png"]];
	[pitHalfD6 setHighlightedImage:[UIImage imageNamed:@"D6_half_selected.png"]];
	pitHalfD6.opaque = YES;
	[myPitchScrollView addSubview:pitHalfD6];
	
	yCoord += 168;
	UIImageView *pitHalfDSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitHalfDSharp6 setImage:[UIImage imageNamed: @"DSharp6_half.png"]];
	[pitHalfDSharp6 setHighlightedImage:[UIImage imageNamed:@"DSharp6_half_selected.png"]];
	pitHalfDSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitHalfDSharp6];
	
	yCoord += 168;
	UIImageView *pitHalfC6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitHalfC6 setImage:[UIImage imageNamed: @"C6_half.png"]];
	[pitHalfC6 setHighlightedImage:[UIImage imageNamed:@"C6_half_selected.png"]];
	pitHalfC6.opaque = YES;
	[myPitchScrollView addSubview:pitHalfC6];
	
	yCoord += 168;
	UIImageView *pitHalfCSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitHalfCSharp6 setImage:[UIImage imageNamed: @"CSharp6_half.png"]];
	[pitHalfCSharp6 setHighlightedImage:[UIImage imageNamed:@"CSharp6_half_selected.png"]];
	pitHalfCSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitHalfCSharp6];
	
	yCoord += 168;
	UIImageView *pitHalfB5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitHalfB5 setImage:[UIImage imageNamed: @"B5_half.png"]];
	[pitHalfB5 setHighlightedImage:[UIImage imageNamed:@"B5_half_selected.png"]];
	pitHalfB5.opaque = YES;
	[myPitchScrollView addSubview:pitHalfB5];
	
	yCoord += 168;
	UIImageView *pitHalfA5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitHalfA5 setImage:[UIImage imageNamed: @"A5_half.png"]];
	[pitHalfA5 setHighlightedImage:[UIImage imageNamed:@"A5_half_selected.png"]];
	pitHalfA5.opaque = YES;
	[myPitchScrollView addSubview:pitHalfA5];
	
	yCoord += 168;
	UIImageView *pitHalfASharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitHalfASharp5 setImage:[UIImage imageNamed: @"ASharp5_half.png"]];
	[pitHalfASharp5 setHighlightedImage:[UIImage imageNamed:@"ASharp5_half_selected.png"]];
	pitHalfASharp5.opaque = YES;
	[myPitchScrollView addSubview:pitHalfASharp5];
	
	yCoord += 168;
	UIImageView *pitHalfG5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitHalfG5 setImage:[UIImage imageNamed: @"G5_half.png"]];
	[pitHalfG5 setHighlightedImage:[UIImage imageNamed:@"G5_half_selected.png"]];
	pitHalfG5.opaque = YES;
	[myPitchScrollView addSubview:pitHalfG5];
	
	yCoord += 168;
	UIImageView *pitHalfGSharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitHalfGSharp5 setImage:[UIImage imageNamed: @"GSharp5_half.png"]];
	[pitHalfGSharp5 setHighlightedImage:[UIImage imageNamed:@"GSharp5_half_selected.png"]];
	pitHalfGSharp5.opaque = YES;
	[myPitchScrollView addSubview:pitHalfGSharp5];
	
	yCoord += 168;
	UIImageView *pitHalfF5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitHalfF5 setImage:[UIImage imageNamed: @"F5_half.png"]];
	[pitHalfF5 setHighlightedImage:[UIImage imageNamed:@"F5_half_selected.png"]];
	pitHalfF5.opaque = YES;
	[myPitchScrollView addSubview:pitHalfF5];
	
	yCoord += 168;
	UIImageView *pitHalfFSharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitHalfFSharp5 setImage:[UIImage imageNamed: @"FSharp5_half.png"]];
	[pitHalfFSharp5 setHighlightedImage:[UIImage imageNamed:@"FSharp5_half_selected.png"]];
	pitHalfFSharp5.opaque = YES;
	[myPitchScrollView addSubview:pitHalfFSharp5];
	
	yCoord += 168;
	UIImageView *pitHalfE5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitHalfE5 setImage:[UIImage imageNamed: @"E5_half.png"]];
	[pitHalfE5 setHighlightedImage:[UIImage imageNamed:@"E5_half_selected.png"]];
	pitHalfE5.opaque = YES;
	[myPitchScrollView addSubview:pitHalfE5];
	
	/*
	 * Note Pitch Array
	 */
	pitchImages = [[NSArray alloc] initWithObjects: pitHalfF6,
									pitHalfFSharp6,
									pitHalfE6,
									pitHalfD6,
									pitHalfDSharp6,
									pitHalfC6,
									pitHalfCSharp6,
									pitHalfB5,
									pitHalfA5,
									pitHalfASharp5,
									pitHalfG5,
									pitHalfGSharp5,
									pitHalfF5,
									pitHalfFSharp5,
									pitHalfE5, nil];
	/*
	[pitHalfF6 release];
	[pitHalfFSharp6 release];
	[pitHalfE6 release];
	[pitHalfD6 release];
	[pitHalfDSharp6 release];
	[pitHalfC6 release];
	[pitHalfCSharp6 release];
	[pitHalfB5 release];
	[pitHalfA5 release];
	[pitHalfASharp5 release];
	[pitHalfG5 release];
	[pitHalfGSharp5 release];
	[pitHalfF5 release];
	[pitHalfFSharp5 release];
	[pitHalfE5 release];
	*/
	
	[self determineScrollViewPage:myPitchScrollView];
}

- (void) drawQuarternotePitches {
	float yCoord = 76;
	
	/*
	 * Note Pitches (Quarternote)
	 */
	UIImageView *pitQuarterF6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitQuarterF6 setImage:[UIImage imageNamed: @"F6_quarter.png"]];
	[pitQuarterF6 setHighlightedImage:[UIImage imageNamed:@"F6_quarter_selected.png"]];
	pitQuarterF6.opaque = YES;
	[myPitchScrollView addSubview:pitQuarterF6];
	
	yCoord += 168;
	UIImageView *pitQuarterFSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitQuarterFSharp6 setImage:[UIImage imageNamed: @"FSharp6_quarter.png"]];
	[pitQuarterFSharp6 setHighlightedImage:[UIImage imageNamed:@"FSharp6_quarter_selected.png"]];
	pitQuarterFSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitQuarterFSharp6];
	
	yCoord += 168;
	UIImageView *pitQuarterE6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitQuarterE6 setImage:[UIImage imageNamed: @"E6_quarter.png"]];
	[pitQuarterE6 setHighlightedImage:[UIImage imageNamed:@"E6_quarter_selected.png"]];
	pitQuarterE6.opaque = YES;
	[myPitchScrollView addSubview:pitQuarterE6];
	
	yCoord += 168;
	UIImageView *pitQuarterD6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitQuarterD6 setImage:[UIImage imageNamed: @"D6_quarter.png"]];
	[pitQuarterD6 setHighlightedImage:[UIImage imageNamed:@"D6_quarter_selected.png"]];
	pitQuarterD6.opaque = YES;
	[myPitchScrollView addSubview:pitQuarterD6];
	
	yCoord += 168;
	UIImageView *pitQuarterDSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitQuarterDSharp6 setImage:[UIImage imageNamed: @"DSharp6_quarter.png"]];
	[pitQuarterDSharp6 setHighlightedImage:[UIImage imageNamed:@"DSharp6_quarter_selected.png"]];
	pitQuarterDSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitQuarterDSharp6];
	
	yCoord += 168;
	UIImageView *pitQuarterC6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitQuarterC6 setImage:[UIImage imageNamed: @"C6_quarter.png"]];
	[pitQuarterC6 setHighlightedImage:[UIImage imageNamed:@"C6_quarter_selected.png"]];
	pitQuarterC6.opaque = YES;
	[myPitchScrollView addSubview:pitQuarterC6];
	
	yCoord += 168;
	UIImageView *pitQuarterCSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitQuarterCSharp6 setImage:[UIImage imageNamed: @"CSharp6_quarter.png"]];
	[pitQuarterCSharp6 setHighlightedImage:[UIImage imageNamed:@"CSharp6_quarter_selected.png"]];
	pitQuarterCSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitQuarterCSharp6];
	
	yCoord += 168;
	UIImageView *pitQuarterB5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitQuarterB5 setImage:[UIImage imageNamed: @"B5_quarter.png"]];
	[pitQuarterB5 setHighlightedImage:[UIImage imageNamed:@"B5_quarter_selected.png"]];
	pitQuarterB5.opaque = YES;
	[myPitchScrollView addSubview:pitQuarterB5];
	
	yCoord += 168;
	UIImageView *pitQuarterA5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitQuarterA5 setImage:[UIImage imageNamed: @"A5_quarter.png"]];
	[pitQuarterA5 setHighlightedImage:[UIImage imageNamed:@"A5_quarter_selected.png"]];
	pitQuarterA5.opaque = YES;
	[myPitchScrollView addSubview:pitQuarterA5];
	
	yCoord += 168;
	UIImageView *pitQuarterASharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitQuarterASharp5 setImage:[UIImage imageNamed: @"ASharp5_quarter.png"]];
	[pitQuarterASharp5 setHighlightedImage:[UIImage imageNamed:@"ASharp5_quarter_selected.png"]];
	pitQuarterASharp5.opaque = YES;
	[myPitchScrollView addSubview:pitQuarterASharp5];
	
	yCoord += 168;
	UIImageView *pitQuarterG5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitQuarterG5 setImage:[UIImage imageNamed: @"G5_quarter.png"]];
	[pitQuarterG5 setHighlightedImage:[UIImage imageNamed:@"G5_quarter_selected.png"]];
	pitQuarterG5.opaque = YES;
	[myPitchScrollView addSubview:pitQuarterG5];
	
	yCoord += 168;
	UIImageView *pitQuarterGSharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitQuarterGSharp5 setImage:[UIImage imageNamed: @"GSharp5_quarter.png"]];
	[pitQuarterGSharp5 setHighlightedImage:[UIImage imageNamed:@"GSharp5_quarter_selected.png"]];
	pitQuarterGSharp5.opaque = YES;
	[myPitchScrollView addSubview:pitQuarterGSharp5];
	
	yCoord += 168;
	UIImageView *pitQuarterF5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitQuarterF5 setImage:[UIImage imageNamed: @"F5_quarter.png"]];
	[pitQuarterF5 setHighlightedImage:[UIImage imageNamed:@"F5_quarter_selected.png"]];
	pitQuarterF5.opaque = YES;
	[myPitchScrollView addSubview:pitQuarterF5];
	
	yCoord += 168;
	UIImageView *pitQuarterFSharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitQuarterFSharp5 setImage:[UIImage imageNamed: @"FSharp5_quarter.png"]];
	[pitQuarterFSharp5 setHighlightedImage:[UIImage imageNamed:@"FSharp5_quarter_selected.png"]];
	pitQuarterFSharp5.opaque = YES;
	[myPitchScrollView addSubview:pitQuarterFSharp5];
	
	yCoord += 168;
	UIImageView *pitQuarterE5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitQuarterE5 setImage:[UIImage imageNamed: @"E5_quarter.png"]];
	[pitQuarterE5 setHighlightedImage:[UIImage imageNamed:@"E5_quarter_selected.png"]];
	pitQuarterE5.opaque = YES;
	[myPitchScrollView addSubview:pitQuarterE5];
	
	/*
	 * Note Pitch Array
	 */
	pitchImages = [[NSArray alloc] initWithObjects: pitQuarterF6,
									   pitQuarterFSharp6,
									   pitQuarterE6,
									   pitQuarterD6,
									   pitQuarterDSharp6,
									   pitQuarterC6,
									   pitQuarterCSharp6,
									   pitQuarterB5,
									   pitQuarterA5,
									   pitQuarterASharp5,
									   pitQuarterG5,
									   pitQuarterGSharp5,
									   pitQuarterF5,
									   pitQuarterFSharp5,
									   pitQuarterE5, nil];
	/*
	[pitQuarterF6 release];
	[pitQuarterFSharp6 release];
	[pitQuarterE6 release];
	[pitQuarterD6 release];
	[pitQuarterDSharp6 release];
	[pitQuarterC6 release];
	[pitQuarterCSharp6 release];
	[pitQuarterB5 release];
	[pitQuarterA5 release];
	[pitQuarterASharp5 release];
	[pitQuarterG5 release];
	[pitQuarterGSharp5 release];
	[pitQuarterF5 release];
	[pitQuarterFSharp5 release];
	[pitQuarterE5 release];
	*/
	
	[self determineScrollViewPage:myPitchScrollView];
}

- (void) drawEighthnotePitches {
	float yCoord = 76;
	
	/*
	 * Note Pitches (Eighthnote)
	 */
	UIImageView *pitEighthF6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitEighthF6 setImage:[UIImage imageNamed: @"F6_eighth.png"]];
	[pitEighthF6 setHighlightedImage:[UIImage imageNamed:@"F6_eighth_selected.png"]];
	pitEighthF6.opaque = YES;
	[myPitchScrollView addSubview:pitEighthF6];
	
	yCoord += 168;
	UIImageView *pitEighthFSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitEighthFSharp6 setImage:[UIImage imageNamed: @"FSharp6_eighth.png"]];
	[pitEighthFSharp6 setHighlightedImage:[UIImage imageNamed:@"FSharp6_eighth_selected.png"]];
	pitEighthFSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitEighthFSharp6];
	
	yCoord += 168;
	UIImageView *pitEighthE6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitEighthE6 setImage:[UIImage imageNamed: @"E6_eighth.png"]];
	[pitEighthE6 setHighlightedImage:[UIImage imageNamed:@"E6_eighth_selected.png"]];
	pitEighthE6.opaque = YES;
	[myPitchScrollView addSubview:pitEighthE6];
	
	yCoord += 168;
	UIImageView *pitEighthD6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitEighthD6 setImage:[UIImage imageNamed: @"D6_eighth.png"]];
	[pitEighthD6 setHighlightedImage:[UIImage imageNamed:@"D6_eighth_selected.png"]];
	pitEighthD6.opaque = YES;
	[myPitchScrollView addSubview:pitEighthD6];
	
	yCoord += 168;
	UIImageView *pitEighthDSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitEighthDSharp6 setImage:[UIImage imageNamed: @"DSharp6_eighth.png"]];
	[pitEighthDSharp6 setHighlightedImage:[UIImage imageNamed:@"DSharp6_eighth_selected.png"]];
	pitEighthDSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitEighthDSharp6];
	
	yCoord += 168;
	UIImageView *pitEighthC6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitEighthC6 setImage:[UIImage imageNamed: @"C6_eighth.png"]];
	[pitEighthC6 setHighlightedImage:[UIImage imageNamed:@"C6_eighth_selected.png"]];
	pitEighthC6.opaque = YES;
	[myPitchScrollView addSubview:pitEighthC6];
	
	yCoord += 168;
	UIImageView *pitEighthCSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitEighthCSharp6 setImage:[UIImage imageNamed: @"CSharp6_eighth.png"]];
	[pitEighthCSharp6 setHighlightedImage:[UIImage imageNamed:@"CSharp6_eighth_selected.png"]];
	pitEighthCSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitEighthCSharp6];
	
	yCoord += 168;
	UIImageView *pitEighthB5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitEighthB5 setImage:[UIImage imageNamed: @"B5_eighth.png"]];
	[pitEighthB5 setHighlightedImage:[UIImage imageNamed:@"B5_eighth_selected.png"]];
	pitEighthB5.opaque = YES;
	[myPitchScrollView addSubview:pitEighthB5];
	
	yCoord += 168;
	UIImageView *pitEighthA5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitEighthA5 setImage:[UIImage imageNamed: @"A5_eighth.png"]];
	[pitEighthA5 setHighlightedImage:[UIImage imageNamed:@"A5_eighth_selected.png"]];
	pitEighthA5.opaque = YES;
	[myPitchScrollView addSubview:pitEighthA5];
	
	yCoord += 168;
	UIImageView *pitEighthASharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitEighthASharp5 setImage:[UIImage imageNamed: @"ASharp5_eighth.png"]];
	[pitEighthASharp5 setHighlightedImage:[UIImage imageNamed:@"ASharp5_eighth_selected.png"]];
	pitEighthASharp5.opaque = YES;
	[myPitchScrollView addSubview:pitEighthASharp5];
	
	yCoord += 168;
	UIImageView *pitEighthG5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitEighthG5 setImage:[UIImage imageNamed: @"G5_eighth.png"]];
	[pitEighthG5 setHighlightedImage:[UIImage imageNamed:@"G5_eighth_selected.png"]];
	pitEighthG5.opaque = YES;
	[myPitchScrollView addSubview:pitEighthG5];
	
	yCoord += 168;
	UIImageView *pitEighthGSharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitEighthGSharp5 setImage:[UIImage imageNamed: @"GSharp5_eighth.png"]];
	[pitEighthGSharp5 setHighlightedImage:[UIImage imageNamed:@"GSharp5_eighth_selected.png"]];
	pitEighthGSharp5.opaque = YES;
	[myPitchScrollView addSubview:pitEighthGSharp5];
	
	yCoord += 168;
	UIImageView *pitEighthF5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitEighthF5 setImage:[UIImage imageNamed: @"F5_eighth.png"]];
	[pitEighthF5 setHighlightedImage:[UIImage imageNamed:@"F5_eighth_selected.png"]];
	pitEighthF5.opaque = YES;
	[myPitchScrollView addSubview:pitEighthF5];
	
	yCoord += 168;
	UIImageView *pitEighthFSharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitEighthFSharp5 setImage:[UIImage imageNamed: @"FSharp5_eighth.png"]];
	[pitEighthFSharp5 setHighlightedImage:[UIImage imageNamed:@"FSharp5_eighth_selected.png"]];
	pitEighthFSharp5.opaque = YES;
	[myPitchScrollView addSubview:pitEighthFSharp5];
	
	yCoord += 168;
	UIImageView *pitEighthE5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitEighthE5 setImage:[UIImage imageNamed: @"E5_eighth.png"]];
	[pitEighthE5 setHighlightedImage:[UIImage imageNamed:@"E5_eighth_selected.png"]];
	pitEighthE5.opaque = YES;
	[myPitchScrollView addSubview:pitEighthE5];
	
	/*
	 * Note Pitch Array
	 */
	pitchImages = [[NSArray alloc] initWithObjects: pitEighthF6,
									  pitEighthFSharp6,
									  pitEighthE6,
									  pitEighthD6,
									  pitEighthDSharp6,
									  pitEighthC6,
									  pitEighthCSharp6,
									  pitEighthB5,
									  pitEighthA5,
									  pitEighthASharp5,
									  pitEighthG5,
									  pitEighthGSharp5,
									  pitEighthF5,
									  pitEighthFSharp5,
									  pitEighthE5, nil];
	/*
	[pitEighthF6 release];
	[pitEighthFSharp6 release];
	[pitEighthE6 release];
	[pitEighthD6 release];
	[pitEighthDSharp6 release];
	[pitEighthC6 release];
	[pitEighthCSharp6 release];
	[pitEighthB5 release];
	[pitEighthA5 release];
	[pitEighthASharp5 release];
	[pitEighthG5 release];
	[pitEighthGSharp5 release];
	[pitEighthF5 release];
	[pitEighthFSharp5 release];
	[pitEighthE5 release];
	*/
	
	[self determineScrollViewPage:myPitchScrollView];
}

#pragma mark Initialize View Methods

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	/******************
	 * User Interface *
	 ******************/
	[self becomeFirstResponder];
	
	[self drawPortraitView];
}

/*
 // The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        // Custom initialization
    }
    return self;
}

// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/

#pragma mark Unload Controller

- (void) didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

- (void) viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}


- (void) dealloc {
    [super dealloc];
	[myLengthScrollView release];
	[myPitchScrollView release];
	[buildLabel release];
	[backButton release];
	[lengthImages release];
	[pitchImages release];
	[noteLength release];
	[notePitch release];
}


@end
