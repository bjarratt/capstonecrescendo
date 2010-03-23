//
//  CrescendoAppDelegate.m
//  Crescendo
//
//  Created by Brandon on 3/5/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import "CrescendoAppDelegate.h"
#import "CrescendoViewController.h"

@implementation CrescendoAppDelegate

@synthesize window;
@synthesize viewController;
@synthesize client;


- (void)applicationDidFinishLaunching:(UIApplication *)application {    
    /*
	 * Get an instance of the ChatTranslations scope.
	 */
	TranslationScope* scope = [ChatTranslations get];
    
	/*
	 * Initialize the client with the ChatTranslations scope.
	 */
	self.client = [[XMLClient alloc] initWithHostAddress:@"lennon.cs.tamu.edu" andPort:2108 
									 andTranslationScope:scope];
	
	/*
	 * Designate self as the client's delegate.
	 */
	self.client.delegate = self;
	
	/*
	 * Set the ChatRoomViewController's client as the client.
	 */
	viewController.client = self.client;
	
	/*
	 * Set up the chatRoomViewController as the CHAT_UPDATE_DELEGATE in the application scope.
	 
	[self.client.scope setObject:[NSValue valueWithPointer:(chatRoomViewController)] forKey:CHAT_UPDATE_DELEGATE];
	*/
	
	/*
	 * Connect the client to the server.
	 */
	[self.client connect];	
	
    // Override point for customization after app launch    
    [window addSubview:viewController.view];
    [window makeKeyAndVisible];
}


- (void) dealloc {
    [viewController release];
    [window release];
	[client release];
    [super dealloc];
}

/*
 * XMLClientDelegate methods:
 */
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

/*
 * Close the connection when the user hits the home button.
 */
- (void) applicationWillTerminate:(UIApplication*) application
{
	[client disconnect];
}
@end