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
@synthesize clientConnected;
@synthesize playerId;
@synthesize gamemodeViewController;
@synthesize helpViewController;
@synthesize gameModes;
@synthesize connect;
@synthesize disconnect;
@synthesize ipText;
@synthesize ipLabel;
@synthesize validatedIp;

#pragma mark PlayNoteUpdateDelegate Method

- (void) recievedPlayNoteUpdate: (PlayNoteUpdate*) update {
}

#pragma mark ConnectionUpdateDelegate Method

- (void) recievedConnectionUpdate: (ConnectionUpdate*) update {
	self.playerId = [NSString stringWithString: update.playerNumber];
	if ([playerId isEqualToString:@""]) {
		UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Max players" message:(@"Maximum number of players have connected to %@.", validatedIp) delegate:self cancelButtonTitle:@"Ok" otherButtonTitles:nil];
		[alert show];
		[alert release];
		return;
	}
	self.clientConnected = YES;
	if (self.gamemodeViewController) {
		[self.gamemodeViewController setClientConnected: YES];
		if (self.gamemodeViewController.composeViewController) {
			[self.gamemodeViewController.composeViewController setClientConnected: YES];
		}
	}
	[self drawMain];
}

#pragma mark GameTypeUpdateDelegate Method

- (void) recievedGameTypeUpdate: (GameTypeUpdate*) update {
}

#pragma mark UITextFieldDelegate Method

- (BOOL) textFieldShouldReturn: (UITextField *) textField {
	[textField resignFirstResponder];
	return NO;
}

#pragma mark UIAlertViewDelegate Method

- (void) alertView: (UIAlertView *) actionSheet clickedButtonAtIndex: (NSInteger) buttonIndex {
	if (buttonIndex == 0)
		NSLog(@"alertView: Ok");

	[self drawIP];
}

#pragma mark Interface Methods

- (IBAction) goToGamemodeView {
	gamemodeViewController.client = self.client;
	gamemodeViewController.clientConnected = self.clientConnected;
	gamemodeViewController.playerId = self.playerId;
	[self presentModalViewController:gamemodeViewController animated:YES];
}

- (IBAction) goConnect {
	/*
	 * Setup Client
	 */
	// Get an instance of the ChatTranslations scope.
	TranslationScope* scope = [ServerTranslations get];
	
	//TODO: Validate IP Address
	
	//UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"IP Invalid" message:(@"%@ is an incorrect IP Address.", validatedIp) delegate:self cancelButtonTitle:@"Ok" otherButtonTitles:nil];
	//[alert show];
	//[alert release];
	
	validatedIp = ipText.text;
    
	// Initialize the client with the ChatTranslations scope.
	// 192.168.1.105
	// 128.194.143.165
	self.client = [[XMLClient alloc] initWithHostAddress:validatedIp andPort:2108 
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
}

- (IBAction) goDisconnect {
	[client disconnect];
	[self drawIP];
}

- (IBAction) goToHelpView {
	[self presentModalViewController:helpViewController	animated:YES];
}

#pragma mark Draw Methods

- (void) drawIP {
	connect.hidden = NO;
	disconnect.hidden = YES;
	ipText.hidden = NO;
	ipLabel.hidden = NO;
	gameModes.hidden = YES;
	//ipText.text = @"192.168.1.105";
	//ipText.text = @"128.194.132.140";
	ipText.text = @"128.194.143.165";
}

- (void) drawMain {
	connect.hidden = YES;
	disconnect.hidden = NO;
	ipText.hidden = YES;
	ipLabel.hidden = YES;
	gameModes.hidden = NO;
	if (![playerId isEqualToString:@"player1"]) {
		[self drawGamemodesQuick];
	}
}

- (void) drawGamemodesQuick {
	gamemodeViewController.client = self.client;
	gamemodeViewController.clientConnected = self.clientConnected;
	gamemodeViewController.playerId = self.playerId;
	[self presentModalViewController:gamemodeViewController animated:NO];
	[self performSelector:@selector(drawComposeQuick) withObject:nil];
}

- (void) drawComposeQuick {
	[gamemodeViewController goToCompose];
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

- (void) viewWillAppear:(BOOL) animated {
	[super viewDidAppear:animated];
	if (self.clientConnected == YES) {
		[self performSelector:@selector(drawMain) withObject:nil];
	}
	else {
		[self performSelector:@selector(drawIP) withObject:nil];
	}
}

 // Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
 - (void)viewDidLoad {
	 [super viewDidLoad];
	 ipText.delegate = self;
	 clientConnected = NO;
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
	self.clientConnected = NO;
	if (self.gamemodeViewController) {
		[self.gamemodeViewController setClientConnected: NO];
		if (self.gamemodeViewController.composeViewController) {
			[self.gamemodeViewController.composeViewController setClientConnected: NO];
		}
	}
}

- (void) connectionAttemptFailed:(XMLClient*) connection
{
	NSLog(@"The connection failed to connect!\n");
	self.clientConnected = NO;
	if (self.gamemodeViewController) {
		[self.gamemodeViewController setClientConnected: NO];
		if (self.gamemodeViewController.composeViewController) {
			[self.gamemodeViewController.composeViewController setClientConnected: NO];
		}
	}
	UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Unable to connect" message:(@"Unable to connect to %@.", validatedIp) delegate:self cancelButtonTitle:@"Ok" otherButtonTitles:nil];
	[alert show];
	[alert release];
}

- (void) connectionSuccessful:(XMLClient*) client withSessionId:(NSString*) sessionId;
{
	NSLog(@"Connection successful with session id:%@\n", sessionId);
	
	//TODO: Add IP to the list of successful connection IPs for user to later use
}

@end
