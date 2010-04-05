//
//  CrescendoViewController.m
//  Crescendo
//
//  Created by Brandon on 3/5/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "CrescendoViewController.h"
#import "AppConfig.h"
#import "ServerRequest.h"

@implementation CrescendoViewController

@synthesize client;
@synthesize composeViewController;
@synthesize gamemodeViewController;
@synthesize helpViewController;
@synthesize trainingViewController;

#pragma mark ChatUpdateDelegate Method

- (void) recievedChatUpdate: (ServerUpdate*) update {
}

#pragma mark Interface Methods

- (IBAction) goToComposeView {
	[self presentModalViewController:composeViewController animated:YES];
}

- (IBAction) goToGamemodeView {
	[self presentModalViewController:gamemodeViewController animated:YES];
}

- (IBAction) goToHelpView {
	[self presentModalViewController:helpViewController	animated:YES];
}

- (IBAction) goToTrainingView {
	NSString* inputText = @"training view";
	
    // Initialize ChatRequest and set message to content's of the text field.
	ServerRequest* request = [[ServerRequest alloc] init];
    [request setMessage:inputText];
    
    // Setup the client to send the message a little later in the run loop.
    [client performSelector:@selector(sendMessage:) withObject: request];
    
    [request release];
	
	[self presentModalViewController:trainingViewController animated:YES];
}

#pragma mark Initialize View Methods

/*
 // The designated initializer. Override to perform setup that is required before the view is loaded.
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


- (void) dealloc {
	[composeViewController release];
	[gamemodeViewController release];
	[helpViewController release];
	[trainingViewController release];
    [super dealloc];
}

@end
