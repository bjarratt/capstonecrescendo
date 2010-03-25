//
//  CrescendoViewController.m
//  Crescendo
//
//  Created by Brandon on 3/5/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "CrescendoViewController.h"
#import "AppConfig.h"
#import "ChatRequest.h"

@implementation CrescendoViewController

@synthesize client;

/*
 * ChatUpdateDelegate method.
 */
- (void) recievedChatUpdate:(ChatUpdate*)update {
}

- (IBAction) goToTrainingView {
	NSString* inputText = @"training view";
	
    /*
     * Initialize ChatRequest and set message to content's of the text field.
     */
    ChatRequest* request = [[ChatRequest alloc] init];
    [request setMessage:inputText];
    
    /*
     * Setup the client to send the message a little later in
     * the run loop.
     */
    [client performSelector:@selector(sendMessage:) withObject: request];
    
    [request release];
	
	[self presentModalViewController:trainingViewController animated:YES];
}
- (IBAction) goToGamemodeView {
	[self presentModalViewController:gamemodeViewController animated:YES];
}
- (IBAction) goToComposeView {
	[self presentModalViewController:composeViewController animated:YES];
}
- (IBAction) goToHelpView {
	[self presentModalViewController:helpViewController	animated:YES];
}

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


/*
 // Override to allow orientations other than the default portrait orientation.
 - (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
 // Return YES for supported orientations
 return (interfaceOrientation == UIInterfaceOrientationPortrait);
 }
 */

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
	[trainingViewController release];
	[gamemodeViewController release];
	[helpViewController release];
    [super dealloc];
}

@end
