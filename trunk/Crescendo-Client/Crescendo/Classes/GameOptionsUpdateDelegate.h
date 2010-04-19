//
//  GameOptionsUpdateDelegate.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/18/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GameOptionsUpdate.h"

extern NSString * const GAMEOPTIONS_UPDATE_DELEGATE;

@protocol GameOptionsUpdateDelegate
- (void) recievedGameOptionsUpdate:(GameOptionsUpdate*)update;
@end