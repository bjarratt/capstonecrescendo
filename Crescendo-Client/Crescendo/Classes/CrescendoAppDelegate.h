//
//  CrescendoAppDelegate.h
//  Crescendo
//
//  Created by Brandon on 3/5/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
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

