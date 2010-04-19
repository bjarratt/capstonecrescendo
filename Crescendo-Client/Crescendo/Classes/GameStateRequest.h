//
//  GameStateRequest.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/18/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RequestMessage.h"

@interface GameStateRequest : RequestMessage
{
	/* playerX_play (opposite of pause)
	 * playerX_pause (opposite of play)
	 * playerX_play_song
	 */
	NSString *gameState;
}

@property (nonatomic,readwrite, retain) NSString *gameState;

@end