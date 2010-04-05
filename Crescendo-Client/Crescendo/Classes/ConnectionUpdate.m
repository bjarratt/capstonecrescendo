//
//  ConnectionUpdate.m
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import "ConnectionUpdate.h"
#import "ConnectionUpdateDelegate.h"

@implementation ConnectionUpdate

@synthesize message;
@synthesize host;
@synthesize port;

+ (void) initialize {
	[ConnectionUpdate class];
}

- (void) setPortWithReference: (int *) p_port {
	port = *p_port;
}

-(void) processUpdate:(Scope *) scope
{
	/*
	 * Get reference to the CHAT_UPDATE_DELEGATE out of the application scope. 
	 */
	NSValue* updateDelegatePtr = (NSValue*)[scope objectForKey:CONNECTION_UPDATE_DELEGATE];
	id<ConnectionUpdateDelegate> updateDelegate = (id<ConnectionUpdateDelegate>)[updateDelegatePtr pointerValue];
	
	/*
	 * updateDelegate may be nil but we can send a selector to nil
	 */
	[updateDelegate recievedConnectionUpdate:self];
}

@end
