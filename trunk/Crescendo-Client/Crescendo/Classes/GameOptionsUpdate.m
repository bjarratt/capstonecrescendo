//
//  GameOptionsUpdate.m
//  Crescendo
//
//  Created by Brandon Kaster on 4/18/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import "GameOptionsUpdate.h"
#import "GameOptionsUpdateDelegate.h"

@implementation GameOptionsUpdate

+ (void) initialize {
	[GameOptionsUpdate class];
}

-(void) processUpdate:(Scope *) scope
{
	/*
	 * Get reference to the SERVER_UPDATE_DELEGATE out of the application scope. 
	 */
	NSValue* updateDelegatePtr = (NSValue*)[scope objectForKey:GAMEOPTIONS_UPDATE_DELEGATE];
	id<GameOptionsUpdateDelegate> updateDelegate = (id<GameOptionsUpdateDelegate>)[updateDelegatePtr pointerValue];
	
	/*
	 * updateDelegate may be nil but we can send a selector to nil
	 */
	[updateDelegate recievedGameOptionsUpdate:self];
}

@end