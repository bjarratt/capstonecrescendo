//  Test
//  CrescendoAppDelegate.h
//  Crescendo
//
//  Created by Brandon on 3/5/10.
//  Copyright __MyCompanyName__ 2010. All rights reserved.
//

#import <UIKit/UIKit.h>

@class CrescendoViewController;

@interface CrescendoAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    CrescendoViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet CrescendoViewController *viewController;

@end

