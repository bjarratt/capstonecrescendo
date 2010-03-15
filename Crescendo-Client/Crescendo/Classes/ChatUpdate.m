//  ecologylabFundamentalObjC
//
//  Created by William Hamilton on 2/24/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//
#import "ChatUpdate.h"
#import "ChatUpdateDelegate.h"

@implementation ChatUpdate

@synthesize message;
@synthesize host;
@synthesize port;

+ (void) initialize {
	[ChatUpdate class];
}

- (void) setPortWithReference: (int *) p_port {
	port = *p_port;
}

-(void) processUpdate:(Scope *) scope
{
  /*
   * Get reference to the CHAT_UPDATE_DELEGATE out of the application scope. 
   */
  NSValue* updateDelegatePtr = (NSValue*)[scope objectForKey:CHAT_UPDATE_DELEGATE];
  id<ChatUpdateDelegate> updateDelegate = (id<ChatUpdateDelegate>)[updateDelegatePtr pointerValue];
  
  /*
   * updateDelegate may be nil but we can send a selector to nil
   */
  [updateDelegate recievedChatUpdate:self];
}

@end

