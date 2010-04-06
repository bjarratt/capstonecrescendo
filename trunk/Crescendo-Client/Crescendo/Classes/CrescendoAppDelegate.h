//
//  CrescendoAppDelegate.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "XMLClient.h"
#import "ServerTranslations.h"

@class CrescendoViewController;

@interface CrescendoAppDelegate : NSObject <UIApplicationDelegate, XMLClientDelegate> {
    UIWindow *window;
    CrescendoViewController *viewController;
	XMLClient* client;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet CrescendoViewController *viewController;
@property (nonatomic, retain) XMLClient *client;

@end

