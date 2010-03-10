//
//  ChatRoomViewController.h
//  ecologylabFundamentalObjC
//
//  Created by William Hamilton on 2/24/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//
#import <UIKit/UIKit.h>
#import "ChatUpdateDelegate.h"
#import "xMLClient.h"

@interface ChatRoomViewController : UIViewController <UITextFieldDelegate, ChatUpdateDelegate> {
  IBOutlet UITextView* chat;
  IBOutlet UITextField* input;
  XMLClient* client;
}

@property(nonatomic, retain) XMLClient* client;

// View is active, start everything up
- (void)activate;

@end
