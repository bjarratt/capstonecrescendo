//
//  GameOptionsRequest.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/18/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RequestMessage.h"

@interface GameOptionsRequest : RequestMessage
{
	/* player1_play (start button)
	 * --- Update on slider movement ---
	 * player1_settempo_X
	 * player1_setkey_PITCH
	 * player1_settimesignature_X_Y
	 * player1_setnumberofbars_X
	 */
	NSString *gameOption;
}

@property (nonatomic,readwrite, retain) NSString *gameOption;

@end