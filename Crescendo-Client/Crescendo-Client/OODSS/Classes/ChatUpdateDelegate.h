//
//  ChatUpdateDelegate.h
//  ecologylabFundamentalObjC
//
//  Created by William Hamilton on 2/24/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ChatUpdate.h"

extern NSString * const CHAT_UPDATE_DELEGATE;

@protocol ChatUpdateDelegate
  - (void) recievedChatUpdate:(ChatUpdate*)update;
@end
