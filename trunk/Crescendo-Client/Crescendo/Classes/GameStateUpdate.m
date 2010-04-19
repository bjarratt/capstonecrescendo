//
//  GameStateUpdate.m
//  Crescendo
//
//  Created by Brandon Kaster on 4/18/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import "GameStateUpdate.h"
#import "GameStateUpdateDelegate.h"

@implementation GameStateUpdate

+ (void) initialize {
	[GameStateUpdate class];
}

-(void) processUpdate:(Scope *) scope
{
	/*
	 * Get reference to the SERVER_UPDATE_DELEGATE out of the application scope. 
	 */
	NSValue* updateDelegatePtr = (NSValue*)[scope objectForKey:GAMESTATE_UPDATE_DELEGATE];
	id<GameStateUpdateDelegate> updateDelegate = (id<GameStateUpdateDelegate>)[updateDelegatePtr pointerValue];
	
	/*
	 * updateDelegate may be nil but we can send a selector to nil
	 */
	[updateDelegate recievedGameStateUpdate:self];
}

@end