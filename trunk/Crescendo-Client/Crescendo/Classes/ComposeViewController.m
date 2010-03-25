//
//  ComposeViewController.m
//  Crescendo
//
//  Created by Brandon on 3/5/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "ComposeViewController.h"


@implementation ComposeViewController

@synthesize myHolderView;
@synthesize myLengthScrollView;
@synthesize myPitchScrollView;
@synthesize buildLabel;
@synthesize noteLength;
@synthesize notePitch;

#pragma mark UIScrollViewDelegate Methods

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
	
	[self build];
}

#pragma mark Interface Methods

- (IBAction) build {
	buildLabel.text = [NSString stringWithFormat:@"%@ / %@", noteLength, notePitch];
}

- (IBAction) goBack {
	[self dismissModalViewControllerAnimated:YES];
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
	
	float xCoord = 0;
	
	/*
	 * Note Length Scrollview
	 */
	xCoord = 76;
	myLengthScrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 65, 320, 200)];
    myLengthScrollView.delegate = self;
	myLengthScrollView.contentSize = CGSizeMake(xCoord + 168*4 + xCoord, 200);
	myLengthScrollView.scrollEnabled = YES;
	myLengthScrollView.canCancelContentTouches = NO;
	[myLengthScrollView setUserInteractionEnabled:YES];
	
	/*
	 * Note Lengths
	 */
	UIImageView *lenWholenote = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[lenWholenote setImage:[UIImage imageNamed: @"wholenote.png"]];
	lenWholenote.opaque = YES;
	[myLengthScrollView addSubview:lenWholenote];
	[lenWholenote release];
	
	xCoord += 168;
	UIImageView *lenHalfnote = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[lenHalfnote setImage:[UIImage imageNamed: @"halfnote.png"]];
	lenHalfnote.opaque = YES;
	[myLengthScrollView addSubview:lenHalfnote];
	[lenHalfnote release];
	
	xCoord += 168;
	UIImageView *lenQuarternote = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[lenQuarternote setImage:[UIImage imageNamed: @"quarternote.png"]];
	lenQuarternote.opaque = YES;
	[myLengthScrollView addSubview:lenQuarternote];
	[lenQuarternote release];
	
	xCoord += 168;
	UIImageView *lenEighthnote = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[lenEighthnote setImage:[UIImage imageNamed: @"eighthnote.png"]];
	lenEighthnote.opaque = YES;
	[myLengthScrollView addSubview:lenEighthnote];
	[lenEighthnote release];
	
	/*
	xCoord += 168;
	UIImageView * = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[ setImage:[UIImage imageNamed: @".png"]];
	.opaque = YES;
	[myPitchScrollView addSubview:];
	[ release];
	*/
	
	[self.view addSubview:myLengthScrollView];
	
	/*
	 * Note Pitch Scrollview
	 */
	xCoord = 76;
	myPitchScrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 265, 320, 200)];
    myPitchScrollView.delegate = self;
	myPitchScrollView.contentSize = CGSizeMake(xCoord + 168*15 + xCoord, 200);
	myPitchScrollView.scrollEnabled = YES;
	myPitchScrollView.canCancelContentTouches = NO;
	[myPitchScrollView setUserInteractionEnabled:YES];
	
	/*
	 * Note Pitches
	 */
	UIImageView *pitF6 = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[pitF6 setImage:[UIImage imageNamed: @"F6.png"]];
	pitF6.opaque = YES;
	[myPitchScrollView addSubview:pitF6];
	[pitF6 release];
	
	xCoord += 168;
	UIImageView *pitFSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[pitFSharp6 setImage:[UIImage imageNamed: @"FSharp6.png"]];
	pitFSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitFSharp6];
	[pitFSharp6 release];
	
	xCoord += 168;
	UIImageView *pitE6 = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[pitE6 setImage:[UIImage imageNamed: @"E6.png"]];
	pitE6.opaque = YES;
	[myPitchScrollView addSubview:pitE6];
	[pitE6 release];
	
	xCoord += 168;
	UIImageView *pitD6 = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[pitD6 setImage:[UIImage imageNamed: @"D6.png"]];
	pitD6.opaque = YES;
	[myPitchScrollView addSubview:pitD6];
	[pitD6 release];
	
	xCoord += 168;
	UIImageView *pitDSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[pitDSharp6 setImage:[UIImage imageNamed: @"DSharp6.png"]];
	pitDSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitDSharp6];
	[pitDSharp6 release];
	
	xCoord += 168;
	UIImageView *pitC6 = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[pitC6 setImage:[UIImage imageNamed: @"C6.png"]];
	pitC6.opaque = YES;
	[myPitchScrollView addSubview:pitC6];
	[pitC6 release];
	
	xCoord += 168;
	UIImageView *pitCSharp6 = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[pitCSharp6 setImage:[UIImage imageNamed: @"CSharp6.png"]];
	pitCSharp6.opaque = YES;
	[myPitchScrollView addSubview:pitCSharp6];
	[pitCSharp6 release];
	
	xCoord += 168;
	UIImageView *pitB5 = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[pitB5 setImage:[UIImage imageNamed: @"B5.png"]];
	pitB5.opaque = YES;
	[myPitchScrollView addSubview:pitB5];
	[pitB5 release];
	
	xCoord += 168;
	UIImageView *pitA5 = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[pitA5 setImage:[UIImage imageNamed: @"A5.png"]];
	pitA5.opaque = YES;
	[myPitchScrollView addSubview:pitA5];
	[pitA5 release];
	
	xCoord += 168;
	UIImageView *pitASharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[pitASharp5 setImage:[UIImage imageNamed: @"ASharp5.png"]];
	pitASharp5.opaque = YES;
	[myPitchScrollView addSubview:pitASharp5];
	[pitASharp5 release];
	
	xCoord += 168;
	UIImageView *pitG5 = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[pitG5 setImage:[UIImage imageNamed: @"G5.png"]];
	pitG5.opaque = YES;
	[myPitchScrollView addSubview:pitG5];
	[pitG5 release];
	
	xCoord += 168;
	UIImageView *pitGSharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[pitGSharp5 setImage:[UIImage imageNamed: @"GSharp5.png"]];
	pitGSharp5.opaque = YES;
	[myPitchScrollView addSubview:pitGSharp5];
	[pitGSharp5 release];
	
	xCoord += 168;
	UIImageView *pitF5 = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[pitF5 setImage:[UIImage imageNamed: @"F5.png"]];
	pitF5.opaque = YES;
	[myPitchScrollView addSubview:pitF5];
	[pitF5 release];
	
	xCoord += 168;
	UIImageView *pitFSharp5 = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[pitFSharp5 setImage:[UIImage imageNamed: @"FSharp5.png"]];
	pitFSharp5.opaque = YES;
	[myPitchScrollView addSubview:pitFSharp5];
	[pitFSharp5 release];
	
	xCoord += 168;
	UIImageView *pitE5 = [[UIImageView alloc] initWithFrame: CGRectMake(xCoord, 0, 160, 160)];
	[pitE5 setImage:[UIImage imageNamed: @"E5.png"]];
	pitE5.opaque = YES;
	[myPitchScrollView addSubview:pitE5];
	[pitE5 release];
	
	[self.view addSubview:myPitchScrollView];
}

/*
 // The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        // Custom initialization
    }
    return self;
}
*/

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView {
}
*/

#pragma mark Autorotate Orientation Override

/*
// Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
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
	myHolderView = nil;
}


- (void)dealloc {
    [super dealloc];
	[myPitchScrollView release];
	[myLengthScrollView release];
	[myHolderView release];
	[buildLabel release];
}


@end
