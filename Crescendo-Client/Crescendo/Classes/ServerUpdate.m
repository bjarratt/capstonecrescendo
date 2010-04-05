//  ecologylabFundamentalObjC
//
//  Created by William Hamilton on 2/24/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//
#import "ServerUpdate.h"
#import "ServerUpdateDelegate.h"

@implementation ServerUpdate

@synthesize message;
@synthesize host;
@synthesize port;

+ (void) initialize {
	[ServerUpdate class];
}

- (void) setPortWithReference: (int *) p_port {
	port = *p_port;
}

-(void) processUpdate:(Scope *) scope
{
  /*
   * Get reference to the CHAT_UPDATE_DELEGATE out of the application scope. 
   */
  NSValue* updateDelegatePtr = (NSValue*)[scope objectForKey:SERVER_UPDATE_DELEGATE];
  id<ServerUpdateDelegate> updateDelegate = (id<ServerUpdateDelegate>)[updateDelegatePtr pointerValue];
  
  /*
   * updateDelegate may be nil but we can send a selector to nil
   */
  [updateDelegate recievedServerUpdate:self];
}

@end

