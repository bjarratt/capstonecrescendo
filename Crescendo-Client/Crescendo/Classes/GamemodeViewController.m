//
//  GamemodeViewController.m
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import "GamemodeViewController.h"
#import "GameTypeRequest.h"

@implementation GamemodeViewController

@synthesize client;
@synthesize playerId;
@synthesize composeViewController;

#pragma mark Interface Methods

- (IBAction) goBack {
	[self dismissModalViewControllerAnimated:YES];
}

- (IBAction) freeCompose {
	composeViewController.client = self.client;
	composeViewController.playerId = self.playerId;
	/*
	 *	Send game type selected to public display
	 */
	NSString* inputText = @"free compose";
	
    // Initialize PlayNoteRequest and set message to content's of the text field.
	GameTypeRequest* request = [[GameTypeRequest alloc] init];
    [request setGameType:inputText];
    
    // Setup the client to send the message a little later in the run loop.
    [client performSelector:@selector(sendMessage:) withObject: request];
    
    [request release];
	
	[self presentModalViewController:composeViewController animated:YES];
}
- (IBAction) noteLengths {
	composeViewController.client = self.client;
	composeViewController.playerId = self.playerId;
	/*
	 *	Send game type selected to public display
	 */
	NSString *inputText = @"note lengths";
	
    // Initialize PlayNoteRequest and set message to content's of the text field.
	GameTypeRequest* request = [[GameTypeRequest alloc] init];
    [request setGameType:inputText];
    
    // Setup the client to send the message a little later in the run loop.
    [client performSelector:@selector(sendMessage:) withObject: request];
    
    [request release];
	
	[self presentModalViewController:composeViewController animated:YES];
}
- (IBAction) notePitches {
	composeViewController.client = self.client;
	composeViewController.playerId = self.playerId;
	/*
	 *	Send game type selected to public display
	 */
	NSString *inputText = @"note pitches";
	
    // Initialize PlayNoteRequest and set message to content's of the text field.
	GameTypeRequest* request = [[GameTypeRequest alloc] init];
    [request setGameType:inputText];
    
    // Setup the client to send the message a little later in the run loop.
    [client performSelector:@selector(sendMessage:) withObject: request];
    
    [request release];
	
	[self presentModalViewController:composeViewController animated:YES];
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
}


@end