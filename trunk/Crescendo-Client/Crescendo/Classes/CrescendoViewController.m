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
@synthesize gameModes;
@synthesize connect;
@synthesize ipText;
@synthesize ipLabel;

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

- (IBAction) goConnect {
	/*
	 * Setup Client
	 */
	// Get an instance of the ChatTranslations scope.
	TranslationScope* scope = [ServerTranslations get];
    
	// Initialize the client with the ChatTranslations scope.
	// 192.168.1.105
	// 128.194.143.165
	self.client = [[XMLClient alloc] initWithHostAddress:@"128.194.143.165" andPort:2108 
									 andTranslationScope:scope];
	
	// Designate self as the client's delegate.
	self.client.delegate = self;
	
	// Set up the self as the CONNECTION_UPDATE_DELEGATE in the application scope.
	[self.client.scope setObject:[NSValue valueWithPointer:(self)] forKey:CONNECTION_UPDATE_DELEGATE];
	
	// Connect the client to the server.
	[self.client connect];	
	
	/*
	 * Connection Request
	 */
	
	// Initialize a Connection Request.
	ConnectionRequest* request = [[ConnectionRequest alloc] init];
	
	// Setup the client to send the connection request a little later in the run loop.
	[client performSelector:@selector(sendMessage:) withObject: request];
	
	[request release];
	
	// Draw main screen with game modes
    [self drawMain];
}

- (IBAction) goToHelpView {
	[self presentModalViewController:helpViewController	animated:YES];
}

#pragma mark Draw Methods

- (void) drawIP {
	connect.hidden = NO;
	ipText.hidden = NO;
	ipLabel.hidden = NO;
	gameModes.hidden = YES;
}

- (void) drawMain {
	connect.hidden = YES;
	ipText.hidden = YES;
	ipLabel.hidden = YES;
	gameModes.hidden = NO;
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
	 [self drawIP];
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

#pragma mark XMLClientDelegate Methods

- (void) connectionTerminated:(XMLClient*)client
{
	NSLog(@"The connection terminated!\n");
}

- (void) connectionAttemptFailed:(XMLClient*) connection
{
	NSLog(@"The connection failed to connect!\n");
}

- (void) connectionSuccessful:(XMLClient*) client withSessionId:(NSString*) sessionId;
{
	NSLog(@"Connection successful with session id:%@\n", sessionId);
}

#pragma mark UIApplicationDelegate Methods

// Close the connection when the user hits the home button.
- (void) applicationWillTerminate:(UIApplication*) application
{
	[client disconnect];
}

@end
