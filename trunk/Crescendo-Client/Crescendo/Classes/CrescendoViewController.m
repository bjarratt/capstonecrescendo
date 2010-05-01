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
#import "GameStateRequest.h"
#import "GameTypeRequest.h"
#import "PlayNoteRequest.h"

#import "DataController.h"

@implementation CrescendoViewController

@synthesize client;
@synthesize clientConnected;
@synthesize playerId;
@synthesize composeViewController;
@synthesize helpViewController;
@synthesize start;
@synthesize connect;
@synthesize disconnect;
@synthesize ipText;
@synthesize ipLabel;
@synthesize validatedIp;
@synthesize dataController;
@synthesize ipTableView;

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
	if (self.composeViewController) {
		[self.composeViewController setClientConnected: YES];
	}
	[ipText setText: validatedIp];
	[self drawMain];
	/*
	 * Advance players (other than player 1) to compose screen
	 */
	if(![self.playerId isEqualToString: @"player1"]) {
		[self goStart];
	}
}

#pragma mark GameTypeUpdateDelegate Method

- (void) recievedGameTypeUpdate: (GameTypeUpdate*) update {
}

#pragma mark GameStateUpdateDelegate Method

- (void) recievedGameStateUpdate: (GameStateUpdate*) update {
}

#pragma mark PlayNoteUpdateDelegate Method

- (void) recievedPlayNoteUpdate: (PlayNoteUpdate*) update {
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

#pragma mark TableViewDelegate Methods

- (NSInteger) numberOfSectionsInTableView: (UITableView *) tableView {
	return 1;
}

- (NSInteger) tableView: (UITableView *) tableView numberOfRowsInSection: (NSInteger) section {
	return [dataController countOfList];
}

- (void) tableView: (UITableView *) tableView didSelectRowAtIndexPath: (NSIndexPath *) indexPath {
	ipText.text = [dataController objectInListAtIndex:indexPath.row];
}

- (UITableViewCell *) tableView: (UITableView *) tableView cellForRowAtIndexPath: (NSIndexPath *) indexPath {
	/*
	 * Call back methods invoked by the interface when drawing the table view.
	 * This methods creates a cell for each row and adds text from the datasource
	 */
	UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"CellIdentifier"];
	if (cell == nil) {
		cell = [[[UITableViewCell alloc] initWithFrame:CGRectMake(0, 0, 0, 0) reuseIdentifier:@"CellIdentifier"] autorelease];
		cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
	}
	if ([dataController countOfList] > 0 && indexPath.row < [dataController countOfList]) {
		cell.selectionStyle = UITableViewCellSelectionStyleBlue;
		//cell.textLabel.textColor = [UIColor whiteColor];
		//cell.textLabel.backgroundColor = [UIColor clearColor];
		cell.textLabel.text = [dataController objectInListAtIndex:indexPath.row];
		cell.textLabel.font = [UIFont systemFontOfSize:12];
	}
	return cell;
}

- (UITableViewCellEditingStyle) tableView: (UITableView *) tableView editingStyleForRowAtIndexPath: (NSIndexPath *) indexPath {
	return UITableViewCellEditingStyleDelete;
}

- (void) tableView: (UITableView *) tableView commitEditingStyle: (UITableViewCellEditingStyle) editingStyle forRowAtIndexPath: (NSIndexPath *) indexPath {
	/*
	 * This methosd is invoked when the user has finished editing one of the rows of the table
	 */
	if (editingStyle == UITableViewCellEditingStyleDelete) {
		[dataController removeDataAtIndex:indexPath.row];
		[tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
	}
}

#pragma mark Interface Methods

- (IBAction) goStart {
	composeViewController.client = self.client;
	composeViewController.clientConnected = self.clientConnected;
	composeViewController.playerId = self.playerId;
	
	/*
	 *	Send game type selected to public display
	 */
	NSString* inputText = [NSString stringWithFormat: @"%@_%@", playerId, @"gameoptions"];
	
    // Initialize PlayNoteRequest and set message to content's of the text field.
	GameStateRequest* request = [[GameStateRequest alloc] init];
    [request setGameState:inputText];
    
    // Setup the client to send the message a little later in the run loop.
    [client performSelector:@selector(sendMessage:) withObject: request];
    
    [request release];
	
	[self presentModalViewController:composeViewController animated:YES];
}

- (IBAction) goConnect {
	/*
	 * Setup Client
	 */
	// Get an instance of the ChatTranslations scope.
	TranslationScope* scope = [ServerTranslations get];
	
	//TODO: Validate IP Address
	validatedIp = [ipText.text copy];
    
	// Initialize the client with the ChatTranslations scope.
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
	if (self.composeViewController) {
		[self.composeViewController resetSliderValues];
	}
	
	[self drawIP];
}

- (IBAction) goToHelpView {
	[self presentModalViewController:helpViewController	animated:YES];
	[helpViewController showMainHelp];
}

#pragma mark Draw Methods

- (void) drawIP {
	connect.hidden = NO;
	disconnect.hidden = YES;
	ipText.hidden = NO;
	ipLabel.hidden = NO;
	start.hidden = YES;
	ipText.text = [dataController objectInListAtIndex:0];
	ipTableView.hidden = NO;
}

- (void) drawMain {
	connect.hidden = YES;
	disconnect.hidden = NO;
	ipText.hidden = YES;
	ipLabel.hidden = YES;
	ipTableView.hidden = YES;
	start.hidden = NO;
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
	 self.dataController = [[DataController alloc] init];
	 //ipTableView = [[UITableView alloc] initWithFrame:CGRectMake(50, 310, 220, 125) style:UITableViewStylePlain];
	 ipTableView = [[UITableView alloc] initWithFrame:CGRectMake(50, 310, 220, 125) style:UITableViewStyleGrouped];
	 ipTableView.dataSource = self;
	 ipTableView.delegate = self;
	 ipTableView.backgroundColor = [UIColor clearColor];
	 //ipTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
	 ipTableView.indicatorStyle = UIScrollViewIndicatorStyleWhite;
	 ipTableView.showsVerticalScrollIndicator = YES;
	 [ipTableView flashScrollIndicators];
	 
	 [self.view addSubview:ipTableView];
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
	[helpViewController release];
	[dataController release];
    [super dealloc];
}

#pragma mark XMLClientDelegate Methods

- (void) connectionTerminated:(XMLClient*)client
{
	NSLog(@"The connection terminated!\n");
	self.clientConnected = NO;
	if (self.composeViewController) {
		[self.composeViewController setClientConnected: NO];
	}
}

- (void) connectionAttemptFailed:(XMLClient*) connection
{
	NSLog(@"The connection failed to connect!\n");
	self.clientConnected = NO;
	if (self.composeViewController) {
		[self.composeViewController setClientConnected: NO];
	}
	UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Unable to connect" message:(@"Unable to connect to %@.", validatedIp) delegate:self cancelButtonTitle:@"Ok" otherButtonTitles:nil];
	[alert show];
	[alert release];
}

- (void) connectionSuccessful:(XMLClient*) client withSessionId:(NSString*) sessionId;
{
	NSLog(@"Connection successful with session id:%@\n", sessionId);
	[dataController addData:validatedIp];
	[ipTableView reloadData];
}

@end
