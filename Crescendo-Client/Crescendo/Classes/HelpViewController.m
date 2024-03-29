//
//  HelpViewController.m
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import "HelpViewController.h"


@implementation HelpViewController

@synthesize helpText;

#pragma mark Interface Methods

- (IBAction) goBack {
	[self dismissModalViewControllerAnimated:YES];
}


/*
 * Help TextView
 */

- (void) showMainHelp {

	if(!helpText) {
		helpText = [[UITextView alloc] initWithFrame:CGRectMake(39, 194, 240, 265)];
		helpText.backgroundColor = [UIColor blackColor];
		helpText.textColor = [UIColor whiteColor];
		helpText.font = [UIFont fontWithName:@"helvetica" size:24];
		helpText.editable = NO;
	}
	helpText.text = [NSString stringWithFormat: @"FROM MAIN SCREEN! The help text should go here. Depending on what view the HelpViewController is accessed from, this text should say something different that applies to that view. Crescendo Boosh!"];
	[self.view addSubview:helpText];
}

#pragma mark Initialize View Methods

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

/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
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
}


- (void)dealloc {
    [super dealloc];
	[helpText release];
}


@end
