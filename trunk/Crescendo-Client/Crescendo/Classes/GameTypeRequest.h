//
//  GameTypeRequest.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RequestMessage.h"

@interface GameTypeRequest : RequestMessage
{
	/* player1_splashscreen (showing game modes button)
	 * player1_gamemodes (showing the different modes listed below)
	 * player1_lengthtraining
	 * player1_pitchtraining
	 * player1_notetraining
	 * player1_concertmaster
	 * player1_musicaliphones
	 * player1_notesaroundtheroom
	 * player1_comptime
	 * player1_compose
	 */
	NSString *gameType;
}

@property (nonatomic,readwrite, retain) NSString *gameType;

@end
