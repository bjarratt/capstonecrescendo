//
//  GameTypeUpdateDelegate.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GameTypeUpdate.h"

extern NSString * const GAMETYPE_UPDATE_DELEGATE;

@protocol GameTypeUpdateDelegate
- (void) recievedGameTypeUpdate:(GameTypeUpdate*)update;
@end
