//
//  ComposeViewController.m
//  Crescendo
//
//  Created by Brandon on 3/5/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "ComposeViewController.h"


@implementation ComposeViewController

@synthesize myLengthScrollView;
@synthesize myPitchScrollView;
@synthesize buildLabel;
@synthesize backButton;
@synthesize lengthImages;
@synthesize pitchImages;
@synthesize noteLength;
@synthesize notePitch;

#pragma mark UIViewController Events

- (void) willRotateToInterfaceOrientation: (UIInterfaceOrientation) toInterfaceOrientation duration:(NSTimeInterval) duration {
	if (toInterfaceOrientation == UIInterfaceOrientationPortrait) {
		[self drawPortraitView];
	}
	else if (toInterfaceOrientation == UIInterfaceOrientationLandscapeLeft) {
		[self drawPortraitLandscapeLeftView];
	}
	else {
		buildLabel.hidden = YES;
		backButton.hidden = YES;
	}
}

- (BOOL) shouldAutorotateToInterfaceOrientation: (UIInterfaceOrientation) interfaceOrientation {
	return YES;
}

#pragma mark UIScrollViewDelegate Methods

- (void) scrollViewDidEndDragging: (UIScrollView *) scrollView
				   willDecelerate: (BOOL) decelerate {
	
	if (decelerate == YES) // Wait until it is done decelerating (calls scrollViewDidEndDecelerating)
		return;
	
	[self determineScrollViewPage:scrollView];
}

- (void) scrollViewDidEndDecelerating: (UIScrollView *) scrollView {
	[self determineScrollViewPage:scrollView];
}

#pragma mark Helper Methods

- (void) determineScrollViewPage: (UIScrollView *) scrollView {
	float page = scrollView.contentOffset.y / scrollView.contentSize.height;
	
	if (scrollView == myLengthScrollView)
	{
		if (page >= 0.503)
		{
			noteLength = @"eighthnote";
		}
		else if(page >= 0.2997)
		{
			noteLength = @"quarternote";
		}
		else if (page >= 0.0958)
		{
			noteLength = @"halfnote";
		}
		else if (page >= 0)
		{
			noteLength = @"wholenote";
		}
	}
	else if (scrollView == myPitchScrollView)
	{
		if (page >= 0.8469)
		{
			notePitch = @"E5";
		}
		else if (page >= 0.7840)
		{
			notePitch = @"FSharp5";
		}
		else if (page >= 0.7211)
		{
			notePitch = @"F5";
		}
		else if (page >= 0.6583)
		{
			notePitch = @"GSharp5";
		}
		else if (page >= 0.5954)
		{
			notePitch = @"G5";
		}
		else if (page >= 0.5325)
		{
			notePitch = @"ASharp5";
		}
		else if (page >= 0.4696)
		{
			notePitch = @"A5";
		}
		else if (page >= 0.4068)
		{
			notePitch = @"B5";
		}
		else if (page >= 0.3439)
		{
			notePitch = @"CSharp6";
		}
		else if (page >= 0.2810)
		{
			notePitch = @"C6";
		}
		else if (page >= 0.2181)
		{
			notePitch = @"DSharp6";
		}
		else if (page >= 0.1553)
		{
			notePitch = @"D6";
		}
		else if (page >= 0.0924)
		{
			notePitch = @"E6";
		}
		else if (page >= 0.0295)
		{
			notePitch = @"FSharp6";
		}
		else if (page >= 0)
		{
			notePitch = @"F6";
		}
	}
	
	[self updateBuildLabel];
}

#pragma mark Interface Methods

- (IBAction) goBack {
	[self dismissModalViewControllerAnimated:YES];
}

- (void) updateBuildLabel {
	buildLabel.text = [NSString stringWithFormat:@"%@ / %@", noteLength, notePitch];
}

- (void) drawPortraitView {
	/*
	 * Properties
	 */
	[myLengthScrollView removeFromSuperview];
	[myPitchScrollView removeFromSuperview];
	backButton.hidden = NO;
	buildLabel.hidden = YES;
}

- (void) drawPortraitLandscapeLeftView {
	/*
	 * Properties
	 */
	[myLengthScrollView removeFromSuperview];
	[myPitchScrollView removeFromSuperview];
	backButton.hidden = YES;
	buildLabel.hidden = NO;
	
	float yCoord = 0;
	
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
	
	/*
	 * Note Lengths
	 */
	UIImageView *lenWholenote = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[lenWholenote setImage:[UIImage imageNamed: @"wholenote.png"]];
	lenWholenote.opaque = YES;
	[myLengthScrollView addSubview:lenWholenote];
	
	yCoord += 168;
	UIImageView *lenHalfnote = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[lenHalfnote setImage:[UIImage imageNamed: @"halfnote.png"]];
	lenHalfnote.opaque = YES;
	[myLengthScrollView addSubview:lenHalfnote];
	
	yCoord += 168;
	UIImageView *lenQuarternote = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[lenQuarternote setImage:[UIImage imageNamed: @"quarternote.png"]];
	lenQuarternote.opaque = YES;
	[myLengthScrollView addSubview:lenQuarternote];
	
	yCoord += 168;
	UIImageView *lenEighthnote = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[lenEighthnote setImage:[UIImage imageNamed: @"eighthnote.png"]];
	lenEighthnote.opaque = YES;
	[myLengthScrollView addSubview:lenEighthnote];
	
	/*
	 * Note Lengths Array
	 */
	lengthImages = [[NSArray alloc] initWithObjects: lenWholenote,
					lenHalfnote,
					lenQuarternote,
					lenEighthnote, nil];
	[lenWholenote release];
	[lenHalfnote release];
	[lenQuarternote release];
	[lenEighthnote release];
		
	[self.view addSubview:myLengthScrollView];
	
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
	
	/*
	 * Note Pitches
	 */
	UIImageView *pitF6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitF6 setImage:[UIImage imageNamed: @"F6.png"]];
	pitF6.opaque = YES;
	[myPitchScrollView addSubview:pitF6];
	
	yCoord += 168;
	UIImageView *pitFSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitFSharp6 setImage:[UIImage imageNamed: @"FSharp6.png"]];
	pitFSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitFSharp6];
	
	yCoord += 168;
	UIImageView *pitE6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitE6 setImage:[UIImage imageNamed: @"E6.png"]];
	pitE6.opaque = YES;
	[myPitchScrollView addSubview:pitE6];
	
	yCoord += 168;
	UIImageView *pitD6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitD6 setImage:[UIImage imageNamed: @"D6.png"]];
	pitD6.opaque = YES;
	[myPitchScrollView addSubview:pitD6];
	
	yCoord += 168;
	UIImageView *pitDSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitDSharp6 setImage:[UIImage imageNamed: @"DSharp6.png"]];
	pitDSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitDSharp6];
	
	yCoord += 168;
	UIImageView *pitC6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitC6 setImage:[UIImage imageNamed: @"C6.png"]];
	pitC6.opaque = YES;
	[myPitchScrollView addSubview:pitC6];
	
	yCoord += 168;
	UIImageView *pitCSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitCSharp6 setImage:[UIImage imageNamed: @"CSharp6.png"]];
	pitCSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitCSharp6];
	
	yCoord += 168;
	UIImageView *pitB5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitB5 setImage:[UIImage imageNamed: @"B5.png"]];
	pitB5.opaque = YES;
	[myPitchScrollView addSubview:pitB5];
	
	yCoord += 168;
	UIImageView *pitA5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitA5 setImage:[UIImage imageNamed: @"A5.png"]];
	pitA5.opaque = YES;
	[myPitchScrollView addSubview:pitA5];
	
	yCoord += 168;
	UIImageView *pitASharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitASharp5 setImage:[UIImage imageNamed: @"ASharp5.png"]];
	pitASharp5.opaque = YES;
	[myPitchScrollView addSubview:pitASharp5];
	
	yCoord += 168;
	UIImageView *pitG5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitG5 setImage:[UIImage imageNamed: @"G5.png"]];
	pitG5.opaque = YES;
	[myPitchScrollView addSubview:pitG5];
	
	yCoord += 168;
	UIImageView *pitGSharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitGSharp5 setImage:[UIImage imageNamed: @"GSharp5.png"]];
	pitGSharp5.opaque = YES;
	[myPitchScrollView addSubview:pitGSharp5];
	
	yCoord += 168;
	UIImageView *pitF5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitF5 setImage:[UIImage imageNamed: @"F5.png"]];
	pitF5.opaque = YES;
	[myPitchScrollView addSubview:pitF5];
	
	yCoord += 168;
	UIImageView *pitFSharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitFSharp5 setImage:[UIImage imageNamed: @"FSharp5.png"]];
	pitFSharp5.opaque = YES;
	[myPitchScrollView addSubview:pitFSharp5];
	
	yCoord += 168;
	UIImageView *pitE5 = [[UIImageView alloc] initWithFrame: CGRectMake(0, yCoord, 160, 160)];
	[pitE5 setImage:[UIImage imageNamed: @"E5.png"]];
	pitE5.opaque = YES;
	[myPitchScrollView addSubview:pitE5];
	
	/*
	 * Note Pitches Array
	 */
	pitchImages = [[NSArray alloc] initWithObjects: pitF6,
				   pitFSharp6,
				   pitE6,
				   pitD6,
				   pitDSharp6,
				   pitC6,
				   pitCSharp6,
				   pitB5,
				   pitA5,
				   pitASharp5,
				   pitG5,
				   pitGSharp5,
				   pitF5,
				   pitFSharp5,
				   pitE5, nil];
	[pitF6 release];
	[pitFSharp6 release];
	[pitE6 release];
	[pitD6 release];
	[pitDSharp6 release];
	[pitC6 release];
	[pitCSharp6 release];
	[pitB5 release];
	[pitA5 release];
	[pitASharp5 release];
	[pitG5 release];
	[pitGSharp5 release];
	[pitF5 release];
	[pitFSharp5 release];
	[pitE5 release];
		
	[self.view addSubview:myPitchScrollView];
}

#pragma mark Initialize View Methods

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	/************************
	 * Initialize Variables *
	 ************************/
	noteLength = @"wholenote";
	notePitch = @"F6";
	
	/******************
	 * User Interface *
	 ******************/
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

- (void)didReceiveMemoryWarning {
	// Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload {
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}


- (void)dealloc {
    [super dealloc];
	[myLengthScrollView release];
	[myPitchScrollView release];
	[buildLabel release];
	[lengthImages release];
	[pitchImages release];
	[noteLength release];
	[notePitch release];
}


@end
