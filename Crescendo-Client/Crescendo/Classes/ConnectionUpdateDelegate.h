//
//  ConnectionUpdateDelegate.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ConnectionUpdate.h"

extern NSString * const CONNECTION_UPDATE_DELEGATE;

@protocol ConnectionUpdateDelegate

- (void) recievedConnectionUpdate:(ConnectionUpdate*)update;

@end
