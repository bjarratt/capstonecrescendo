//
//  PlayNoteRequest.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RequestMessage.h"

@interface PlayNoteRequest : RequestMessage
{
	/* playerId_notePitch_noteLength
	 */
	NSString *jfuguePattern;
}

@property (nonatomic,readwrite, retain) NSString *jfuguePattern;

@end