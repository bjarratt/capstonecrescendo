//
//  CrescendoViewController.m
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import "CrescendoViewController.h"
#import "AppConfig.h"

#import "ConnectionRequest.h"
#import "GameTypeRequest.h"
#import "PlayNoteRequest.h"

@implementation CrescendoViewController

@synthesize client;
@synthesize playerId;
@synthesize gamemodeViewController;
@synthesize helpViewController;

#pragma mark PlayNoteUpdateDelegate Method

- (void) recievedPlayNoteUpdate: (PlayNoteUpdate*) update {
}

#pragma mark ConnectionUpdateDelegate Method

- (void) recievedConnectionUpdate: (ConnectionUpdate*) update {
	self.playerId = [NSString stringWithString: update.playerNumber];
}

#pragma mark GameTypeUpdateDelegate Method

- (void) recievedGameTypeUpdate: (GameTypeUpdate*) update {
}

#pragma mark Interface Methods

- (IBAction) goToGamemodeView {
	gamemodeViewController.client = self.client;
	gamemodeViewController.playerId = self.playerId;
	
	[self presentModalViewController:gamemodeViewController animated:YES];
}

- (IBAction) goToHelpView {
	[self presentModalViewController:helpViewController	animated:YES];
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

#pragma mark Initialize View Methods

 // Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
 - (void)viewDidLoad {
	 [super viewDidLoad];
	 
	 // Initialize a Connection Request.
	 ConnectionRequest* request = [[ConnectionRequest alloc] init];
	 
	 // Setup the client to send the connection request a little later in the run loop.
	 [client performSelector:@selector(sendMessage:) withObject: request];
	 
	 [request release];
 }

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
	[gamemodeViewController release];
	[helpViewController release];
    [super dealloc];
}

@end
