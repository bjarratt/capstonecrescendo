//
//  GameStateUpdateDelegate.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/18/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GameStateUpdate.h"

extern NSString * const GAMESTATE_UPDATE_DELEGATE;

@protocol GameStateUpdateDelegate
- (void) recievedGameStateUpdate:(GameStateUpdate*)update;
@end
