//
//  ChatUpdateDelegate.h
//  ecologylabFundamentalObjC
//
//  Created by William Hamilton on 2/24/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ServerUpdate.h"

extern NSString * const SERVER_UPDATE_DELEGATE;

@protocol ServerUpdateDelegate
  - (void) recievedServerUpdate:(ServerUpdate*)update;
@end
