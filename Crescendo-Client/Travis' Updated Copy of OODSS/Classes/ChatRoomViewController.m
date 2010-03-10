//
//  ChatRoomViewController.m
//  ecologylabFundamentalObjC
//
//  Created by William Hamilton on 2/24/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import "ChatRoomViewController.h"
#import "ChatAppDelegate.h"
#import "UITextView+Utils.h"
#import "AppConfig.h"
#import "ChatRequest.h"

@implementation ChatRoomViewController

@synthesize client;

/*
 * Add a chat message
 */
- (void)displayChatMessage:(NSString*)message fromUser:(NSString*)userName {
  [chat appendTextAfterLinebreak:[NSString stringWithFormat:@"%@: %@", userName, message]];
  [chat scrollToBottom];
}

/*
 * ChatUpdateDelegate method.
 */
- (void) recievedChatUpdate:(ChatUpdate*)update;
{
  /*
   * Build username (ip:port) and post to chat area.
   */
  NSString* user = [NSString stringWithFormat:@"%@:%d", update.host, update.port];
  [self displayChatMessage:update.message fromUser:user];
}

#pragma mark -
#pragma mark UITextFieldDelegate Method Implementations

/*
 * This is called whenever "Return" is touched on iPhone's keyboard
 */
- (BOOL)textFieldShouldReturn:(UITextField *)theTextField {
	if (theTextField == input) {
		NSString* inputText = input.text;
       
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

		[input setText:@""];
    [self displayChatMessage:inputText fromUser:@"me"];
	}
	return YES;
}

- (void)activate {
  [input becomeFirstResponder];
}

- (void)dealloc {
  [super dealloc];
}

@end
