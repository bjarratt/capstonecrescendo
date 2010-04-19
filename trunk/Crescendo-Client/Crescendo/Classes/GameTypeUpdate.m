//
//  GameTypeUpdate.m
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import "GameTypeUpdate.h"
#import "GameTypeUpdateDelegate.h"

@implementation GameTypeUpdate

+ (void) initialize {
	[GameTypeUpdate class];
}

-(void) processUpdate:(Scope *) scope
{
	/*
	 * Get reference to the GAMETYPE_UPDATE_DELEGATE out of the application scope. 
	 */
	NSValue* updateDelegatePtr = (NSValue*)[scope objectForKey:GAMETYPE_UPDATE_DELEGATE];
	id<GameTypeUpdateDelegate> updateDelegate = (id<GameTypeUpdateDelegate>)[updateDelegatePtr pointerValue];
	
	/*
	 * updateDelegate may be nil but we can send a selector to nil
	 */
	[updateDelegate recievedGameTypeUpdate:self];
}

@end
