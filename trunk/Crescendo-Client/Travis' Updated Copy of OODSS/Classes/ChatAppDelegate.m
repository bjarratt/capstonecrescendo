//
//  ChatAppDelegate.m
//  ecologylabFundamentalObjC
//
//  Created by William Hamilton on 2/24/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//
#import "ChatAppDelegate.h"
#import "ChatViewController.h"
#import "ChatRoomViewController.h"

/*
 App Delegate
 */
@implementation ChatAppDelegate

@synthesize window;
@synthesize chatRoomViewController;
@synthesize client;

- (void)applicationDidFinishLaunching:(UIApplication *)application 
{         
  /*
   * Get an instance of the ChatTranslations scope.
   */
  TranslationScope* scope = [ChatTranslations get];
    
  /*
   * Initialize the client with the ChatTranslations scope.
   */
  self.client = [[XMLClient alloc] initWithHostAddress:@"192.168.11.5" andPort:2108 
                                         andTranslationScope:scope];

  /*
   * Designate self as the client's delegate.
   */
  self.client.delegate = self;
  
  /*
   * Set the ChatRoomViewController's client as the client.
   */
  chatRoomViewController.client = self.client;
  
  /*
   * Set up the chatRoomViewController as the CHAT_UPDATE_DELEGATE in the application scope.
   */
  [self.client.scope setObject:[NSValue valueWithPointer:(chatRoomViewController)] forKey:CHAT_UPDATE_DELEGATE];
  
  /*
   * Connect the client to the server.
   */
  [self.client connect];
  
  [window addSubview:chatRoomViewController.view];
  [window makeKeyAndVisible];
  
}


- (void)dealloc {
  [chatRoomViewController release];
  [window release];
  [super dealloc];
}

/*
 * Show chat room
 */
- (void)showChatRoom
{
  [chatRoomViewController activate];  
  [window bringSubviewToFront:chatRoomViewController.view];
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
  /*
   * Show the chat window after the client has connected.
   */ 
  [window bringSubviewToFront:chatRoomViewController.view];
  [self showChatRoom];
  
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
