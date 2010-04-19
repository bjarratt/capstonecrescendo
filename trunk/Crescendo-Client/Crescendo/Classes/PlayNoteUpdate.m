//
//  PlayNoteUpdate.m
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import "PlayNoteUpdate.h"
#import "PlayNoteUpdateDelegate.h"

@implementation PlayNoteUpdate

+ (void) initialize {
	[PlayNoteUpdate class];
}

-(void) processUpdate:(Scope *) scope
{
	/*
	 * Get reference to the SERVER_UPDATE_DELEGATE out of the application scope. 
	 */
	NSValue* updateDelegatePtr = (NSValue*)[scope objectForKey:PLAYNOTE_UPDATE_DELEGATE];
	id<PlayNoteUpdateDelegate> updateDelegate = (id<PlayNoteUpdateDelegate>)[updateDelegatePtr pointerValue];
	
	/*
	 * updateDelegate may be nil but we can send a selector to nil
	 */
	[updateDelegate recievedPlayNoteUpdate:self];
}

@end