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
	/* gameinfo
	 * play
	 * settempo_X
	 * setkey_PITCH
	 * settimesignature_X_Y
	 * setnumberofbars_X
	 */
	NSString *gameOption;
}

@property (nonatomic,readwrite, retain) NSString *gameOption;

@end