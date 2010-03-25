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
	else if (scrollView == myPitchScrollView)
	{
		if (page >= 0.87)
		{
			pitchPage = 15;
		}
		else if (page >= 0.86)
		{
			pitchPage = 14;
		}
		else if (page >= 0.84)
		{
			pitchPage = 13;
		}
		else if (page >= 0.78)
		{
			pitchPage = 12;
		}
		else if (page >= 0.73)
		{
			pitchPage = 11;
		}
		else if (page >= 0.66)
		{
			pitchPage = 10;
		}
		else if (page >= 0.59)
		{
			pitchPage = 9;
		}
		else if (page >= 0.52)
		{
			pitchPage = 8;
		}
		else if (page >= 0.45)
		{
			pitchPage = 7;
		}
		else if (page >= 0.38)
		{
			pitchPage = 6;
		}
		else if (page >= 0.31)
		{
			pitchPage = 5;
		}
		else if (page >= 0.24)
		{
			pitchPage = 4;
		}
		else if (page >= 0.17)
		{
			pitchPage = 3;
		}
		else if (page >= 0.1)
		{
			pitchPage = 2;
		}
		else if (page >= 0)
		{
			pitchPage = 1;
		}
	}
}

#pragma mark Interface Methods

- (IBAction) build {
	buildLabel.text = [NSString stringWithFormat:@"%d / %d", lengthPage, pitchPage];
}

- (IBAction) goBack {
	[self dismissModalViewControllerAnimated:YES];
}

#pragma mark Initialize View Methods

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	pitchPage = 1;
	lengthPage = 1;
    myLengthScrollView.contentSize = CGSizeMake(885, 200);
	myPitchScrollView.contentSize = CGSizeMake(2715, 200);
	[myLengthScrollView setDelegate:self];
	[myPitchScrollView setDelegate:self];
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
