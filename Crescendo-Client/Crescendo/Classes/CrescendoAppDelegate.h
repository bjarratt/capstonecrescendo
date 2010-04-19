//
//  CrescendoAppDelegate.h
//  Crescendo
//
//  Created by Brandon Kaster on 4/5/10.
//  Copyright 2010 Texas A&M University. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "CrescendoViewController.h"

@class CrescendoViewController;

@interface CrescendoAppDelegate : NSObject <UIApplicationDelegate> {
	UIWindow *window;
    CrescendoViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet CrescendoViewController *viewController;

@end

